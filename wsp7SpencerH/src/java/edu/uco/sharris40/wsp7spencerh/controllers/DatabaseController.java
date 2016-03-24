package edu.uco.sharris40.wsp7spencerh.controllers;

import edu.uco.sharris40.wsp7spencerh.models.Book;
import edu.uco.sharris40.wsp7spencerh.models.Order;
import edu.uco.sharris40.wsp7spencerh.models.OrderFactory;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Named(value = "databaseController")
@ApplicationScoped
public class DatabaseController implements Serializable {
  private static final long serialVersionUID = 2L;

  @Resource(name="jdbc/WSP7")
  private DataSource ds;

  private volatile LinkedList<Book> cachedBookList = null;
  private volatile LinkedList<Order> cachedOrderList = null;
  ReentrantReadWriteLock bookLock;
  private ReentrantReadWriteLock orderLock;

  public DatabaseController() {}

  @PostConstruct
  private void init() {
    bookLock = new ReentrantReadWriteLock();
    orderLock = new ReentrantReadWriteLock();
  }

  private Connection getConnection() {
    if (ds == null)
      return null;
    Connection connection = null;
    try {
      connection = ds.getConnection();
    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    return connection;
  }

  private void doBookUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
    bookLock.writeLock().lock();
    cachedBookList = null;
    try {
      int rows = stmt.executeUpdate();
      if (rows != 1)
        throw new SQLException("Wrong number of rows generated; "
                + "expected 1, got " + rows);
    } finally {
      try {
        conn.close();
      } catch (SQLException se) {
        se.printStackTrace(System.err);
      }
      bookLock.writeLock().unlock();
    }
  }

  public boolean createBook(Book book) {
    if (book == null)
      return false;

    boolean success = false;
    Connection connection = getConnection();
    if (connection == null)
      return false;

    try {
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO books(title, author, price, publicationYear) "
                  + "VALUES(?, ?, ?, ?)");
      statement.setNString(1, book.getTitle());
      statement.setNString(2, book.getAuthor());
      statement.setInt(3, book.getPrice());
      statement.setInt(4, book.getPublicationYear());
      doBookUpdate(connection, statement);
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    return success;
  }

  private static Book createBookFromRow(ResultSet row) throws SQLException {
    Book nextBook = new Book();
    nextBook.setId(row.getInt("bookid"));
    nextBook.setTitle(row.getNString("title"));
    nextBook.setAuthor(row.getNString("author"));
    nextBook.setPrice(row.getInt("price"));
    nextBook.setPublicationYear(row.getInt("publicationYear"));
    nextBook.setChanged(false);
    return nextBook;
  }

  public List<Book> readBooks() {
    LinkedList<Book> books = null;
    Connection connection = getConnection();
    if (connection == null)
      return null;
    bookLock.readLock().lock();
    try {
      if (cachedBookList == null) {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM books");
        cachedBookList = new LinkedList<>();
        while (results.next()) {
          cachedBookList.add(createBookFromRow(results));
        }
      }
      books = new LinkedList<>();
      for (Book book : cachedBookList) {
        books.add(book.clone());
      }
    } catch (SQLException | CloneNotSupportedException e) {
      e.printStackTrace(System.err);
    } finally {
      try {
        connection.close();
      } catch (SQLException se) {
        se.printStackTrace(System.err);
      }
      bookLock.readLock().unlock();
    }
    return books;
  }

  public boolean updateBook(Book book) {
    if (book == null || book.getId() < 0)
      return false;

    boolean success = false;
    Connection connection = getConnection();
    if (connection == null)
      return false;

    try {
      PreparedStatement statement = connection.prepareStatement(
              "UPDATE books SET title=?, author=?, price=?, publicationYear=? "
                  + "WHERE bookid=?");
      statement.setNString(1, book.getTitle());
      statement.setNString(2, book.getAuthor());
      statement.setInt(3, book.getPrice());
      statement.setInt(4, book.getPublicationYear());
      statement.setInt(5, book.getId());
      doBookUpdate(connection, statement);
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    return success;
  }

  public boolean deleteBook(Book book) {
    if (book == null || book.getId() < 0)
      return false;

    boolean success = false;
    Connection connection = getConnection();
    if (connection == null)
      return false;

    try {
      PreparedStatement statement = connection.prepareStatement(
              "DELETE FROM books WHERE bookid=?");
      statement.setInt(1, book.getId());
      doBookUpdate(connection, statement);
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    return success;
  }

  public boolean placeOrder(Order order) {
    if (order == null)
      return false;

    boolean success = false;
    Connection connection = getConnection();
    if (connection == null)
      return false;

    int orderid = 1;
    orderLock.writeLock().lock();
    try {
      orderLock.readLock().lock();
      try {
        Statement orderStatement = connection.createStatement();
        ResultSet orderResult = orderStatement.executeQuery(
                "SELECT orderid FROM orders "
                  + "ORDER BY orderid DESC "
                  + "LIMIT 1");
        if (orderResult.next()) {
          orderid = orderResult.getInt(1) + 1;
        }
      } finally {
        orderLock.readLock().unlock();
      }
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO orders(orderid, bookid, quantity) "
                  + "VALUES(?, ?, ?)");
      int rows;
      cachedOrderList = null;
      for (Map.Entry<Book, Integer> entry : order.entrySet()) {
        statement.setInt(1, orderid);
        statement.setInt(2, entry.getKey().getId());
        statement.setInt(3, entry.getValue());
        rows = statement.executeUpdate();
        if (rows != 1)
          throw new SQLException("Wrong number of rows generated; "
                  + "expected 1, got " + rows);
      }
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    } finally {
      orderLock.writeLock().unlock();
    }

    orderLock.readLock().lock();
    try {
      PreparedStatement dateStatement = connection.prepareStatement(
              "SELECT date FROM orders "
                + "WHERE orderid = ? "
                + "LIMIT 1");
      dateStatement.setInt(1, orderid);
      ResultSet dateResult = dateStatement.executeQuery();
      if (!dateResult.next())
          throw new SQLException("Could not retrieve order after submission.");
      order.setDate(dateResult.getTimestamp(1));
    }catch (SQLException se) {
      se.printStackTrace(System.err);
    } finally {
      try {
        connection.close();
      } catch (SQLException se) {
        se.printStackTrace(System.err);
      }
      orderLock.readLock().unlock();
    }
    return success;
  }

  public List<Order> readOrders() {
    LinkedList<Order> orders = null;
    Connection connection = getConnection();
    if (connection == null)
      return null;
    orderLock.readLock().lock();
    bookLock.readLock().lock();
    try {
      if (cachedOrderList == null) {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM orders "
                                                     + "ORDER BY orderid ASC");

        cachedOrderList = new LinkedList<>();
        int lastid = -1;
        Order order = OrderFactory.create(-1); // for safety
        int id;
        PreparedStatement bookStatement;
        ResultSet bookResult;
        HashMap<Integer, Book> bookMap = new HashMap<>();
        Book book;
        int bookid;
        while (results.next()) {
          id = results.getInt("orderid");
          if (id != lastid) {
            if (lastid > -1)
              cachedOrderList.add(order);
            order = OrderFactory.create(id);
            order.setDate(results.getDate("date"));
            lastid = id;
          }

          bookid = results.getInt("bookid");
          book = bookMap.get(bookid);
          if (book == null) {
            bookStatement = connection.prepareStatement("SELECT * FROM books "
                                                          + "WHERE bookid = ?");
            bookStatement.setInt(1, id);
            bookResult = bookStatement.executeQuery();
            if (!bookResult.next())
              throw new SQLException(String.format("Could not access book with "
                      + "bookid %d.", id));
            book = DatabaseController.createBookFromRow(bookResult);
            bookMap.put(bookid, book);
          }
          order.put(book, results.getInt("quantity"));
        }
        if (order != null) {
          System.out.println(order.getId());
          cachedOrderList.add(order);
        }
      }
      orders = (LinkedList<Order>) cachedOrderList.clone();
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    } finally {
      try {
        connection.close();
      } catch (SQLException se) {
        se.printStackTrace(System.err);
      }
      bookLock.readLock().unlock();
      orderLock.readLock().unlock();
    }
    return orders;
  }
}

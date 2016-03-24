package edu.uco.sharris40.wsp7spencerh;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Named(value = "ordersTable")
@ApplicationScoped
public class OrdersTable implements Serializable {
  private static final long serialVersionUID = 1L;

  @Resource(name="jdbc/WSP7")
  private DataSource ds;

  private volatile LinkedList<Book> cachedList = null;
  private ReentrantReadWriteLock lock;

  public OrdersTable() {}

  @PostConstruct
  private void init() {
    lock = new ReentrantReadWriteLock();
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

  private void doUpdate(Connection conn, PreparedStatement stmt) throws SQLException {
    lock.writeLock().lock();
    cachedList = null;
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
      lock.writeLock().unlock();
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
      doUpdate(connection, statement);
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    return success;
  }

  public List<Book> readBooks() {
    LinkedList<Book> books = null;
    Connection connection = getConnection();
    if (connection == null)
      return null;
    lock.readLock().lock();
    try {
      if (cachedList == null) {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM books");
        cachedList = new LinkedList<>();
        while (results.next()) {
          Book nextBook = new Book();
          nextBook.setId(results.getInt("bookid"));
          nextBook.setTitle(results.getNString("title"));
          nextBook.setAuthor(results.getNString("author"));
          nextBook.setPrice(results.getInt("price"));
          nextBook.setPublicationYear(results.getInt("publicationYear"));
          nextBook.setChanged(false);
          cachedList.add(nextBook);
        }
      }
      books = new LinkedList<>();
      for (Book book : cachedList) {
        books.add(book.clone());
      }
    } catch (SQLException | CloneNotSupportedException e) {
      e.printStackTrace(System.err);
    } finally {
      lock.readLock().unlock();
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
      doUpdate(connection, statement);
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
      doUpdate(connection, statement);
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    return success;
  }
}

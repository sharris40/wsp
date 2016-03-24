package edu.uco.sharris40.wsp7spencerh.controllers;

import edu.uco.sharris40.wsp7spencerh.models.OrderFactory;
import edu.uco.sharris40.wsp7spencerh.models.Order;
import edu.uco.sharris40.wsp7spencerh.models.Book;
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
import javax.inject.Inject;

@Named(value = "ordersTable")
@ApplicationScoped
public class OrdersTable implements Serializable {
  private static final long serialVersionUID = 2L;

  @Resource(name="jdbc/WSP7")
  private DataSource ds;

  @Inject
  private BookTable table;

  private volatile LinkedList<Order> cachedList = null;
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

  public boolean placeOrder(Order order) {
    if (order == null)
      return false;

    boolean success = false;
    Connection connection = getConnection();
    if (connection == null)
      return false;

    try {
      PreparedStatement statement = connection.prepareStatement(
              "INSERT INTO orders(orderid, bookid, quantity) "
                  + "VALUES(?, ?, ?)");
      int rows;
      cachedList = null;
      for (Map.Entry<Book, Integer> entry : order.entrySet()) {
        statement.setInt(1, entry.getKey().getPrice());
        statement.setInt(2, entry.getValue());
        lock.writeLock().lock();
        rows = statement.executeUpdate();
        if (rows != 1)
          throw new SQLException("Wrong number of rows generated; "
                  + "expected 1, got " + rows);
      }
      success = true;

    } catch (SQLException se) {
      se.printStackTrace(System.err);
    } finally {
      try {
        connection.close();
      } catch (SQLException se) {
        se.printStackTrace(System.err);
      }
      lock.writeLock().unlock();
    }
    return success;
  }

  public List<Order> readOrders() {
    LinkedList<Order> orders = null;
    Connection connection = getConnection();
    if (connection == null)
      return null;
    lock.readLock().lock();
    table.lock.readLock().lock();
    try {
      if (cachedList == null) {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM orders "
                                                     + "ORDER BY orderid ASC");

        cachedList = new LinkedList<>();
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
            cachedList.add(order);
            order = OrderFactory.create(id);
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
            book = BookTable.createBookFromRow(bookResult);
            bookMap.put(bookid, book);
          }
          order.put(book, results.getInt("quantity"));
        }
        if (order != null)
          cachedList.add(order);
      }
      orders = (LinkedList<Order>) cachedList.clone();
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    } finally {
      try {
        connection.close();
      } catch (SQLException se) {
        se.printStackTrace(System.err);
      }
      table.lock.readLock().unlock();
      lock.readLock().unlock();
    }
    return orders;
  }
}

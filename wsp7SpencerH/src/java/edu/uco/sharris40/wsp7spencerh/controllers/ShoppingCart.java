package edu.uco.sharris40.wsp7spencerh.controllers;

import edu.uco.sharris40.wsp7spencerh.models.Book;
import edu.uco.sharris40.wsp7spencerh.models.Order;
import edu.uco.sharris40.wsp7spencerh.models.OrderFactory;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value = "shoppingCart")
@SessionScoped
public class ShoppingCart implements Serializable {
  private static final long serialVersionUID = 2L;

  @Inject
  private OrdersTable table;

  private Order order;

  public ShoppingCart() {
  }

  @PostConstruct
  private void init() {
    order = OrderFactory.create();
  }

  public Order getOrder() {
    return order;
  }

  public String clearCart() {
    init();
    return null;
  }

  public String addBook(Book book) {
    if (book == null)
      throw new IllegalArgumentException("book", new NullPointerException());
    boolean found = false;
    for (Map.Entry<Book, Integer> item : order.entrySet()) {
      if (item.getKey().equals(book)) {
        found = true;
        item.setValue(order.get(book) + 1);
        break;
      }
    }
    if (!found) {
      order.put(book, 1);
    }
    return null;
  }

  public String removeBook(Book book) {
    Integer count = order.get(book);
    if (count != null) {
      if (count > 1)
        order.put(book, count - 1);
      else
        order.remove(book);
    }
    return null;
  }
}

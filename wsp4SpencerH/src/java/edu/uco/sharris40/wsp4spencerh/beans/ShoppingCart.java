package edu.uco.sharris40.wsp4spencerh.beans;

import edu.uco.sharris40.wsp4spencerh.models.Book;
import edu.uco.sharris40.wsp4spencerh.models.BookItem;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

@Named(value = "shoppingCart")
@SessionScoped
public class ShoppingCart implements Serializable {
  private static final long serialVersionUID = 2L;

  private ArrayList<BookItem> items;

  public ShoppingCart() {
  }

  @PostConstruct
  public void init() {
    items = new ArrayList<>();
    Book book = new Book();
    BookItem item = new BookItem();
    item.setBook(book.init("Intro to Java Servlets", "Mark", 1095));
    items.add(item);
    book = new Book();
    item = new BookItem();
    item.setBook(book.init("Intro to JSP", "John", 1195));
    items.add(item);
    book = new Book();
    item = new BookItem();
    item.setBook(book.init("Intro to JSF", "Luke", 1295));
    items.add(item);
    book = new Book();
    item = new BookItem();
    item.setBook(book.init("Intro to Java EE", "Matt", 1395));
    items.add(item);
  }

  public ArrayList<BookItem> getItems() {
    return items;
  }

  public long getTotal() {
    long sum = 0;
    for (BookItem item : items) {
      if (sum != Long.MAX_VALUE) {
        sum += item.getTotal();
        if (sum < 0)
          sum = Long.MAX_VALUE;
      }
    }
    return sum;
  }

  public String formatTotal() {
    return String.format("$%d.%02d", getTotal() / 100, getTotal() % 100);
  }

  public void addBook(Book book) {
    if (book == null)
      throw new IllegalArgumentException("book", new NullPointerException());
    boolean found = false;
    for (BookItem item : items) {
      if (item.getBook().equals(book)) {
        found = true;
        item.add(1);
        break;
      }
    }
    if (!found) {
      BookItem newItem = new BookItem();
      newItem.setBook(book);
      newItem.setQuantity(1);
      items.add(newItem);
    }
  }

  public void removeBook(Book book) {
    if (book == null)
      throw new IllegalArgumentException("book", new NullPointerException());
    boolean found = false;
    for (int i = 0; i < items.size(); ++i) {
      BookItem item = items.get(i);
      if (item.getBook().equals(book)) {
        item.remove(1);
        if (item.getQuantity() == 0)
          items.remove(i);
        break;
      }
    }
  }

}

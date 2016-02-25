
package edu.uco.sharris40.wsp4spencerh.beans;

import edu.uco.sharris40.wsp4spencerh.models.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

@Named(value = "bookDatabase")
@ApplicationScoped
public class BookDatabase implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<Book> books;

  /**
   * Creates a new instance of BookDatabase
   */
  public BookDatabase() {
  }

  @PostConstruct
  protected void init() {
    books = new ArrayList<>(4);
    Book book = new Book();
    books.add(book.init("Intro to Java Servlets", "Mark", 1095));
    book = new Book();
    books.add(book.init("Intro to JSP", "John", 1195));
    book = new Book();
    books.add(book.init("Intro to JSF", "Luke", 1295));
    book = new Book();
    books.add(book.init("Intro to Java EE", "Matt", 1395));
  }

  public List<Book> getBookList() {
    return books;
  }
}

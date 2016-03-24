package edu.uco.sharris40.wsp7spencerh.controllers;

import edu.uco.sharris40.wsp7spencerh.models.Book;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value = "bookBean")
@SessionScoped
public class BookBean implements Serializable {
  private static final long serialVersionUID = 2L;

  @Inject
  private BookTable table;

  private List<Book> books;
  private LinkedList<Book> remove;

  public BookBean() {
  }

  @PostConstruct
  private void init() {
    books = table.readBooks();
    remove = new LinkedList<>();
  }

  public List<Book> getBooks() {
    return books;
  }

  public String resetBooks() {
    init();
    return null;
  }

  public String updateBooks() {
    boolean result = true;
    for(Book book : books) {
      if (book.getId() < 0) {
        result = table.createBook(book);
      } else if (book.isChanged()) {
        result = table.updateBook(book);
      }
      if (result)
        book.setChanged(false);
      else
        break;
    }
    if (result) {
      while (!remove.isEmpty()) {
        result = table.deleteBook(remove.getFirst());
        if (result)
          remove.removeFirst();
        else
          break;
      }
    }
    books = table.readBooks();
    if (result && books != null) {
      return null;
    } else {
      return "error";
    }
  }

  public String addBook() {
    books.add(new Book());
    return null;
  }

  public String removeBook(Book book) {
    books.remove(book);
    if (book.getId() > -1)
      remove.add(book);
    return null;
  }
}

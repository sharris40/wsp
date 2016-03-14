
package edu.uco.sharris40.wsp6spencerh;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Named(value = "bookBean")
@RequestScoped
public class BookBean implements Serializable {
  private static final long serialVersionUID = 1L;

  @Inject
  private BookTable table;

  public BookBean() {
  }

  public List<Book> getBooks() {
    return table.readBooks();
  }

  public String updateBooks(List<Book> books) {
    boolean result = true;
    for(Book book : books) {
      if (book.getId() < 0) {
        result = table.createBook(book);
      } else if (book.isChanged()) {
        result = table.updateBook(book);
      }
      if (!result)
        break;
    }
    if (result)
      return null;
    else
      return "error";
  }

  public String removeBook(Book book) {
    if (table.deleteBook(book))
      return null;
    else
      return "error";
  }
}

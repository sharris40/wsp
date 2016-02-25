package edu.uco.sharris40.wsp4spencerh.models;

public class BookItem {
  private Book book = null;
  private int quantity = 0;

  public BookItem init(Book book) {
    if (book == null)
      throw new IllegalArgumentException("book", new NullPointerException());
    this.book = book;
    return this;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    if (book == null)
      throw new IllegalArgumentException("book", new NullPointerException());
    if (this.book == null || !this.book.equals(book))
      quantity = 0;
    this.book = book;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

}

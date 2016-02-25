package edu.uco.sharris40.wsp4spencerh.models;

import java.io.Serializable;

public class BookItem implements Serializable {
  private static final long serialVersionUID = 1L;

  private Book book = null;
  private int quantity = 0;

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
    if (quantity < 0)
      throw new IllegalArgumentException("Quantity must be positive.");
    this.quantity = quantity;
  }

  public boolean remove(int quantity) {
    if (quantity < 0) {
      if (quantity == Integer.MIN_VALUE) {
        this.quantity = Integer.MAX_VALUE;
        return false;
      } else {
        return add(quantity * -1);
      }
    } else {
      this.quantity -= quantity;
      if (this.quantity < 0) {
        this.quantity = 0;
        return false;
      }
      return true;
    }
  }

  public boolean add(int quantity) {
    if (quantity < 0) {
      if (quantity == Integer.MIN_VALUE) {
        this.quantity = 0;
        return true;
      } else {
        return remove(quantity * -1);
      }
    } else {
      this.quantity += quantity;
      if (this.quantity < 0) {
        this.quantity = Integer.MAX_VALUE;
        return false;
      }
      return true;
    }
  }

}

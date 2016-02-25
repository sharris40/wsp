package edu.uco.sharris40.wsp4spencerh.models;

public class Book {
  private String title, author;
  private int price = -1;
  private boolean initialized;

  public Book init(String title, String author, int price) {
    if (title == null)
      throw new IllegalArgumentException("title", new NullPointerException());
    if (author == null)
      throw new IllegalArgumentException("author", new NullPointerException());
    if (price < 0)
      throw new IllegalArgumentException("Price must not be negative.");
    this.title = title;
    this.author = author;
    this.price = price;
    initialized = true;
    return this;
  }

  private void checkRead() {
    if (!initialized)
      throw new IllegalStateException("");
  }

  private void checkWrite() {
    if (!initialized && title != null && author != null && price > -1)
      initialized = true;
  }

  public String getTitle() {
    checkRead();
    return title;
  }

  public void setTitle(String title) {
    if (title == null)
      throw new IllegalArgumentException("title", new NullPointerException());
    this.title = title;
    checkWrite();
  }

  public String getAuthor() {
    checkRead();
    return author;
  }

  public void setAuthor(String author) {
    if (author == null)
      throw new IllegalArgumentException("author", new NullPointerException());
    this.author = author;
    checkWrite();
  }

  public int getPrice() {
    checkRead();
    return price;
  }

  public void setPrice(int price) {
    if (price < 0)
      throw new IllegalArgumentException("Price must not be negative.");
    this.price = price;
    checkWrite();
  }
}

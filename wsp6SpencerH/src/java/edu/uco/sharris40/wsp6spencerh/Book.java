package edu.uco.sharris40.wsp6spencerh;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(min=3, max=20)
  private String title;

  @NotNull
  @Size(min=3, max=20)
  private String author;

  @Min(1)
  @Max(50000)
  private int price;

  @Min(2000)
  @Max(2016)
  private int publicationYear;

  public Book() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
  }

}

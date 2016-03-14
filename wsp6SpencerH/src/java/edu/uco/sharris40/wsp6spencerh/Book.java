package edu.uco.sharris40.wsp6spencerh;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book implements Serializable, Cloneable {
  private static final long serialVersionUID = 2L;

  private int id = -1;

  @NotNull
  @Size(min=3, max=20)
  private String title;

  @NotNull
  @Size(min=3, max=20)
  private String author;

  @Min(1)
  @Max(50000)
  private int price;

  @DecimalMin("0.01")
  @DecimalMax("500.00")
  private String dollarPrice;

  @Min(2000)
  @Max(2016)
  private int publicationYear;

  private boolean changed = true;

  public Book() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
    this.setChanged(true);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    this.setChanged(true);
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
    this.setChanged(true);
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
    this.dollarPrice = Double.toString((double)this.price / 100.);
    this.setChanged(true);
  }

  public String getDollarPrice() {
    return dollarPrice;
  }

  public void setDollarPrice(String dollarPrice) {
    double dollarPriceAsDouble = Double.parseDouble(dollarPrice);
    this.setPrice((int)(dollarPriceAsDouble * 100.));
  }

  public int getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
    this.setChanged(true);
  }

  public boolean isChanged() {
    return changed;
  }

  public void setChanged(boolean changed) {
    this.changed = changed;
  }

  @Override
  public Book clone() throws CloneNotSupportedException {
    return (Book) super.clone();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Book))
      return false;
    Book other = (Book)o;
    return this.id == other.id
        && this.title.equals(other.title)
        && this.author.equals(other.author)
        && this.price == other.price
        && this.publicationYear == other.publicationYear
        && this.changed == other.changed;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 67 * hash + this.id;
    hash = 67 * hash + Objects.hashCode(this.title);
    hash = 67 * hash + Objects.hashCode(this.author);
    hash = 67 * hash + this.price;
    hash = 67 * hash + this.publicationYear;
    hash = 67 * hash + (this.changed ? 1 : 0);
    return hash;
  }

}

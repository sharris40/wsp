package edu.uco.sharris40.wsp7spencerh.impl.models;

import edu.uco.sharris40.wsp7spencerh.models.Order;
import edu.uco.sharris40.wsp7spencerh.models.Book;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderImpl extends HashMap<Book, Integer> implements Order {
  private static final long serialVersionUID = 1L;

  private int id = -1;
  private Date date;

  public OrderImpl() {}

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int getSubtotal(Book book) {
    Integer count = this.get(book);
    if (count != null)
      return count * book.getPrice();
    return 0;
  }

  @Override
  public int getTotal() {
    int total = 0;
    for (Map.Entry<Book, Integer> entry : this.entrySet()) {
      total += entry.getKey().getPrice() * entry.getValue();
    }
    return total;
  }

  @Override
  public String getSubtotalAsString(Book book) {
    int subtotal = this.getSubtotal(book);
    return String.format("$%d.%02d", subtotal / 100, subtotal % 100);
  }

  @Override
  public String getTotalAsString() {
    int total = this.getTotal();
    return String.format("$%d.%02d", total / 100, total % 100);
  }

  @Override
  @SuppressWarnings("CloneDeclaresCloneNotSupported")
  public Object clone() {
    OrderImpl clone = (OrderImpl)super.clone();
    clone.id = this.id;
    return clone;
  }

  @Override
  public Date getDate() {
    return date;
  }

  @Override
  public void setDate(Date date) {
    this.date = date;
  }

}

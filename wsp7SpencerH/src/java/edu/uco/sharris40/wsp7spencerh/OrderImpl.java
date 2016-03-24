package edu.uco.sharris40.wsp7spencerh;

import java.util.HashMap;

public class OrderImpl extends HashMap<Book, Integer> implements Order {
  private static final long serialVersionUID = 1L;

  private int id = -1;

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
    Integer subtotal = this.get(book);
    if (subtotal != null)
      return subtotal;
    return 0;
  }

  @Override
  public int getTotal() {
    int total = 0;
    for (Integer val : this.values()) {
      total += val;
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

}

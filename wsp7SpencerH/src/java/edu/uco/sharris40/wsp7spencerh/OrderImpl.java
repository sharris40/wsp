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

}

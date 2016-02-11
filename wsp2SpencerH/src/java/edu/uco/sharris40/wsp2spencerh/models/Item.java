package edu.uco.sharris40.wsp2spencerh.models;

import java.io.Serializable;

public abstract class Item implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private int count = 0;

  public abstract String getName();
  public abstract int getCost();

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    if (count < 0)
      throw new IllegalArgumentException("count");
    this.count = count;
  }

  public void addToCount(int newItems) {
    this.count += newItems;
  }

  public void removeFromCount(int newItems) {
    this.count -= newItems;
    if (this.count < 0)
      this.count = 0;
  }

  public int getTotal() {
    return getCost() * getCount();
  }

  public String formatCost() {
    return String.format("$%d.%02d", getCost() / 100, getCost() % 100);
  }

  public String formatTotal() {
    return String.format("$%d.%02d", getTotal() / 100, getTotal() % 100);
  }
}

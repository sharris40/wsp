package edu.uco.sharris40.wsp7spencerh.models;

import edu.uco.sharris40.wsp7spencerh.impl.models.OrderImpl;

public class OrderFactory {
  private static final Class<OrderImpl> IMPLEMENTATION = OrderImpl.class;

  public static Order create() {
    try {
      return IMPLEMENTATION.newInstance();
    } catch (InstantiationException | IllegalAccessException ie) {
      throw new AssertionError("Cannot construct Order object.", ie);
    }
  }

  public static Order create(int id) {
    Order newOrder = null;
    try {
      newOrder = IMPLEMENTATION.newInstance();
    } catch (InstantiationException | IllegalAccessException ie) {
      throw new AssertionError("Cannot construct Order object.", ie);
    }
    newOrder.setId(id);
    return newOrder;
  }
}

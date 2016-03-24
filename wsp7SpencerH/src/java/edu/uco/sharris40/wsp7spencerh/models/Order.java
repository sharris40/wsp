package edu.uco.sharris40.wsp7spencerh.models;

import java.util.Date;
import java.util.Map;

public interface Order extends Map<Book, Integer>, Cloneable {
  int getId();
  void setId(int id);
  Date getDate();
  String getDateString();
  void setDate(Date date);
  int getSubtotal(Book book);
  int getTotal();
  String getSubtotalAsString(Book book);
  String getTotalAsString();
}

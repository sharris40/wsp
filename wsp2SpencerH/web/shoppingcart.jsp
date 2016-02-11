<%@page contentType="text/html" pageEncoding="UTF-8" %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
  <head><meta charset="utf-8"/>
<%@page import="edu.uco.sharris40.wsp2spencerh.models.Item" %>
<%@page import="edu.uco.sharris40.wsp2spencerh.models.Pencil" %>
<%@page import="edu.uco.sharris40.wsp2spencerh.models.USBDrive" %>
<%@page import="edu.uco.sharris40.wsp2spencerh.models.Eraser" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

    <title>Shopping Cart</title>
    <link rel="stylesheet" href="Styles/cart.css"/>
  </head>
<%
List<Item> cart = (List<Item>)session.getAttribute("cart");
if (cart == null) {
  cart = new ArrayList<>();
  session.setAttribute("cart", cart);
}
Pencil pencils = null;
USBDrive usbDrives = null;
Eraser erasers = null;
int total = 0;
for (Item item : cart) {
  if (item instanceof Pencil) {
    pencils = (Pencil) item;
  } else if (item instanceof USBDrive) {
    usbDrives = (USBDrive) item;
  } else if (item instanceof Eraser) {
    erasers = (Eraser) item;
  }
}
/*if (request.getParameter("buy_pencil") != null) {
  if (pencils == null) {
    pencils = new Pencil();
    cart.add(pencils);
  }
  pencils.addToCount(1);
}
if (request.getParameter("buy_usb") != null) {
  if (usbDrives == null) {
    usbDrives = new USBDrive();
    cart.add(usbDrives);
  }
  usbDrives.addToCount(1);
}
if (request.getParameter("buy_eraser") != null) {
  if (erasers == null) {
    erasers = new Eraser();
    cart.add(erasers);
  }
  erasers.addToCount(1);
}*/
pencils = new Pencil();
cart.add(pencils);
pencils.addToCount(1);
usbDrives = new USBDrive();
cart.add(usbDrives);
usbDrives.addToCount(1);
erasers = new Eraser();
cart.add(erasers);
erasers.addToCount(1);
%>
  <body>
    <div class="contents">
      <h3>The list of items in the shopping cart</h3>
<% if (pencils == null && usbDrives == null && erasers == null) { %>
      <p>Shopping cart is empty! Buy more!</p>
<% } else { %>
      <table class="listings">
        <thead>
          <tr>
            <th>Item</th>
            <th>Count</th>
            <th>Unit Price</th>
            <th>SubTotal</th>
          </tr>
        </thead>
        <tbody>
<%   for (Item item : cart) { %>
<%     total += item.getTotal(); %>
        <tr>
          <td><%= item.getName() %></td>
          <td><%= item.getCount() %></td>
          <td><%= item.formatCost() %></td>
          <td><%= item.formatTotal() %></td>
        </tr>
<%   } %>
      </table>
      <p><%= String.format("Total = $%d.%02d", total / 100, total % 100) %></p>
<% } %>
      <a href="index.html">Go Home</a>
    </div>
  </body>
</html>

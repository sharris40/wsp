<%-- 
    Document   : loginFailed
    Created on : Jan 27, 2016, 10:08:47 PM
    Author     : dpbjinc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
  <head><meta charset="UTF-8"/>
    <title>Login Failed</title>
  </head>
  <body>
    <h1>Login Failed</h1>
    <p>Either user <b><%= request.getParameter("username") %></b> does not exist, or you have entered an incorrect password for <b><%= request.getParameter("username") %></b>.</p>
    <p><a href="index.html">Click here</a> to enter your credentials again.</p>
  </body>
</html>

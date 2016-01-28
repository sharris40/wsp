<%-- 
    Document   : loginFailed
    Created on : Jan 27, 2016, 10:08:47 PM
    Author     : Spencer Harris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
  <head><meta charset="UTF-8"/>
    <title>Login Failed</title>
    <link rel="stylesheet" href="Styles/login.css"/>
  </head>
  <body>
    <div class="container">
      <div class="vc">
        <div class="login">
          <h1>Login Failed</h1>

<% String user = request.getParameter("username"); %>
<% if (user != null && !user.isEmpty()) { %>
          <p class="message">Either user <b><%= request.getParameter("username") %></b> does not exist,
            or you have entered an incorrect password for <b><%= request.getParameter("username") %></b>.</p>
<% } %>

          <p class="message"><a href="index.html">Click here</a> to enter your credentials again.</p>
        </div>
      </div>
    </div>
  </body>
</html>
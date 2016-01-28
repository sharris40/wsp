/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sharris40.wsp1spencerh;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dpbjinc
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String user = request.getParameter("username");
    String pass = request.getParameter("password");
    String location = null;
    if (user == null || pass == null) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
    } else if (pass.equals("secret")) {
      switch (user.toLowerCase(Locale.US)) {
        case "sung":
          location = "http://cs3.uco.edu/";
          break;
        case "cs":
          location = "http://cs.uco.edu/";
          break;
        default:
          response.sendRedirect(response.encodeRedirectURL("index.html"));
      }
    } else {
      response.sendRedirect(response.encodeRedirectURL("index.html"));
    }
    if (location != null) {
      response.setStatus(HttpServletResponse.SC_SEE_OTHER);
      response.setHeader("Location", location);
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter()) {
        out.println("<!DOCTYPE html>");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-US\" lang=\"en-US\">");
        out.println("  <head><meta charset=\"UTF-8\"/>");
        out.println("    <meta http-equiv=\"refresh\" content=\"5; url=" + location + "\"/>");
        out.println("    <title>Redirecting&#x2026;</title>");      
        out.println("  </head>");
        out.println("  <body>");
        out.println("    <h1>Redirecting&#x2026;</h1>");
        out.println("    <p>You are now being redirected to your home page.</p>");
        out.println("    <p>If you are not automatically redirected, <a href=\"" + location + "\">click here</a> to visit your home page.</p>");
        out.println("  </body>");
        out.println("</html>");
      }
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Processes login information and redirects user to an appropriate location.";
  }

}

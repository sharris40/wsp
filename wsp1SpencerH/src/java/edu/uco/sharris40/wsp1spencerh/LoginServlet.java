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
 * Handles login for the site.
 * @author Spencer Harris
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
    //response.sendError(HttpServletResponse.SC_FORBIDDEN);
    doPost(request, response);
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
    //String user = request.getParameter("username");
    //String pass = request.getParameter("password");
    String location = "http://example.com/";
    /*if (user == null || pass == null) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
    } else if (pass.equals("secret")) {
      switch (user.toLowerCase(Locale.US)) {
        case "sung":
          response.setStatus(HttpServletResponse.SC_SEE_OTHER);
          location = "http://cs3.uco.edu/";
          break;
        case "cs":
          response.setStatus(HttpServletResponse.SC_SEE_OTHER);
          location = "http://cs.uco.edu/";
          break;
        default:
          response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
          location = response.encodeRedirectURL("loginFailed.jsp");
      }
    } else {
      response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
      location = response.encodeRedirectURL("loginFailed.jsp");
    }*/
    if (location != null) {
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
        out.println("    <p>You are now being redirected.</p>");
        out.println("    <p>If you are not automatically redirected in five seconds, <a href=\"" + location + "\">click here</a> to continue.</p>");
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

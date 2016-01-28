/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sharris40.wsp1spencerh;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;

/**
 *
 * @author dpbjinc
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
  
  private String toLowerCaseASCII(String input) {
    char[] chars = input.toCharArray();
    for (int i = 0; i < chars.length; ++i) {
      if (chars[i] >= 'A' && chars[i] <= 'Z') {
        chars[i] += 0x20;
      }
    }
    return new String(chars);
  }

  private String remapCharset(String charset) {
    switch (toLowerCaseASCII(charset)) {
      case "iso-ir-6":
      case "ansi_x3.4-1968":
      case "ansi_x3.4-1986":
      case "iso_646.irv:1991":
      case "iso646-us":
      case "us-ascii":
      case "us":
      case "ibm367":
      case "cp367":
      case "csascii":
        return "us-ascii";
      case "utf-8":
      case "csutf8":
        return "utf-8";
      default:
        return "";
    }
  }
  
  private boolean useXML(String header) {
    List<AcceptableMediaType> types = null;
    try {
      types = HttpHeaderReader.readAcceptMediaType(header);
    } catch (ParseException ex) {
      Logger.getLogger(LoginServlet.class.getName()).log(Level.WARNING, "Received invalid Accept header from client; using default media type.", ex);
      return true;
    }
    for (AcceptableMediaType type : types) {
      String mainType = type.getType();
      String subType = type.getSubtype();
      int qValue = type.getQuality();
      Map<String, String> params = type.getParameters();
      boolean hasExtraParam = false;
      String charset = null;
      for (String key : params.keySet()) {
        if (toLowerCaseASCII(key).equals("charset")) {
          charset = remapCharset(params.get(key));
        } else {
          hasExtraParam = true;
        }
      }
      switch (mainType) {
        case "*":
          if (subType.equals("*")) {
            if (!hasExtraParam && charset != "") {
              return type;
            } else if 
          }
          break;
        case "application":
          break;
        case "text":
          break;
        default:
          break;
      }
    }
    return backupType;
  }

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet LoginServlet</title>");      
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    processRequest(request, response);
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
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}

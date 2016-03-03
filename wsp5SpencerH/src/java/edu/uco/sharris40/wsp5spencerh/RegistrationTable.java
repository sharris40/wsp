/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sharris40.wsp5spencerh;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

/**
 *
 * @author dpbjinc
 */
@Named(value = "registrationTable")
@ApplicationScoped
public class RegistrationTable implements Serializable {
  private static final long serialVersionUID = 1L;
  @Resource(name="jdbc/WSP5")
  private DataSource ds;

  /**
   * Creates a new instance of RegistrationTable
   */
  public RegistrationTable() {
  }

  public List<RegistrationBean> getEntries() {
    LinkedList<RegistrationBean> resultList = new LinkedList<>();

    if (ds == null)
      return null;
    Connection connection = null;
    try {
      connection = ds.getConnection();
    } catch (SQLException se) {
      se.printStackTrace(System.err);
    }
    if (connection == null)
      return null;

    try {
      Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(
              "SELECT lastName, firstName, email, "
                      + "phone, male, langCPlusPlus, langJava, langCSharp, "
                      + "langSwift, langPython, hometown "
                  + "FROM registration");
      while (results.next()) {
        RegistrationBean bean = new RegistrationBean();
        bean.setLastName(results.getNString(1));
        bean.setFirstName(results.getNString(2));
        bean.setEmail(results.getString(3));
        long phone = results.getLong(4);
        StringBuilder phoneBuilder = new StringBuilder(Long.toString(phone));
        phoneBuilder.insert(3, '\u2012');
        phoneBuilder.insert(7, '\u2012');
        bean.setPhoneNumber(phoneBuilder.toString());
        bean.setGender((results.getBoolean(5)) ? RegistrationBean.MALE
                                               : RegistrationBean.FEMALE);
        ArrayList<String> languages = new ArrayList<>(5);
        if (results.getBoolean(6)) {
          languages.add("C++");
        }
        if (results.getBoolean(7)) {
          languages.add("Java");
        }
        if (results.getBoolean(8)) {
          languages.add("C#");
        }
        if (results.getBoolean(9)) {
          languages.add("Swift");
        }
        if (results.getBoolean(10)) {
          languages.add("Python");
        }
        bean.setLanguages(languages);
        bean.setHometown(results.getNString(11));
        resultList.add(bean);
      }

    } catch (SQLException se) {
      se.printStackTrace(System.err);
      return null;
    }
    try {
      connection.close();
    } catch (SQLException se) {
      se.printStackTrace(System.err);
      return null;
    }
    return resultList;
  }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.sharris40.wsp3spencerh;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author dpbjinc
 */
@Named(value = "registrationData")
@SessionScoped
public class RegistrationBean implements Serializable {
  private static final List<String> LANGUAGES, TOWNS;
  private static final String NEXT_SUCCESS = "register";
  public static final Pattern PHONE_PATTERN;
  public static final Pattern NAME_PATTERN;
  public static final int PASSWORD_MIN_LENGTH = 4;
  public static final String FEMALE = "F";
  public static final String MALE = "M";

  static {
    LANGUAGES = new ArrayList<>(5);
    LANGUAGES.add("C++");
    LANGUAGES.add("Java");
    LANGUAGES.add("C#");
    LANGUAGES.add("Swift");
    LANGUAGES.add("Python");
    TOWNS = new ArrayList<>(7);
    TOWNS.add("-- choose --");
    TOWNS.add("Edmond");
    TOWNS.add("Oklahoma City");
    TOWNS.add("Tulsa");
    TOWNS.add("Moore");
    TOWNS.add("Norman");
    TOWNS.add("Yukon");
    TOWNS.add("Lawrence");
    TOWNS.add("Wichita");
    TOWNS.add("other");
    PHONE_PATTERN = Pattern.compile("\\d{3}[-\u2012]\\d{3}[-\u2012]\\d{4}");
    NAME_PATTERN = Pattern.compile("\\p{Alpha}+( \\p{Alpha}+)*");
  }

  public static String getDefaultHometown() {
    return TOWNS.get(0);
  }

  private String firstName, lastName;
  private char[] password;
  private String email;
  private String phoneNumber;
  private boolean female, male;
  private ArrayList<String> languages;
  private String hometown;

  /**
   * Creates a new instance of RegistrationBean
   */
  public RegistrationBean() {
  }

  @PostConstruct
  protected void init() {
    firstName = "";
    lastName = "";
    password = new char[]{};
    email = "";
    phoneNumber = "";
    female = false;
    male = false;
    languages = new ArrayList<>();
    hometown = getDefaultHometown();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    if (firstName == null)
      this.firstName = "";
    else
      this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    if (lastName == null)
      this.lastName = "";
    else
      this.lastName = lastName;
  }

  public void setPassword(String password) {
    Arrays.fill(this.password, '\0');
    if (password != null) {
      this.password = new char[password.length()];
      System.gc();
      for (int i = 0; i < this.password.length; ++i)
        this.password[i] = password.charAt(i);
    } else {
      this.password = new char[0];
      System.gc();
    }
  }

  public String getPassword() {
    return new String(this.password);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (email == null)
      this.email = "";
    else
      this.email = email;
  }

  public String getPhoneNumber() {
    if (phoneNumber == null || phoneNumber.isEmpty())
      return "###-###-####";
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    if (phoneNumber == null)
      this.phoneNumber = "";
    else
      this.phoneNumber = phoneNumber;
  }

  public String getGender() {
    if (this.female)
      return FEMALE;
    else if (this.male)
      return MALE;
    else
      return "";
  }

  public void setGender(String gender) {
    if (gender != null) {
      switch (gender) {
        case "F":
          this.female = true;
          this.male = false;
          break;
        case "M":
          this.male = true;
          this.female = false;
          break;
      }
    }
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(ArrayList<String> languages) {
    if (languages != null)
      this.languages = languages;
  }

  public String getHometown() {
    return hometown;
  }

  public void setHometown(String hometown) {
    if (hometown == null || hometown.isEmpty())
      this.hometown = getDefaultHometown();
    else
      this.hometown = hometown;
  }

  public List<String> getLanguageList() {
    return LANGUAGES;
  }

  public List<String> getHometownList() {
    return TOWNS;
  }

  private static void addError(FacesContext context, UIComponent container,
          String componentId, String message, String detail) {
    String clientId = container.findComponent(componentId).getClientId();
    FacesMessage fm
            = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
    context.addMessage(clientId, fm);
  }

  public String submit() {
    String next = NEXT_SUCCESS;
    FacesContext context = FacesContext.getCurrentInstance();
    UIComponent form = context.getViewRoot().findComponent("registration");

    if (lastName.isEmpty()) {
      next = "";
      addError(context, form, "lastName", "Invalid name.",
              "Please enter a last name.");
    } else if (!NAME_PATTERN.matcher(lastName).matches()) {
      next = "";
      addError(context, form, "lastName", "Invalid name.",
              "Please use alphabetic characters (A through Z and a through z) only for your last name. You can use spaces to separate words.");
    }

    if (firstName.isEmpty()) {
      next = "";
      addError(context, form, "firstName", "Invalid name.",
              "Please enter a first name.");
    } else if (!NAME_PATTERN.matcher(firstName).matches()) {
      next = "";
      addError(context, form, "firstName", "Invalid name.",
              "Please use alphabetic characters (A through Z and a through z) only for your first name. You can use spaces to separate words.");
    }

    if (password.length < PASSWORD_MIN_LENGTH) {
      next = "";
      addError(context, form, "password", "Invalid password.",
              "Your password must be at least four characters long.");
    }

    if (email.isEmpty()) {
      next = "";
      addError(context, form, "email", "Invalid email.",
              "Please enter an email address.");
    } else if (!email.contains("@")) {
      next = "";
      addError(context, form, "email", "Invalid email.",
              "Please enter a valid email address. Valid email addresses must contain an at sign (@).");
    }

    if (phoneNumber.isEmpty()) {
      next = "";
      addError(context, form, "phone", "Invalid phone number.",
              "Please enter a phone number.");
    } else if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
      next = "";
      addError(context, form, "phone", "Invalid phone number.",
              "Please enter a phone number in the format \u201C###\u2012###\u2012####\u201D, where \u201C#\u201D is any digit (0 through 9).");
    }

    if (!female && !male) {
      next = "";
      addError(context, form, "gender", "Gender not specified.",
              "Please choose a gender (female or male).");
    }

    if (languages.isEmpty()) {
      next = "";
      addError(context, form, "languages", "Language not specified.",
              "Please choose at least one language.");
    }

    if (hometown.isEmpty() || hometown.startsWith("-")) {
      next = "";
      addError(context, form, "town", "Hometown not specified.",
              "Please choose a hometown from the menu.");
    }

    return next;
  }

}

package edu.byu.isys413.data.web;

import javax.servlet.*;

/**
 * A simple exception to indicate web exceptions.  If you throw
 * this exception from anywhere in a servlet or JSP file,
 * Tomcat will call the error.jsp page.  See error.jsp for
 * the web.xml entry to enable this functionality.
 *
 * @author Conan C. Albrecht
 */
public class WebException extends ServletException {
  
  public WebException() {
    super();
  }
  
  public WebException(String message) {
    super(message);
  }
  
  public WebException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public WebException(Throwable cause) {
    super(cause);
  }
  
}

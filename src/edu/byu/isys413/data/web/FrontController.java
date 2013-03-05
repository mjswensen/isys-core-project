package edu.byu.isys413.data.web;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The primary controller for all actions in the system.  This
 * controller looks at the URL and calls the appropriate Action
 * given in the URL.  This object must implement the Action.java
 * interface.
 *
 * To enable this controller, do the following:
 *
 *  1. Modify the package statement above to match your packages.
 *     Be sure this file is in the appropriate directory that matches
 *     the package statement.
 *
 *  2. Modify your web.xml file with the following code (note
 *     you'll need to modify the package to match #1):
            <servlet>
                <servlet-name>FrontController</servlet-name>
                <servlet-class>edu.byu.isys413.cca.web.FrontController</servlet-class>
            </servlet>
            <servlet-mapping>
                <servlet-name>FrontController</servlet-name>
                <url-pattern>*.action</url-pattern>
            </servlet-mapping>
 *     The above XML commands specify that any URL ending with
 *     the string ".action" should call the Controller.java
 *     controller.
 *
 * This system is similar to Struts, only simpler.  It is not
 * entirely secure.  In the real world, you'd probably want 
 * to ues Jave Server Faces or Struts or Tapestry or some other 
 * framework.
 * 
 * @author Conan C. Albrecht
 * @version 1.0
 */
public class FrontController extends HttpServlet {
  
   
  /**
   *  A front controller that discovers the action based on the URL, calls the action,
   *  and forwards to the appropriate JSP page.
   */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher dispatcher = null;
    try {
      // get the action class to be called
      // to do this, we grab the URL and take the ".action" off of it
      String servletpath = request.getServletPath().substring(1);
      int dot = servletpath.lastIndexOf(".action");
      if (dot <= 0) {
        throw new ServletException("Invalid action.  No method specified");
      }
      String className = servletpath.substring(0, dot);
      
      // get an instance of the action
      Class klass = Class.forName(className);
      Action action = (Action)klass.newInstance();

      // call the action 
      String viewPath = action.process(request, response);
      
      // get a dispatcher to the view (if the action returned something besides null)
      if (viewPath != null) {
        dispatcher = request.getRequestDispatcher(viewPath);
      }
      
    }catch (Exception e) {
      // this is wierd.  from a servlet, tomcat seems to inspect the embedded
      // exception, not the main exception.  so i'm embedding a WebException
      // inside a regular ServletException.  this makes tomcat (per web.xml)
      // call the /error.jsp page.
      if (e instanceof WebException) {  // already a WebException
        throw new ServletException(e);
      }else{  // wrap it in one
        throw new ServletException(new WebException("Error in controller: " + e.getMessage(), e));
      }//if
    }
    
    // forward to the JSP page
    if (dispatcher != null) {
      dispatcher.forward(request, response);
    }
    
  }
  
}

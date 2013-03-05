

package edu.byu.isys413.data.actions;

import edu.byu.isys413.data.web.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * An example action for a number game.
 * @author conan
 */
public class NumberGame implements Action {
  
  Random random = new Random();
  
  /** Constructor */
  public NumberGame() {
      // no op
  }
  
  /**
   *  Responds to an action call from the Controller.java file.
   *  This method should perform any work required, then return
   *  a new JSP page to call.
   *
   *  @param request  The HttpServletRequest object that represents
   *                  information sent from the browser.  The getParameter()
   *                  method is particularly useful.
   *
   *  @returns        A string giving the path of the JSP file to call
   *                  after this action is performed.  If you need this
   *                  to be dynamic, use hidden form field to send
   *                  a parameter giving the page to go to after the
   *                  action.  
   *
   *                  If the path starts with "/", the path is 
   *                  absolute to the application context.  If the 
   *                  path doesn't start with "/", it is relative
   *                  to the current page (dangerous!).
   */
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // ensure we have a number to guess for
    HttpSession session = request.getSession();
    int number;
    if (session.getAttribute("number") == null) {
      number = random.nextInt(10) + 1;
      session.setAttribute("number", new Integer(number));
    }else{
      Integer temp = (Integer)session.getAttribute("number");
      number = temp.intValue();
    }//if
    
    // ensure we have a history
    if (session.getAttribute("history") == null) {
       session.setAttribute("history", new LinkedList<String>());
    }//if

    // if we have a guess, check it against the number
    if (request.getParameter("guess") != null) {
      int guess;
      try {
        guess = Integer.parseInt(request.getParameter("guess"));
      }catch (NumberFormatException e) {
        throw new WebException("Please enter a valid number.");
      }//try
      if (guess < number) {
        request.setAttribute("message", "Try Higher");
        ((List<String>)session.getAttribute("history")).add(request.getParameter("guess"));
      }else if (guess > number) {
        request.setAttribute("message", "Try Lower");
        ((List<String>)session.getAttribute("history")).add(request.getParameter("guess"));
      }else{
        request.setAttribute("message", "You got it!.  Let's play again.");
        session.removeAttribute("number");  // forces it to be recreated next time
        session.setAttribute("history", new LinkedList<String>());  // reset the history
      }//if
    }//if


    // forward to the correct JSP file
    return "numbergame.jsp";
    
  }//process
  
  
  
}//class

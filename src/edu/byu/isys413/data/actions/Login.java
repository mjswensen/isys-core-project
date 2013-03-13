package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.web.*;
import edu.byu.isys413.data.*;

public class Login implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public Login() {}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Authenticate the customer.
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Customer cust = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", email), new SearchCriteria("password", password));
		
		if(cust == null) {
			request.setAttribute("title", "Error");
			request.setAttribute("messageType", "error");
			request.setAttribute("message", "Incorrect username/password. Please go back and try again.");
			return "message.jsp";
		} else {
			request.getSession().setAttribute("cust", cust);
			return "products.jsp";
		}
		
	}

}

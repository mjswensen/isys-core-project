package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import edu.byu.isys413.data.models.*;
import edu.byu.isys413.data.web.*;

public class Login implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public Login() {}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Authenticate the customer.
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Customer cust = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", email), new SearchCriteria("password", password));
		
		if(request.getParameter("format").equals("json")) {
			// This request came from the Android app.
			JSONObject json = new JSONObject();
			if(cust == null) {
				json.put("status", "fail");
			} else {
				json.put("status", "success");
				// Get images here.
			}
			request.setAttribute("json", json);
			return "json.jsp";
		} else {
			// This request came from the web site.
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

}

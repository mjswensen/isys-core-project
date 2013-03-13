package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.web.*;

public class Purchase implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getSession().getAttribute("cust") == null) {
			request.setAttribute("message", "Please log in.");
			request.setAttribute("messageType", "error");
			return "message.jsp";
		}
		
		try {
			if(request.getParameter("storeproductid") != null && !request.getParameter("storeproductid").equals("")) {
				
			} else if(request.getParameter("physicalproductid") != null && !request.getParameter("physicalproductid").equals("")) {
				
			}
		} catch(Exception e) {
			request.setAttribute("message", e.getMessage());
			request.setAttribute("messageType", "error");
			return "message.jsp";
		}
		
		request.setAttribute("message", "Your purchase was processed successfully!");
		request.setAttribute("messageType", "success");
		return "message.jsp";
	}

}

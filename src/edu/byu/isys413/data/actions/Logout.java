package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.web.*;

public class Logout implements Action {
	
	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public Logout() {}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.getSession().removeAttribute("cust");
		request.getSession().invalidate();
		
		return "index.jsp";
	}

}

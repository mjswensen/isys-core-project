package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.Picture;
import edu.byu.isys413.data.web.Action;

public class UploadPicture implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String picData = request.getParameter("picData");
		String caption = request.getParameter("caption");
		
		try {
			// Save the picture
			Picture pic = BusinessObjectDAO.getInstance().create("Picture");
			pic.setCustomer((Customer) request.getSession().getAttribute("cust"));
			pic.setCaption(caption);
			pic.setPicData(picData);
			pic.save();
			request.setAttribute("json", "{'status':'success'}");
		} catch(Exception e) {
			
		}
		
		return "json.jsp";
	}

}

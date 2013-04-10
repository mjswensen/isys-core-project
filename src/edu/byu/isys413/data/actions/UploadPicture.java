package edu.byu.isys413.data.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.Picture;
import edu.byu.isys413.data.models.SearchCriteria;
import edu.byu.isys413.data.web.Action;

/**
 * UploadPicture supports the mobile app's option to upload a picture to the database.
 *
 */
public class UploadPicture implements Action {

	/* @see edu.byu.isys413.data.web.Action#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)*/
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String picData = request.getParameter("picData");
		String caption = request.getParameter("caption");
		
		JSONObject json = new JSONObject();
		try {
			// Get the customer out of the session
			Customer cust = (Customer) request.getSession().getAttribute("cust");
			
			// Save the picture
			Picture pic = BusinessObjectDAO.getInstance().create("Picture");
			pic.setCustomer(cust);
			pic.setCaption(caption);
			pic.setPicData(picData);
			pic.save();
			
			// Provide an updated pic list
			
			JSONArray ja = new JSONArray();
			List<Picture> pics = BusinessObjectDAO.getInstance().searchForList("Picture", new SearchCriteria("customerid", cust.getId()));
			for(Picture picture : pics) {
				JSONObject picJo = new JSONObject();
				picJo.put("id", picture.getId());
				picJo.put("caption", picture.getCaption());
				ja.put(picJo);
			}
			// Add status and pictures to JSON object
			json.put("pics", ja);
			
		} catch(Exception e) {
			// TODO: send an error message back to the Android client.
		}
		request.setAttribute("json", json);
		return "json.jsp";
	}

}

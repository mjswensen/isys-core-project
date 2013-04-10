package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Picture;
import edu.byu.isys413.data.web.Action;

/**
 * PicData retrieves the base64 encoded picture stream from the database for a picture
 * requested by the mobile app.
 *
 */
public class PicData implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public PicData() {}
	
	/* @see edu.byu.isys413.data.web.Action#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)*/
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String picId = request.getParameter("picId");
		Picture pic = BusinessObjectDAO.getInstance().read(picId);
		JSONObject json = new JSONObject();
		json.put("picData", pic.getPicData());
		
		request.setAttribute("json", json);
		
		return "json.jsp";
	}

}

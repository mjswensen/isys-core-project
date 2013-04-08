package edu.byu.isys413.data.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import edu.byu.isys413.data.web.*;
import edu.byu.isys413.data.models.*;

public class PicData implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public PicData() {}
	
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

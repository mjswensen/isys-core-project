package edu.byu.isys413.data.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.byu.isys413.data.web.*;
import edu.byu.isys413.data.*;

public class ProductList implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String query = request.getParameter("q");
		List<ConceptualProduct> prods = BusinessObjectDAO.getInstance().searchForList("ConceptualProduct", new SearchCriteria("name", "%" + query + "%", SearchCriteria.LIKE), new SearchCriteria("description", "%" + query + "%", SearchCriteria.LIKE));
		JSONArray json = new JSONArray();
		
		for(ConceptualProduct cp : prods) {
			JSONObject obj = new JSONObject();
			obj.put("name", cp.getName());
			obj.put("description", cp.getDescription());
			json.put(obj);
		}
		
		request.setAttribute("json", json);
		
		return "product-list.jsp";
	}

}

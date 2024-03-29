package edu.byu.isys413.data.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.ConceptualProduct;
import edu.byu.isys413.data.models.SearchCriteria;
import edu.byu.isys413.data.web.Action;

/**
 * ProductList returns a list of Conceptual Producst that match a search criteria specified
 * by a customer using the website. 
 *
 */
public class ProductList implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public ProductList() {}
	
	/* @see edu.byu.isys413.data.web.Action#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)*/
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String query = request.getParameter("q");
		List<ConceptualProduct> prods = BusinessObjectDAO.getInstance().searchForList("ConceptualProduct", new SearchCriteria("name", "%" + query + "%", SearchCriteria.LIKE));
		JSONArray json = new JSONArray();
		
		for(ConceptualProduct cp : prods) {
			JSONObject obj = new JSONObject();
			obj.put("id", cp.getId());
			obj.put("name", cp.getName());
			obj.put("description", cp.getDescription());
			obj.put("manufacturer", cp.getManufacturer());
			json.put(obj);
		}
		
		request.setAttribute("json", json);
		
		return "json.jsp";
	}

}

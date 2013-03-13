package edu.byu.isys413.data.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.byu.isys413.data.web.*;
import edu.byu.isys413.data.*;

public class ProductDetails implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public ProductDetails() {}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String cpId = request.getParameter("id");
		
		ConceptualProduct cp = BusinessObjectDAO.getInstance().searchForBO("ConceptualProduct", new SearchCriteria("id", cpId));
		List<StoreProduct> sps = cp.getStoreProducts();
		List<PhysicalProduct> pps = BusinessObjectDAO.getInstance().searchForList("PhysicalProduct", new SearchCriteria("conceptualproductid", cpId));
		
		request.setAttribute("cp", cp);
		request.setAttribute("sps", sps);
		request.setAttribute("pps", pps);
		
		return "product-details.jsp";
	}

}

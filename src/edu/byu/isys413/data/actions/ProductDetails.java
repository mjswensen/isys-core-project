package edu.byu.isys413.data.actions;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.ConceptualProduct;
import edu.byu.isys413.data.models.ForSale;
import edu.byu.isys413.data.models.PhysicalProduct;
import edu.byu.isys413.data.models.SearchCriteria;
import edu.byu.isys413.data.models.StoreProduct;
import edu.byu.isys413.data.web.Action;

/**
 * ProductDetails retrieves the Conceptual, Store, and Physical products, along
 * with associated For Sale objects from the database for a product specified by a 
 * customer using the website. 
 *
 */
public class ProductDetails implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public ProductDetails() {}
	
	/* @see edu.byu.isys413.data.web.Action#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)*/
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String cpId = request.getParameter("id");
		
		ConceptualProduct cp = BusinessObjectDAO.getInstance().searchForBO("ConceptualProduct", new SearchCriteria("id", cpId));
		List<StoreProduct> sps = cp.getStoreProducts();
		List<PhysicalProduct> pps = BusinessObjectDAO.getInstance().searchForList("PhysicalProduct", new SearchCriteria("conceptualproductid", cpId));
		List<ForSale> fss = new LinkedList<ForSale>();
		for(PhysicalProduct pp : pps) {
			if(pp.isAvailable() && pp.getType().equals("ForSale")) {
				fss.add((ForSale) BusinessObjectDAO.getInstance().searchForBO("ForSale", new SearchCriteria("id", pp.getId())));
			}
		}
		
		request.setAttribute("cp", cp);
		request.setAttribute("sps", sps);
		request.setAttribute("fss", fss);
		
		return "product-details.jsp";
	}

}

package edu.byu.isys413.data.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.web.*;
import edu.byu.isys413.data.*;

public class Purchase implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public Purchase() {}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getSession().getAttribute("cust") == null) {
			request.setAttribute("message", "Please log in.");
			request.setAttribute("messageType", "error");
			return "message.jsp";
		}
		
		Customer cust = (Customer) request.getSession().getAttribute("cust");
		
		try {
			if(request.getParameter("storeproductid") != null && !request.getParameter("storeproductid").equals("")) {
				
				String spId = request.getParameter("storeproductId");
				int qty = Integer.parseInt(request.getParameter("quantity"));
				
				// Get the objects needed to create the transaction
				StoreProduct sp = BusinessObjectDAO.getInstance().read(spId);
				
				// Create and save initially the transaction
				Transaction trans = BusinessObjectDAO.getInstance().create("Transaction");
				trans.setCustomer(cust);
				trans.setStore(sp.getStore());
				trans.setEmployee(sp.getStore().getManager());// Commissions go to managers for online sales.
				trans.setDate(new Date());
				trans.save();
				
				// Create sale and add it to transaction
				Sale sale = BusinessObjectDAO.getInstance().create("Sale");
				sale.setTransaction(trans);
				sale.setProduct(sp.getConceptualProduct());
				sale.setQuantity(qty);
				sale.save();
				
				// Finalize transaction
				trans.finalizeAndSave();
				
			} else if(request.getParameter("physicalproductid") != null && !request.getParameter("physicalproductid").equals("")) {
				
				String ppId = request.getParameter("physicalproductid");
				
				// Get the objects needed to create the transaction
				PhysicalProduct pp = BusinessObjectDAO.getInstance().read(ppId);
				
				// Create and save initially the transaction
				Transaction trans = BusinessObjectDAO.getInstance().create("Transaction");
				trans.setCustomer(cust);
				trans.setStore(pp.getStore());
				trans.setEmployee(pp.getStore().getManager());
				trans.setDate(new Date());
				trans.save();
				
				// Create sale and add it to transaction
				Sale sale = BusinessObjectDAO.getInstance().create("Sale");
				sale.setTransaction(trans);
				sale.setProduct(pp);
				sale.setQuantity(1);
				sale.save();
				
				// Finalize transaction
				trans.finalizeAndSave();
			}
			
			/**
			 * At this point we would send an email to the store
			 * to inform the manager of the sale and to instruct
			 * on shipping according to the customer's selection.
			 */
			
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

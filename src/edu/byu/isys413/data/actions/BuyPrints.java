package edu.byu.isys413.data.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import edu.byu.isys413.data.models.*;
import edu.byu.isys413.data.web.Action;

public class BuyPrints implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject json = new JSONObject();
		try {
			// Get related business objects
			Store s = BusinessObjectDAO.getInstance().read("store1");
			Customer c = (Customer) request.getSession().getAttribute("cust");
			Print pr = BusinessObjectDAO.getInstance().read("print1");
			
			// Create transaction
			Transaction t = BusinessObjectDAO.getInstance().create("Transaction");
			t.setCustomer(c);
			t.setStore(s);// Store 1 will be our default store for charging tax
			t.setEmployee(s.getManager());// We will pay commissions to the manager of the default store.
			t.setDate(new Date());
			
			t.save();
			
			// guids will either have one element (from buyShownPicture) or multiple.
			String[] guids = request.getParameterValues("guid");
			for(String guid : guids) {
				Picture pic = BusinessObjectDAO.getInstance().read(guid);
				PrintOrder po = BusinessObjectDAO.getInstance().create("PrintOrder");
				po.setTransaction(t);
				po.setPicture(pic);
				po.setPrint(pr);
				po.setQuantity(1);
				po.save();
			}
			
			t.finalizeAndSave();
			json.put("message", "Your purchase was processed successfully. Your prints will be shipped to you within a few business days.");
			
		} catch (DataException e) {
			json.put("message", "There was a problem processing your purchase. Please try again.");
		}
		
		request.setAttribute("json", json);
		return "json.jsp";
	}

}

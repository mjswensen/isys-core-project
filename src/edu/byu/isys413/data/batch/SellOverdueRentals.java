package edu.byu.isys413.data.batch;

import java.util.Date;
import java.util.List;
import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Fee;
import edu.byu.isys413.data.models.ForRent;
import edu.byu.isys413.data.models.ForSale;
import edu.byu.isys413.data.models.Rental;
import edu.byu.isys413.data.models.Sale;
import edu.byu.isys413.data.models.Transaction;

/**
 * Program to be run nightly to charge the credit card of customers once
 * their rental is overdue 10 days (the replacement price).
 * 
 * @author mswensen
 */
public class SellOverdueRentals {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			List<Rental> rentals = BusinessObjectDAO.getInstance().searchForAll("Rental");
			int counter = 0;
			for(Rental r : rentals) {
				if(r.isLate() && r.getLatePeriod() >= 10) {
					
					/*
					 *  Dr. Albrecht's Dynamic DAO does not support Dr. Hansen's idea of
					 *  changing a ForRent to ForSale, so we will ignore that aspect for
					 *  now and keep it a ForRent.
					 */
					ForRent fr = r.getForRent();
					fr.setType("ForSale");
					fr.setPrice(fr.getConceptualProduct().getConceputalRental().getReplacementPrice());
					fr.save();
					
					// Create transaction and sale
					Transaction t = BusinessObjectDAO.getInstance().create("Transaction");
					t.setCustomer(r.getTransaction().getCustomer());
					t.setStore(r.getTransaction().getStore());
					t.setEmployee(r.getTransaction().getEmployee());// Commission goes to the employee that rented out the product.
					t.setDate(new Date());
					t.save();
					
					Sale s = BusinessObjectDAO.getInstance().create("Sale");
					s.setTransaction(t);
					s.setProduct(fr);
					s.setQuantity(1);
					s.save();
					
					Fee f = BusinessObjectDAO.getInstance().create("Fee");
					f.setTransaction(t);
					f.setRental(r);
					f.calculateAmount();
					f.setWaived(false);
					f.save();
					
					t.finalizeAndSave();
					
					// (Email customer notification and receipt here.)
					
					counter++;
				}
			}
			System.out.println("Sell overdue rentals batch process complete. Sold " + counter + " items.");
		} catch (DataException e) {
			e.printStackTrace();
		}
		
	}

}

package edu.byu.isys413.data.batch;

import java.util.List;
import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Rental;

public class RemindLateRentals {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			List<Rental> rentals = BusinessObjectDAO.getInstance().searchForAll("Rental");
			int counter = 0;
			for(Rental r : rentals) {
				if(r.isLate() && !r.isReminderSent() && r.getDateIn() == null) {
					// Email here
					r.setReminderSent(true);
					r.save();
					counter++;
				}
			}
			System.out.println("Reminder batch process complete. Sent " + counter + " reminder emails.");
		} catch (DataException e) {
			e.printStackTrace();
		}

	}

}

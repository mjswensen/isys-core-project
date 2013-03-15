package edu.byu.isys413.data.batch;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Commission;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.DebitCredit;
import edu.byu.isys413.data.models.JournalEntry;
import edu.byu.isys413.data.models.SearchCriteria;

/**
 * Program to be run monthly to pay commissions to sales staff.
 * @author mswensen
 *
 */
public class PayCommissions {

	/**
	 * Run the program.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Step 1: generate running total for commissions owed to each relevant employee.
			Map<String,Double> commsPayable = new TreeMap<String,Double>();
			List<Commission> commList = BusinessObjectDAO.getInstance().searchForList("Commission", new SearchCriteria("paid", 0));
			double commsTotal = 0.0;
			for(Commission comm : commList) {
				if(commsPayable.containsKey(comm.getEmployeeId())) {
					double oldAmount = commsPayable.get(comm.getEmployeeId());
					commsPayable.put(comm.getEmployeeId(), oldAmount + comm.getAmount());
				} else {
					commsPayable.put(comm.getEmployeeId(), comm.getAmount());
				}
				commsTotal += comm.getAmount();
			}
			
			// Step 2: print checks.
			for(Entry<String,Double> ent : commsPayable.entrySet()) {
				System.out.println("Printing check for " + ent.getKey() + " in the amount of $" + ent.getValue());
			}
			
			// Step 3: mark commissions as paid in the database.
			for(Commission comm : commList) {
				comm.setPaid(true);
				comm.save();
			}
			
			// Step 4: create journal entry for commissions paid.
			JournalEntry je = BusinessObjectDAO.getInstance().create("JournalEntry");
			je.setDate(new Date());
			je.save();
			
			DebitCredit cash = BusinessObjectDAO.getInstance().create("DebitCredit");
			cash.setJournalEntry(je);
			cash.setGlAccount("Cash");
			cash.setType("CR");
			cash.setAmount(commsTotal);
			cash.save();
			
			DebitCredit commPayable = BusinessObjectDAO.getInstance().create("DebitCredit");
			commPayable.setJournalEntry(je);
			commPayable.setGlAccount("Commission Payable");
			commPayable.setType("DR");
			commPayable.setAmount(commsTotal);
			commPayable.save();
			
			// Done.
			
			System.out.println("Commissions paid successfully. Paid " + commsPayable.size() + " employees from " + commList.size() + " commissions.");
		} catch (DataException e) {
			System.out.println("Unable to pay commissions due to the following error: " + e.getMessage());
		}
	}

}

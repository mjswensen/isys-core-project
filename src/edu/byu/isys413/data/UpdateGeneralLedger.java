package edu.byu.isys413.data;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Program to be run nightly that takes un-posted journal entries and posts them to the general ledger.
 * @author mswensen
 *
 */
public class UpdateGeneralLedger {

	/**
	 * Run the program.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			// Step 1: create a map of the net changes we will need to make to the GL.
			
			Map<String, Double> temporaryLedger = new TreeMap<String, Double>();
			List<JournalEntry> jeList = BusinessObjectDAO.getInstance().searchForList("JournalEntry", new SearchCriteria("posted", 0));
			for(JournalEntry je : jeList) {
				for(DebitCredit drcr : je.getDebitCredits()) {
					GeneralLedger gl = BusinessObjectDAO.getInstance().searchForBO("GeneralLedger", new SearchCriteria("account", drcr.getGlAccount()));
					double amount = drcr.getAmount();
					if((gl.getType().equals("DR") && drcr.getType().equals("CR")) || (gl.getType().equals("CR") && drcr.getType().equals("DR"))) {
						amount = amount * -1;
					}
					if(temporaryLedger.containsKey(drcr.getGlAccount())) {
						Double oldAmount = temporaryLedger.get(drcr.getGlAccount());
						temporaryLedger.put(drcr.getGlAccount(), oldAmount + amount);
					} else {
						temporaryLedger.put(drcr.getGlAccount(), amount);
					}
				}
			}
			
			// Step 2: post the net changes to the ledger.
			
			for(Entry<String, Double> ent : temporaryLedger.entrySet()) {
				GeneralLedger gl = BusinessObjectDAO.getInstance().searchForBO("GeneralLedger", new SearchCriteria("account", ent.getKey()));
				gl.setBalance(gl.getBalance() + ent.getValue());
				gl.save();
			}
			
			// Step 3: mark the JournalEntries as posted.
			
			for(JournalEntry je : jeList) {
				je.setPosted(true);
				je.save();
			}
			
			System.out.println("Journal entries posted to the general ledger successfully. Updated " + temporaryLedger.size() + " accounts from " + jeList.size() + " journal entries.");
			
		} catch (DataException e) {
			System.out.println("There was an error running this program: " + e.getMessage());
		}
	}

}

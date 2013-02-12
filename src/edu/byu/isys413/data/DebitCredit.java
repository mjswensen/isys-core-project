package edu.byu.isys413.data;

public class DebitCredit extends BusinessObject {

	public enum Type { DR, CR }
	
	@BusinessObjectField
	private String journalEntryId;
	@BusinessObjectField
	private JournalEntry journalEntry;
	@BusinessObjectField
	private Type type;
	@BusinessObjectField
	private String glAccount;
	@BusinessObjectField
	private double amount;
	
	/** Creates a new instance of BusinessObject */
	public DebitCredit(String id) {
		super(id);
	}

}

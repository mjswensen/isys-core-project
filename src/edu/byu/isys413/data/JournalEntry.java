package edu.byu.isys413.data;

import java.util.Date;

public class JournalEntry extends BusinessObject {

	@BusinessObjectField
	private String transactionId;
	@BusinessObjectField
	private Transaction transaction;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private boolean isPosted;
	
	/** Creates a new instance of BusinessObject */
	public JournalEntry(String id) {
		super(id);
	}

}

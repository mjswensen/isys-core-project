package edu.byu.isys413.data;

public class DebitCredit extends BusinessObject {
	
//	public enum Type { DR("DR"), CR("CR") }
	
	@BusinessObjectField
	private String journalEntryId;
	@BusinessObjectField
	private String type;
	@BusinessObjectField
	private String glAccount;
	@BusinessObjectField
	private double amount;
	
	/** Creates a new instance of BusinessObject */
	public DebitCredit(String id) {
		super(id);
	}

	/**
	 * @return the journalEntryId
	 */
	public String getJournalEntryId() {
		return journalEntryId;
	}

	/**
	 * @param journalEntryId the journalEntryId to set
	 */
	public void setJournalEntryId(String journalEntryId) {
		this.journalEntryId = journalEntryId;
		setDirty();
	}

	/**
	 * @return the journalEntry
	 * @throws DataException 
	 */
	public JournalEntry getJournalEntry() throws DataException {
		return BusinessObjectDAO.getInstance().read(journalEntryId);
	}

	/**
	 * @param journalEntry the journalEntry to set
	 */
	public void setJournalEntry(JournalEntry journalEntry) {
		this.journalEntryId = journalEntry.getId();
		setDirty();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
		setDirty();
	}

	/**
	 * @return the glAccount
	 */
	public String getGlAccount() {
		return glAccount;
	}

	/**
	 * @param glAccount the glAccount to set
	 */
	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
		setDirty();
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
		setDirty();
	}

}

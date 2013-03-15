package edu.byu.isys413.data.models;

import java.util.Date;
import java.util.List;

public class JournalEntry extends BusinessObject {

	@BusinessObjectField
	private String transactionId;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private boolean posted;
	
	/** Creates a new instance of BusinessObject */
	public JournalEntry(String id) {
		super(id);
		setPosted(false);
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		setDirty();
	}

	/**
	 * @return the transaction
	 * @throws DataException 
	 */
	public Transaction getTransaction() throws DataException {
		return BusinessObjectDAO.getInstance().read(transactionId);
	}

	/**
	 * Also sets the date for convenience.
	 * @param transaction the transaction to set
	 */
	public void setTransaction(Transaction transaction) {
		this.transactionId = transaction.getId();
		setDate(transaction.getDate());
		setDirty();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
		setDirty();
	}

	/**
	 * @return whether or not the journal entry has been posted
	 */
	public boolean isPosted() {
		return posted;
	}

	/**
	 * @param whether or not the journal entry has been posted
	 */
	public void setPosted(boolean posted) {
		this.posted = posted;
		setDirty();
	}
	
	/**
	 * @return list of debitcredits associated with this journal entry
	 * @throws DataException
	 */
	public List<DebitCredit> getDebitCredits() throws DataException {
		return BusinessObjectDAO.getInstance().searchForList("DebitCredit", new SearchCriteria("journalentryid", id));
	}

}

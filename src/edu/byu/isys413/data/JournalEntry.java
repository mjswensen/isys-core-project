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
	 */
	public Transaction getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(Transaction transaction) {
		this.transactionId = transaction.getId();
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
	 * @return the isPosted
	 */
	public boolean isPosted() {
		return isPosted;
	}

	/**
	 * @param isPosted the isPosted to set
	 */
	public void setPosted(boolean isPosted) {
		this.isPosted = isPosted;
		setDirty();
	}

}

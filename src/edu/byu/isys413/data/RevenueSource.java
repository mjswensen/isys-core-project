package edu.byu.isys413.data;

public class RevenueSource extends BusinessObject {

	@BusinessObjectField
	private String transactionId;
	@BusinessObjectField
	private Transaction transaction;
	@BusinessObjectField
	private double chargeAmount;
	@BusinessObjectField
	private String type;
	
	/** Creates a new instance of BusinessObject */
	public RevenueSource(String id) {
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
	 * @return the chargeAmount
	 */
	public double getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * @param chargeAmount the chargeAmount to set
	 */
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
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

}

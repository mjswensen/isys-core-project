package edu.byu.isys413.data;

public class RevenueSource extends BusinessObject {

	@BusinessObjectField
	private String transactionId;
	@BusinessObjectField
	protected double chargeAmount;
	@BusinessObjectField
	private String type;
	
	/** Creates a new instance of BusinessObject */
	public RevenueSource(String id) {
		super(id);
		type = "Sale";
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}

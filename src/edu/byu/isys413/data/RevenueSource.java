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

}

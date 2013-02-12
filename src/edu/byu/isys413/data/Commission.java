package edu.byu.isys413.data;

import java.util.Date;

public class Commission extends BusinessObject {

	@BusinessObjectField
	private String transactionId;
	@BusinessObjectField
	private Transaction transaction;
	@BusinessObjectField
	private String employeeId;
	@BusinessObjectField
	private Employee employee;
	@BusinessObjectField
	private double amount;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private boolean isPaid;
	
	/** Creates a new instance of BusinessObject */
	public Commission(String id) {
		super(id);
	}

}

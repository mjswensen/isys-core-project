package edu.byu.isys413.data;

import java.util.Date;

public class Transaction extends BusinessObject {

	@BusinessObjectField
	private String customerId;
	@BusinessObjectField
	private Customer customer;
	@BusinessObjectField
	private String storeId;
	@BusinessObjectField
	private Store store;
	@BusinessObjectField
	private String employeeId;
	@BusinessObjectField
	private Employee employee;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private double subtotal;
	@BusinessObjectField
	private double tax;
	@BusinessObjectField
	private double total;
	
	/** Creates a new instance of BusinessObject */
	public Transaction(String id) {
		super(id);
	}
}

package edu.byu.isys413.data;

public class Customer extends BusinessObject {

	@BusinessObjectField
	private String fristName;
	@BusinessObjectField
	private String lastName;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String address;
	
	/** Creates a new instance of BusinessObject */
	public Customer(String id) {
		super(id);
	}

}

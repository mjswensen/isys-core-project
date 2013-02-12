package edu.byu.isys413.data;

public class Store extends BusinessObject {

	@BusinessObjectField
	private String location;
	@BusinessObjectField
	private String manager;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String address;
	@BusinessObjectField
	private String city;
	@BusinessObjectField
	private String state;
	@BusinessObjectField
	private String zip;
	
	/** Creates a new instance of BusinessObject */
	public Store(String id) {
		super(id);
	}
	
}

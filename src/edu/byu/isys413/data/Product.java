package edu.byu.isys413.data;

public class Product extends BusinessObject {

	@BusinessObjectField
	protected double price;
	
	/** Creates a new instance of BusinessObject */
	public Product(String id) {
		super(id);
	}

}

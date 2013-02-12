package edu.byu.isys413.data;

public class Sale extends RevenueSource {
	
	@BusinessObjectField
	private String productId;
	@BusinessObjectField
	private Product product;
	@BusinessObjectField
	private int quantity;
	
	/** Creates a new instance of RevenueSource/BusinessObject */
	public Sale(String id) {
		super(id);
	}
}

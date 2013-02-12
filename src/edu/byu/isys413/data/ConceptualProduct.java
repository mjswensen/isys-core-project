package edu.byu.isys413.data;

public class ConceptualProduct extends Product {
	
	@BusinessObjectField
	private String name;
	@BusinessObjectField
	private String description;
	@BusinessObjectField
	private String manufacturer;
	@BusinessObjectField
	private double averageCost;
	@BusinessObjectField
	private String categoryId;
	@BusinessObjectField
	private String vendorId;
	
	/** Creates a new instance of Product/BusinessObject */
	public ConceptualProduct(String id) {
		super(id);
	}
}

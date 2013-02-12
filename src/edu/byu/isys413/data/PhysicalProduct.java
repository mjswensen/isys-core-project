package edu.byu.isys413.data;

import java.util.Date;

public class PhysicalProduct extends Product {

	@BusinessObjectField
	private String storeId;
	@BusinessObjectField
	private Store store;
	@BusinessObjectField
	private String conceptualProductId;
	@BusinessObjectField
	private ConceptualProduct conceptualProduct;
	@BusinessObjectField
	private String serialNum;
	@BusinessObjectField
	private String shelfLocation;
	@BusinessObjectField
	private Date purchased;
	@BusinessObjectField
	private double cost;
	@BusinessObjectField
	private String status;
	@BusinessObjectField
	private double comissionRate;
	
	/** Creates a new instance of Product/BusinessObject */
	public PhysicalProduct(String id) {
		super(id);
	}

}

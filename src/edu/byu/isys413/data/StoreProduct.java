package edu.byu.isys413.data;

public class StoreProduct extends BusinessObject {

	@BusinessObjectField
	private String conceptualProductId;
	@BusinessObjectField
	private ConceptualProduct conceptualProduct;
	@BusinessObjectField
	private String storeId;
	@BusinessObjectField
	private Store store;
	@BusinessObjectField
	private int quantityOnHand;
	@BusinessObjectField
	private String shelfLocation;
	
	/** Creates a new instance of BusinessObject */
	public StoreProduct(String id) {
		super(id);
	}
}

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

	/**
	 * @return the conceptualProductId
	 */
	public String getConceptualProductId() {
		return conceptualProductId;
	}

	/**
	 * @param conceptualProductId the conceptualProductId to set
	 */
	public void setConceptualProductId(String conceptualProductId) {
		this.conceptualProductId = conceptualProductId;
		setDirty();
	}

	/**
	 * @return the conceptualProduct
	 */
	public ConceptualProduct getConceptualProduct() {
		return conceptualProduct;
	}

	/**
	 * @param conceptualProduct the conceptualProduct to set
	 */
	public void setConceptualProduct(ConceptualProduct conceptualProduct) {
		this.conceptualProductId = conceptualProduct.getId();
		setDirty();
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
		setDirty();
	}

	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.storeId = store.getId();
		setDirty();
	}

	/**
	 * @return the quantityOnHand
	 */
	public int getQuantityOnHand() {
		return quantityOnHand;
	}

	/**
	 * @param quantityOnHand the quantityOnHand to set
	 */
	public void setQuantityOnHand(int quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
		setDirty();
	}

	/**
	 * @return the shelfLocation
	 */
	public String getShelfLocation() {
		return shelfLocation;
	}

	/**
	 * @param shelfLocation the shelfLocation to set
	 */
	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
		setDirty();
	}
}

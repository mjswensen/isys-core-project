package edu.byu.isys413.data;

import java.util.Date;

public class PhysicalProduct extends Product {

	@BusinessObjectField
	private String storeId;
	@BusinessObjectField
	private String conceptualProductId;
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
	 * @throws DataException 
	 */
	public Store getStore() throws DataException {
		return BusinessObjectDAO.getInstance().read(storeId);
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.storeId = store.getId();
		setDirty();
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
	 * @throws DataException 
	 */
	public ConceptualProduct getConceptualProduct() throws DataException {
		return BusinessObjectDAO.getInstance().read(conceptualProductId);
	}

	/**
	 * @param conceptualProduct the conceptualProduct to set
	 */
	public void setConceptualProduct(ConceptualProduct conceptualProduct) {
		this.conceptualProductId = conceptualProduct.getId();
		setDirty();
	}

	/**
	 * @return the serialNum
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
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

	/**
	 * @return the purchased
	 */
	public Date getPurchased() {
		return purchased;
	}

	/**
	 * @param purchased the purchased to set
	 */
	public void setPurchased(Date purchased) {
		this.purchased = purchased;
		setDirty();
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
		setDirty();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
		setDirty();
	}

	/**
	 * @return the comissionRate
	 */
	public double getComissionRate() {
		return comissionRate;
	}

	/**
	 * @param comissionRate the comissionRate to set
	 */
	public void setComissionRate(double comissionRate) {
		this.comissionRate = comissionRate;
		setDirty();
	}

}

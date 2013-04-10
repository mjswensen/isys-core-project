package edu.byu.isys413.data.models;

import java.util.Date;

/**
 * A physical product in the system.
 * 
 * @author mswensen
 */
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
	protected boolean available;
	@BusinessObjectField
	private double commissionRate;
	@BusinessObjectField
	private String type;
	
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
	@Override
	public ConceptualProduct getConceptualProduct() {
		ConceptualProduct cp = null;
		try {
			cp = BusinessObjectDAO.getInstance().read(conceptualProductId);
		} catch (DataException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return cp;
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
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
		setDirty();
	}

	/**
	 * @return the commissionRate
	 */
	public double getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @param commissionRate the cmomissionRate to set
	 */
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
		setDirty();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set. Should be either "ForRent" or "ForSale"
	 */
	public void setType(String type) {
		this.type = type;
		setDirty();
	}
	
	/**
	 * @return the full commission rate for the sale or rental of this physical product
	 * @throws DataException
	 */
	public double getFullCommissionRate() throws DataException {
		return getCommissionRate() + getConceptualProduct().getCommissionRate();
	}

}

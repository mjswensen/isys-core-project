package edu.byu.isys413.data;

import java.util.LinkedList;
import java.util.List;

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the averageCost
	 */
	public double getAverageCost() {
		return averageCost;
	}

	/**
	 * @param averageCost the averageCost to set
	 */
	public void setAverageCost(double averageCost) {
		this.averageCost = averageCost;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	/**
	 * @return A list of StoreProducts where the product is this Product.
	 * @throws DataException
	 */
	public List<StoreProduct> getStoreProducts() throws DataException {
		return BusinessObjectDAO.getInstance().searchForList("StoreProduct", new SearchCriteria("conceptualproductid", id));
	}
	
	/**
	 * @return A list of stores this product is associated with.
	 * @throws DataException
	 */
	public List<Store> getStores() throws DataException {
		List<Store> stores = new LinkedList<Store>();
		for(StoreProduct sp : getStoreProducts()) {
			stores.add(sp.getStore());
		}
		return stores;
	}
}

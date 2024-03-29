package edu.byu.isys413.data.models;

import java.util.LinkedList;
import java.util.List;

/**
 * A store.
 * 
 * @author mswensen
 */
public class Store extends BusinessObject {

	@BusinessObjectField
	private String location;
	@BusinessObjectField
	private String managerId;
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
	@BusinessObjectField
	private double salesTaxRate;
	
	/** Creates a new instance of BusinessObject */
	public Store(String id) {
		super(id);
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
		setDirty();
	}

	/**
	 * @return the managerId
	 */
	public String getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
		setDirty();
	}
	
	/**
	 * @return the manager
	 * @throws DataException 
	 */
	public Employee getManager() throws DataException {
		return BusinessObjectDAO.getInstance().read(managerId);
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Employee manager) {
		this.managerId = manager.getId();
		setDirty();
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
		setDirty();
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
		setDirty();
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
		setDirty();
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
		setDirty();
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
		setDirty();
	}
	
	/**
	 * @return the salesTaxRate
	 */
	public double getSalesTaxRate() {
		return salesTaxRate;
	}

	/**
	 * @param salesTaxRate the salesTaxRate to set
	 */
	public void setSalesTaxRate(double salesTaxRate) {
		this.salesTaxRate = salesTaxRate;
	}

	/**
	 * @return A list of StoreProducts where the store is this Store.
	 * @throws DataException
	 */
	public List<StoreProduct> getStoreProducts() throws DataException {
		return BusinessObjectDAO.getInstance().searchForList("StoreProduct", new SearchCriteria("storeid", id));
	}
	
	/**
	 * @return A list of products that pertain to this Store.
	 * @throws DataException
	 */
	public List<ConceptualProduct> getProducts() throws DataException {
		List<ConceptualProduct> conceptualProducts = new LinkedList<ConceptualProduct>();
		for(StoreProduct sp : getStoreProducts()) {
			conceptualProducts.add(sp.getConceptualProduct());
		}
		return conceptualProducts;
	}
	
}

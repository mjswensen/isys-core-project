package edu.byu.isys413.data;

/**
 * A sort of global storage unit for app-wide information.
 * @author mswensen
 */
public class AppData {
	
	private static AppData instance = null;
	
	private Store store;
	private Employee employee;
	private boolean isLoggedIn;
	
	private Customer lookupCustomer;
	
	// Singleton pattern
	private AppData() {}
	
	/**
	 * @return the singleton instance of AppData
	 */
	public static synchronized AppData getInstance() {
		if(instance == null) {
			instance = new AppData();
		}
		return instance;
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
		this.store = store;
	}
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the isLoggedIn
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * @param isLoggedIn the isLoggedIn to set
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * @return the lookupCustomer
	 */
	public Customer getLookupCustomer() {
		return lookupCustomer;
	}

	/**
	 * @param lookupCustomer the lookupCustomer to set
	 */
	public void setLookupCustomer(Customer lookupCustomer) {
		this.lookupCustomer = lookupCustomer;
	}
	
}

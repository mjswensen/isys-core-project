package edu.byu.isys413.data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Transaction extends BusinessObject {

	@BusinessObjectField
	private String customerId;
	@BusinessObjectField
	private String storeId;
	@BusinessObjectField
	private String employeeId;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private Double subtotal = 0.0;
	@BusinessObjectField
	private Double tax = 0.0;
	@BusinessObjectField
	private Double total = 0.0;
	
	/** Creates a new instance of BusinessObject */
	public Transaction(String id) {
		super(id);
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
		setDirty();
	}

	/**
	 * @return the customer
	 * @throws DataException 
	 */
	public Customer getCustomer() throws DataException {
		return BusinessObjectDAO.getInstance().read(customerId);
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customerId = customer.getId();
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
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
		setDirty();
	}

	/**
	 * @return the employee
	 * @throws DataException 
	 */
	public Employee getEmployee() throws DataException {
		return BusinessObjectDAO.getInstance().read(employeeId);
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employeeId = employee.getId();
		setDirty();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
		setDirty();
	}

	/**
	 * @return the subtotal
	 * @throws DataException 
	 */
	public Double getSubtotal() throws DataException {
		return subtotal;
	}

	/**
	 * Should only be called by BusinessObjectDAO when retrieving data from DB.
	 * @param subtotal the subtotal to set
	 * @throws DataException 
	 */
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
		setDirty();
	}

	/**
	 * @return the tax
	 * @throws DataException 
	 */
	public Double getTax() throws DataException {
		return tax;
	}

	/**
	 * Should only be called by BusinessObjectDAO when retrieving data from DB.
	 * @param tax the tax to set
	 */
	public void setTax(Double tax) {
		this.tax = tax;
		setDirty();
	}

	/**
	 * @return the total
	 * @throws DataException 
	 */
	public Double getTotal() throws DataException {
		return total;
	}

	/**
	 * Should only be called by BusinessObjectDAO when retrieving data from DB.
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
		setDirty();
	}
	
	/**
	 * @return sales associated with this transaction
	 * @throws DataException
	 */
	public List<Sale> getSales() throws DataException {
		List<Sale> allSales = BusinessObjectDAO.getInstance().searchForAll("Sale");
		List<Sale> sales = new LinkedList<Sale>();
		for(Sale s : allSales) {
			if(s.getTransactionId() == id) sales.add(s);
		}
		return sales;
	}
	
	/**
	 * Retrieves associated sales from DB and calculates totals. Getters for totals will call this if null.
	 * @throws DataException
	 */
	public void calculateTotals() throws DataException {
		double subtotal = 0.0, tax, total;
		for(Sale s : getSales()) {
			subtotal += s.getChargeAmount();
		}
		tax = subtotal * getStore().getSalesTaxRate();
		total = tax + subtotal;
		setSubtotal(subtotal);
		setTax(tax);
		setTotal(total);
	}
	
	public double getCommissionAmount() throws DataException {
		double commissionAmount = 0.0;
		ConceptualProduct cp;
		PhysicalProduct pp;
		for(Sale s : getSales()) {
			if((cp = BusinessObjectDAO.getInstance().read(s.getProductId())) != null) {
				commissionAmount += cp.getPrice() * cp.getCommissionRate();
			}
			if((pp = BusinessObjectDAO.getInstance().read(s.getProductId())) != null) {
				commissionAmount += pp.getPrice() * (pp.getCommissionRate() + pp.getConceptualProduct().getCommissionRate());
			}
		}
		return commissionAmount;
	}
}

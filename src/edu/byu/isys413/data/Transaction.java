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
	private double subtotal = 0.0;
	@BusinessObjectField
	private double tax = 0.0;
	@BusinessObjectField
	private double total = 0.0;
	
	private List<Sale> sales = new LinkedList<Sale>();
	
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
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 * @throws DataException 
	 */
	private void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
		setDirty();
	}

	/**
	 * @return the tax
	 * @throws DataException 
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	private void setTax(double tax) {
		this.tax = tax;
		setDirty();
	}

	/**
	 * @return the total
	 * @throws DataException 
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	private void setTotal(double total) {
		this.total = total;
		setDirty();
	}
	
	/**
	 * @return sales associated with this transaction
	 * @throws DataException
	 */
	public List<Sale> getSales() throws DataException {
		return sales;
	}
	
	/**
	 * Adds a sale to this transaction.
	 * @param sale
	 * @throws DataException 
	 */
	public void addSale(Sale sale) throws DataException {
		sale.setTransaction(this);
		sales.add(sale);
		calculateTotals();
	}
	
	private void calculateTotals() throws DataException {
		double subtotal = 0.0, tax, total;
		for(Sale s : sales) {
			subtotal += s.getChargeAmount();
		}
		tax = subtotal * getStore().getSalesTaxRate();
		total = tax + subtotal;
		setSubtotal(subtotal);
		setTax(tax);
		setTotal(total);
	}
}

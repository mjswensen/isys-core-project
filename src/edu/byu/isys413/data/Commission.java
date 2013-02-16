package edu.byu.isys413.data;

import java.util.Date;

public class Commission extends BusinessObject {

	@BusinessObjectField
	private String transactionId;
	@BusinessObjectField
	private String employeeId;
	@BusinessObjectField
	private double amount;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private boolean paid;
	
	/** Creates a new instance of BusinessObject */
	public Commission(String id) {
		super(id);
		setPaid(false);
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		setDirty();
	}

	/**
	 * @return the transaction
	 * @throws DataException 
	 */
	public Transaction getTransaction() throws DataException {
		return BusinessObjectDAO.getInstance().read(transactionId);
	}

	/**
	 * Also sets the employee, amount, and date for convenience.
	 * @param transaction the transaction to set
	 * @throws DataException 
	 */
	public void setTransaction(Transaction transaction) throws DataException {
		this.transactionId = transaction.getId();
		setEmployee(transaction.getEmployee());
		setAmount(transaction.getCommissionAmount());
		setDate(transaction.getDate());
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
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
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
	 * @return whether or not the commission has been paid.
	 */
	public boolean isPaid() {
		return paid;
	}

	/**
	 * @param whether or not the commission has been paid.
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
		setDirty();
	}

}

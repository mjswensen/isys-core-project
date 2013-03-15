package edu.byu.isys413.data.models;

import java.util.Date;

public class Membership extends BusinessObject {
	
	@BusinessObjectField
	private String customerId;
	@BusinessObjectField
	private String creditCard;
	@BusinessObjectField
	private Date startDate;
	@BusinessObjectField
	private Date expireDate;
	@BusinessObjectField
	private boolean trial;

	public Membership(String id) {
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
	 * @return the customer associated with this account
	 * @throws DataException
	 */
	public Customer getCustomer() throws DataException {
		return BusinessObjectDAO.getInstance().read(customerId);
	}
	
	/**
	 * @param the Customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customerId = customer.getId();
		setDirty();
	}

	/**
	 * @return the creditCard
	 */
	public String getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
		setDirty();
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		setDirty();
	}

	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
		setDirty();
	}

	/**
	 * @return the trial
	 */
	public boolean isTrial() {
		return trial;
	}

	/**
	 * @param trial the trial to set
	 */
	public void setTrial(boolean trial) {
		this.trial = trial;
		setDirty();
	}
	
}

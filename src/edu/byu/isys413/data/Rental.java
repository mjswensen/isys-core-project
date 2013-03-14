package edu.byu.isys413.data;

import java.util.Date;

public class Rental extends RevenueSource {

	@BusinessObjectField
	private String forRentId;
	@BusinessObjectField
	private Date dateOut;
	@BusinessObjectField
	private Date dateIn;
	@BusinessObjectField
	private Date dateDue;
	@BusinessObjectField
	private String workOrderNumber;
	@BusinessObjectField
	private boolean reminderSent;
	
	/** Creates a new instance of Rental/RevenueSource/BusinessObject */
	public Rental(String id) {
		super(id);
	}

	/**
	 * @return the forRentId
	 */
	public String getForRentId() {
		return forRentId;
	}

	/**
	 * @param forRentId the forRentId to set
	 */
	public void setForRentId(String forRentId) {
		this.forRentId = forRentId;
		setDirty();
	}
	
	/**
	 * @return the ForRent associated with this rental
	 * @throws DataException
	 */
	public ForRent getForRent() throws DataException {
		return BusinessObjectDAO.getInstance().read(forRentId);
	}
	
	/**
	 * @param forRent
	 */
	public void setForRent(ForRent forRent) {
		this.forRentId = forRent.getId();
		setDirty();
	}

	/**
	 * @return the dateOut
	 */
	public Date getDateOut() {
		return dateOut;
	}

	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
		setDirty();
	}

	/**
	 * @return the dateIn
	 */
	public Date getDateIn() {
		return dateIn;
	}

	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
		setDirty();
	}

	/**
	 * @return the dateDue
	 */
	public Date getDateDue() {
		return dateDue;
	}

	/**
	 * @param dateDue the dateDue to set
	 */
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
		setDirty();
	}

	/**
	 * @return the workOrderNumber
	 */
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	/**
	 * @param workOrderNumber the workOrderNumber to set
	 */
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
		setDirty();
	}

	/**
	 * @return the reminderSent
	 */
	public boolean isReminderSent() {
		return reminderSent;
	}

	/**
	 * @param reminderSent the reminderSent to set
	 */
	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
		setDirty();
	}

}

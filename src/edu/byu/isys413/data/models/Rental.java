package edu.byu.isys413.data.models;

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
	
	public final long DAY_IN_MILLIS = 1000L * 60L * 60L * 24L;
	
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
	
	/**
	 * @return whether or not the rental was turned in late or is overdue
	 */
	public boolean isLate() {
		if(dateIn != null) {
			return dateIn.after(dateDue);
		} else {
			return dateDue.getTime() < System.currentTimeMillis();
		}
	}

	/**
	 * @return the length of the rental in days.
	 */
	public int getRentalPeriod() {
		long length = dateDue.getTime() - dateOut.getTime();
		return Math.round(length / DAY_IN_MILLIS);
	}
	
	/**
	 * Convenience method for setting the rental period rather than the due date directly.
	 * @param days
	 */
	public void setRentalPeriod(int days) {
		dateDue = new Date(dateOut.getTime() + DAY_IN_MILLIS * (long) days);
		setDirty();
	}
	
	/**
	 * @return the number of days late the rental was returned, or the number of days overdue if not yet returned.
	 */
	public int getLatePeriod() {
		long length;
		if(dateIn != null) {
			length = dateIn.getTime() - dateDue.getTime();
		} else {
			length = System.currentTimeMillis() - dateDue.getTime();
		}
		return Math.round(length / DAY_IN_MILLIS);
	}
	
	/**
	 * Returns the rental. If the rental is late, a new transaction is created with the late fees.
	 */
	public void returnRental() {
		try {
			setDateIn(new Date());
			save();
			ForRent fr = getForRent();
			fr.makeAvailable();
			fr.save();
			if(isLate()) {
				Transaction t = BusinessObjectDAO.getInstance().create("Transaction");
				t.setCustomer(getTransaction().getCustomer());
				t.setStore(getTransaction().getStore());
				t.setEmployee(getTransaction().getEmployee());
				t.setDate(new Date());
				t.save();
				
				Fee f = BusinessObjectDAO.getInstance().create("Fee");
				f.setTransaction(t);
				f.setRental(this);
				f.calculateAmount();
				f.setWaived(false);
				f.save();
				
				t.finalizeAndSave();
			}
		} catch(DataException e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	@Override
	public double getChargeAmount() {
		try {
			ConceptualRental cr = getForRent().getConceptualProduct().getConceputalRental();
			if(cr == null) throw new DataException("There was no ConceptualRental associated with the ForRent.");
			return cr.getPricePerDay() * getRentalPeriod();
		} catch(DataException e) {
			return super.getChargeAmount();
		}
	}
}

package edu.byu.isys413.data.models;

public class Fee extends RevenueSource {

	@BusinessObjectField
	private String rentalId;
	@BusinessObjectField
	private double amount;
	@BusinessObjectField
	private boolean waived;
	
	/** Creates a new instance of Fee/RevenueSource/BusinessObject */
	public Fee(String id) {
		super(id);
	}

	/**
	 * @return the rentalId
	 */
	public String getRentalId() {
		return rentalId;
	}

	/**
	 * @param rentalId the rentalId to set
	 */
	public void setRentalId(String rentalId) {
		this.rentalId = rentalId;
		setDirty();
	}
	
	/**
	 * @return the Rental associated with this fee
	 * @throws DataException
	 */
	public Rental getRental() throws DataException {
		return BusinessObjectDAO.getInstance().read(rentalId);
	}
	
	/**
	 * @param the rental to set.
	 */
	public void setRental(Rental rental) {
		this.rentalId = rental.getId();
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
	 * @return the waived
	 */
	public boolean isWaived() {
		return waived;
	}

	/**
	 * @param waived the waived to set
	 */
	public void setWaived(boolean waived) {
		this.waived = waived;
		setDirty();
	}
	
	/**
	 * Calculates the late fee amount.
	 * @throws DataException
	 */
	public void calculateAmount() throws DataException {
		Rental r = getRental();
		ConceptualRental cr = r.getForRent().getConceptualProduct().getConceputalRental();
		setAmount(r.getLatePeriod() * cr.getPricePerDay());
	}
	
	/* (non-Javadoc)
	 * @see edu.byu.isys413.data.RevenueSource#getChargeAmount()
	 * Only included for consistency's sake.
	 */
	@Override
	public double getChargeAmount() {
		return amount;
	}

}

package edu.byu.isys413.data.models;

/**
 * Physical product for rent in the system.
 * 
 * @author mswensen
 */
public class ForRent extends PhysicalProduct {

	@BusinessObjectField
	private String currentRentalId;
	@BusinessObjectField
	private int timesRented;
	
	/** Creates a new instance of ForRent/PhysicalProduct/Product/BusinessObject */
	public ForRent(String id) {
		super(id);
	}

	/**
	 * @return the currentRentalId
	 */
	public String getCurrentRentalId() {
		return currentRentalId;
	}

	/**
	 * @param currentRentalId the currentRentalId to set
	 */
	public void setCurrentRentalId(String currentRentalId) {
		this.currentRentalId = currentRentalId;
		setDirty();
	}
	
	/**
	 * @return the current Rental for this ForRent
	 * @throws DataException
	 */
	public Rental getCurrentRental() throws DataException {
		return BusinessObjectDAO.getInstance().read(currentRentalId);
	}
	
	/**
	 * @param the current rental to set
	 */
	public void setCurrentRental(Rental rental) {
		this.currentRentalId = rental.getId();
		setDirty();
	}

	/**
	 * @return the timesRented
	 */
	public int getTimesRented() {
		return timesRented;
	}

	/**
	 * @param timesRented the timesRented to set
	 */
	public void setTimesRented(int timesRented) {
		this.timesRented = timesRented;
		setDirty();
	}
	
	/**
	 * Increases the times rented by one.
	 */
	public void incrementTimesRented() {
		this.timesRented++;
		setDirty();
	}
	
	/**
	 * Convenience method for making a ForRent available again in the rental pool.
	 */
	public void makeAvailable() {
		currentRentalId = null;
		available = true;
		setDirty();
	}

}

package edu.byu.isys413.data.models;

public class ConceptualRental extends ConceptualProduct {

	@BusinessObjectField
	private double pricePerDay;
	@BusinessObjectField
	private double replacementPrice;
	
	/** Creates a new instance of ConceptualProduct/Product/BusinessObject */
	public ConceptualRental(String id) {
		super(id);
	}

	/**
	 * @return the pricePerDay
	 */
	public double getPricePerDay() {
		return pricePerDay;
	}

	/**
	 * @param pricePerDay the pricePerDay to set
	 */
	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
		setDirty();
	}

	/**
	 * @return the replacementPrice
	 */
	public double getReplacementPrice() {
		return replacementPrice;
	}

	/**
	 * @param replacementPrice the replacementPrice to set
	 */
	public void setReplacementPrice(double replacementPrice) {
		this.replacementPrice = replacementPrice;
		setDirty();
	}

}

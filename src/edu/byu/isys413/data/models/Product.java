package edu.byu.isys413.data.models;

public class Product extends BusinessObject {

	@BusinessObjectField
	protected double price;
	
	/** Creates a new instance of BusinessObject */
	public Product(String id) {
		super(id);
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
		setDirty();
	}

}

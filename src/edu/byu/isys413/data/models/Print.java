package edu.byu.isys413.data.models;

/**
 * A conceptual print.
 * 
 * @author mswensen
 */
public class Print extends BusinessObject {

	@BusinessObjectField
	private double price;
	@BusinessObjectField
	private String size;
	@BusinessObjectField
	private String type;
	
	/** Creates a new instance of BusinessObject */
	public Print(String id) {
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

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
		setDirty();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
		setDirty();
	}

}

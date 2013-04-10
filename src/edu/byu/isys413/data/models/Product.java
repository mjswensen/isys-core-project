package edu.byu.isys413.data.models;

/**
 * A product in the system.
 * 
 * @author mswensen
 */
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

	public ConceptualProduct getConceptualProduct(){
		ConceptualProduct cp = null;
		try {
			cp = BusinessObjectDAO.getInstance().read(getId());
		} catch (DataException ex) {
			ex.printStackTrace();
		}
		return cp;
	}
}

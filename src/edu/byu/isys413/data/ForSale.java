package edu.byu.isys413.data;

public class ForSale extends PhysicalProduct {

	@BusinessObjectField
	private boolean used;
	
	/** Creates a new instance of ForSale/PhysicalProduct/Product/BusinessObject */
	public ForSale(String id) {
		super(id);
	}

	/**
	 * @return the used
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
		setDirty();
	}

}

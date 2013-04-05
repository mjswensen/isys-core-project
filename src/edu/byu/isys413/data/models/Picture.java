package edu.byu.isys413.data.models;

/**
 * The Picture class, for customers' photos.
 * 
 * @author mswensen
 */
public class Picture extends BusinessObject {

	@BusinessObjectField
	private String customerId;
	@BusinessObjectField
	private String caption;
	@BusinessObjectField
	private String picData;
	
	/** Creates a new instance of BusinessObject */
	public Picture(String id) {
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
	 * @return the customer this picture belongs to
	 * @throws DataException
	 */
	public Customer getCustomer() throws DataException {
		return BusinessObjectDAO.getInstance().read(customerId);
	}
	
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customerId = customer.getId();
		setDirty();
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
		setDirty();
	}

	/**
	 * @return the picData
	 */
	public String getPicData() {
		return picData;
	}

	/**
	 * @param picData the base64-encoded picData to set
	 */
	public void setPicData(String picData) {
		this.picData = picData;
		setDirty();
	}

}

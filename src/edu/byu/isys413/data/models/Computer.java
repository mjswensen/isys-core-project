package edu.byu.isys413.data.models;

public class Computer extends BusinessObject {

	@BusinessObjectField
	private String mac;
	@BusinessObjectField
	private String storeId;
	
	/**
	 * Constructor.
	 * @param id
	 */
	public Computer(String id) {
		super(id);
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
		setDirty();
	}

	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
		setDirty();
	}
	
	/**
	 * @return the store this computer resides in
	 * @throws DataException
	 */
	public Store getStore() throws DataException {
		return BusinessObjectDAO.getInstance().read(storeId);
	}
	
	/**
	 * @param store the store to set for this computer
	 */
	public void setStore(Store store) {
		this.storeId = store.getId();
		setDirty();
	}

}

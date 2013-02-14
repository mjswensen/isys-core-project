package edu.byu.isys413.data;

public class Customer extends BusinessObject {

	@BusinessObjectField
	private String firstName;
	@BusinessObjectField
	private String lastName;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String address;
	
	/** Creates a new instance of BusinessObject */
	public Customer(String id) {
		super(id);
	}

	/**
	 * @return the fristName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param fristName the fristName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setDirty();
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
		setDirty();
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
		setDirty();
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
		setDirty();
	}

}

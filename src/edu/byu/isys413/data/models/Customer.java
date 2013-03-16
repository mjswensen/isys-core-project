package edu.byu.isys413.data.models;

/**
 * Represents a customer in the system.
 * 
 * @author mswensen
 */
public class Customer extends BusinessObject {

	@BusinessObjectField
	private String firstName;
	@BusinessObjectField
	private String lastName;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String email;
	@BusinessObjectField
	private String address;
	@BusinessObjectField
	private String password;
	@BusinessObjectField
	private String validationCode;
	@BusinessObjectField
	private boolean valid;
	
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
		setDirty();
	}

	/**
	 * @return the validationCode
	 */
	public String getValidationCode() {
		return validationCode;
	}

	/**
	 * @param validationCode the validationCode to set
	 */
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
		setDirty();
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
		setDirty();
	}
	
	/**
	 * @return the membership associated with this customer, if any.
	 * @throws DataException
	 */
	public Membership getMembership() throws DataException {
		return BusinessObjectDAO.getInstance().searchForBO("Membership", new SearchCriteria("customerid", id));
	}

}

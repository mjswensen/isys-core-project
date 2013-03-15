package edu.byu.isys413.data.models;

public class GeneralLedger extends BusinessObject {

//	public static enum Type { DR, CR }
	
	@BusinessObjectField
	private String account;
	@BusinessObjectField
	private double balance;
	@BusinessObjectField
	private String type;
	
	/** Creates a new instance of BusinessObject */
	public GeneralLedger(String id) {
		super(id);
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
		setDirty();
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
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

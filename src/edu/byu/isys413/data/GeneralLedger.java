package edu.byu.isys413.data;

public class GeneralLedger extends BusinessObject {

	public enum Type { DR, CR }
	
	@BusinessObjectField
	private String account;
	@BusinessObjectField
	private double balance;
	@BusinessObjectField
	private Type type;
	
	/** Creates a new instance of BusinessObject */
	public GeneralLedger(String id) {
		super(id);
	}

}

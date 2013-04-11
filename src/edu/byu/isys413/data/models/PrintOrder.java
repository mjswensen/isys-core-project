package edu.byu.isys413.data.models;

/**
 * A print order revenue source.
 * 
 * @author mswensen
 */
public class PrintOrder extends RevenueSource {

	@BusinessObjectField
	private String pictureId;
	@BusinessObjectField
	private String printId;
	@BusinessObjectField
	private int quantity;
	
	/** Creates a new instance of RevenueSource/BusinessObject */
	public PrintOrder(String id) {
		super(id);
	}

	/**
	 * @return the pictureId
	 */
	public String getPictureId() {
		return pictureId;
	}

	/**
	 * @param pictureId the pictureId to set
	 */
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
		setDirty();
	}
	
	/**
	 * @return the picture associated with this print order
	 * @throws DataException
	 */
	public Picture getPicture() throws DataException {
		return BusinessObjectDAO.getInstance().read(pictureId);
	}
	
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(Picture picture) {
		this.pictureId = picture.getId();
		setDirty();
	}

	/**
	 * @return the printId
	 */
	public String getPrintId() {
		return printId;
	}

	/**
	 * @param printId the printId to set
	 */
	public void setPrintId(String printId) {
		this.printId = printId;
		setDirty();
	}
	
	/**
	 * @return the print associated with this print order
	 * @throws DataException
	 */
	public Print getPrint() throws DataException {
		return BusinessObjectDAO.getInstance().read(printId);
	}
	
	/**
	 * @param print the print to set
	 */
	public void setPrint(Print print) {
		this.printId = print.getId();
		setDirty();
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		setDirty();
	}

	/* (non-Javadoc)
	 * @see edu.byu.isys413.data.models.RevenueSource#getChargeAmount()
	 */
	@Override
	public double getChargeAmount() {
		try {
			return getPrint().getPrice();
		} catch (DataException e) {
			e.printStackTrace();
			// If there's no print associated with the print order, the price will be free.
			return 0.0;
		}
	}
	
}

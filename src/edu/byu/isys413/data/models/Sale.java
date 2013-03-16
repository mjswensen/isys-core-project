package edu.byu.isys413.data.models;

/**
 * A sale.
 * 
 * @author mswensen
 */
public class Sale extends RevenueSource {
	
	@BusinessObjectField
	private String productId;
	@BusinessObjectField
	private int quantity = 1;
	
	/** Creates a new instance of RevenueSource/BusinessObject */
	public Sale(String id) {
		super(id);
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
		setDirty();
	}

	/**
	 * @return the product
	 * @throws DataException 
	 */
	public Product getProduct() throws DataException {
		return BusinessObjectDAO.getInstance().read(productId);
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.productId = product.getId();
		this.chargeAmount = product.getPrice() * quantity;
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
			return getProduct().getPrice() * getQuantity();
		} catch (DataException e) {
			return super.getChargeAmount();
		}
	}
}

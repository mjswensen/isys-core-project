package edu.byu.isys413.data.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A transaction. Made up of revenue sources and other relevant data.
 * 
 * @author mswensen
 */
public class Transaction extends BusinessObject {

	@BusinessObjectField
	private String customerId;
	@BusinessObjectField
	private String storeId;
	@BusinessObjectField
	private String employeeId;
	@BusinessObjectField
	private Date date;
	@BusinessObjectField
	private Double subtotal = 0.0;
	@BusinessObjectField
	private Double tax = 0.0;
	@BusinessObjectField
	private Double total = 0.0;
	
	/** Creates a new instance of BusinessObject */
	public Transaction(String id) {
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
	 * @return the customer
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
	 * @return the store
	 * @throws DataException 
	 */
	public Store getStore() throws DataException {
		return BusinessObjectDAO.getInstance().read(storeId);
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.storeId = store.getId();
		setDirty();
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
		setDirty();
	}

	/**
	 * @return the employee
	 * @throws DataException 
	 */
	public Employee getEmployee() throws DataException {
		return BusinessObjectDAO.getInstance().read(employeeId);
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employeeId = employee.getId();
		setDirty();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
		setDirty();
	}

	/**
	 * @return the subtotal
	 * @throws DataException 
	 */
	public Double getSubtotal() throws DataException {
		return subtotal;
	}

	/**
	 * Should only be called by BusinessObjectDAO when retrieving data from DB.
	 * @param subtotal the subtotal to set
	 * @throws DataException 
	 */
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
		setDirty();
	}

	/**
	 * @return the tax
	 * @throws DataException 
	 */
	public Double getTax() throws DataException {
		return tax;
	}

	/**
	 * Should only be called by BusinessObjectDAO when retrieving data from DB.
	 * @param tax the tax to set
	 */
	public void setTax(Double tax) {
		this.tax = tax;
		setDirty();
	}

	/**
	 * @return the total
	 * @throws DataException 
	 */
	public Double getTotal() throws DataException {
		return total;
	}

	/**
	 * Should only be called by BusinessObjectDAO when retrieving data from DB.
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
		setDirty();
	}
	
	/**
	 * @return Sales associated with this transaction
	 * @throws DataException
	 */
	public List<Sale> getSales() throws DataException {
		List<Sale> allSales = BusinessObjectDAO.getInstance().searchForAll("Sale");
        List<Sale> sales = new LinkedList<Sale>();
        for(Sale s : allSales) {
                if(s.getTransactionId() == id) sales.add(s);
        }
        return sales;
	}
	
	/**
	 * @return Rentals associated with this transaction
	 * @throws DataException
	 */
	public List<Rental> getRentals() throws DataException {
		List<Rental> allRentals = BusinessObjectDAO.getInstance().searchForAll("Rental");
		List<Rental> rentals = new LinkedList<Rental>();
		for(Rental r : allRentals) {
			if(r.getTransactionId() == id) rentals.add(r);
		}
		return rentals;
	}
	
	/**
	 * @return Fees associated with this transaction
	 * @throws DataException
	 */
	public List<Fee> getFees() throws DataException {
		List<Fee> allFees = BusinessObjectDAO.getInstance().searchForAll("Fee");
		List<Fee> fees = new LinkedList<Fee>();
		for(Fee f : allFees) {
			if(f.getTransactionId() == id) fees.add(f);
		}
		return fees;
	}
	
	/**
	 * @return Print Orders associated with this transaction
	 * @throws DataException
	 */
	public List<PrintOrder> getPrintOrders() throws DataException {
		List<PrintOrder> allPos = BusinessObjectDAO.getInstance().searchForAll("PrintOrder");
		List<PrintOrder> pos = new LinkedList<PrintOrder>();
		for(PrintOrder po : allPos) {
			if(po.getTransactionId() == id) pos.add(po);
		}
		return pos;
	}
	
	/**
	 * Retrieves associated sales from DB and calculates totals.
	 * @throws DataException
	 */
	public void calculateTotals() throws DataException {
		double subtotal = 0.0, tax, total;
		for(Sale s : getSales()) {
			subtotal += s.getChargeAmount();
		}
		for(Rental r : getRentals()) {
			subtotal += r.getChargeAmount();
		}
		for(Fee f : getFees()) {
			subtotal += f.getChargeAmount();
		}
		for(PrintOrder po : getPrintOrders()) {
			subtotal += po.getChargeAmount();
		}
		tax = subtotal * getStore().getSalesTaxRate();
		total = tax + subtotal;
		setSubtotal(subtotal);
		setTax(tax);
		setTotal(total);
	}
	
	/**
	 * Takes care of updating inventory, creating a JournalEntry, and creating a 
	 * Commission (with its journal entry debits and credits) for this transaction.
	 * @throws DataException 
	 */
	public void finalizeAndSave() throws DataException {
		calculateTotals();
		// Update inventory
		for(Sale s : getSales()) {
			if(s.getProduct().getClass().getSimpleName().equals("ConceptualProduct")) {
				ConceptualProduct cp = BusinessObjectDAO.getInstance().read(s.getProductId());
				StoreProduct sp = BusinessObjectDAO.getInstance().searchForBO("StoreProduct", new SearchCriteria("conceptualproductid", cp.getId()), new SearchCriteria("storeid", getStoreId()));
				if(sp != null) {
					sp.subtractQuantityOnHand(s.getQuantity());
					sp.save();
				}
			}
			if(s.getProduct().getClass().getSimpleName().equals("ForSale")) {
				ForSale fs = BusinessObjectDAO.getInstance().read(s.getProductId());
				fs.setAvailable(false);
				fs.save();
			}
		}
		for(Rental r : getRentals()) {
			ForRent fr = r.getForRent();
			fr.setAvailable(false);
			fr.setCurrentRental(r);
			fr.incrementTimesRented();
			r.save();
			fr.save();
		}
		
		// Create journal entry
		JournalEntry je = BusinessObjectDAO.getInstance().create("JournalEntry");
		je.setTransaction(this);
		je.save();
		
		DebitCredit cash = BusinessObjectDAO.getInstance().create("DebitCredit");
		cash.setJournalEntry(je);
		cash.setGlAccount("Cash");
		cash.setType("DR");
		cash.setAmount(total);
		cash.save();
		

		// Calculate where the revenues are coming from, and create the appropriate journal entries for them.
		
		if(getSales().size() > 0) {
			double salesRevenueTotal = 0.0;
			for(Sale s : getSales()) {
				salesRevenueTotal += s.getChargeAmount();
			}
			DebitCredit salesRevenue = BusinessObjectDAO.getInstance().create("DebitCredit");
			salesRevenue.setJournalEntry(je);
			salesRevenue.setGlAccount("Sales Revenue");
			salesRevenue.setType("CR");
			salesRevenue.setAmount(salesRevenueTotal);
			salesRevenue.save();
		}
		
		if(getRentals().size() > 0) {
			double rentalRevenueTotal = 0.0;
			for(Rental r : getRentals()) {
				rentalRevenueTotal += r.getChargeAmount();
			}
			DebitCredit rentalRevenue = BusinessObjectDAO.getInstance().create("DebitCredit");
			rentalRevenue.setJournalEntry(je);
			rentalRevenue.setGlAccount("Rental Revenue");
			rentalRevenue.setType("CR");
			rentalRevenue.setAmount(rentalRevenueTotal);
			rentalRevenue.save();
		}
		
		if(getFees().size() > 0) {
			double feeRevenueTotal = 0.0;
			for(Fee f : getFees()) {
				feeRevenueTotal += f.getChargeAmount();
			}
			DebitCredit feeRevenue = BusinessObjectDAO.getInstance().create("DebitCredit");
			feeRevenue.setJournalEntry(je);
			feeRevenue.setGlAccount("Fee Revenue");
			feeRevenue.setType("CR");
			feeRevenue.setAmount(feeRevenueTotal);
			feeRevenue.save();
		}
		
		if(getPrintOrders().size() > 0) {
			double printOrderRevenueTotal = 0.0;
			for(PrintOrder po : getPrintOrders()) {
				printOrderRevenueTotal += po.getChargeAmount();
			}
			DebitCredit printOrderRevenue = BusinessObjectDAO.getInstance().create("DebitCredit");
			printOrderRevenue.setJournalEntry(je);
			printOrderRevenue.setGlAccount("Print Order Revenue");
			printOrderRevenue.setType("CR");
			printOrderRevenue.setAmount(printOrderRevenueTotal);
			printOrderRevenue.save();
		}
		
		
		// Taxes
		DebitCredit taxPayable = BusinessObjectDAO.getInstance().create("DebitCredit");
		taxPayable.setJournalEntry(je);
		taxPayable.setGlAccount("Tax Payable");
		taxPayable.setType("CR");
		taxPayable.setAmount(tax);
		taxPayable.save();
		
		// Create commission
		Commission comm = BusinessObjectDAO.getInstance().create("Commission");
		comm.setTransaction(this);
		comm.save();
		
		// Add commission debit and credit to journal entry
		DebitCredit commissionExpense = BusinessObjectDAO.getInstance().create("DebitCredit");
		commissionExpense.setJournalEntry(je);
		commissionExpense.setGlAccount("Commission Expense");
		commissionExpense.setType("DR");
		commissionExpense.setAmount(comm.getAmount());
		commissionExpense.save();
		
		DebitCredit commissionPayable = BusinessObjectDAO.getInstance().create("DebitCredit");
		commissionPayable.setJournalEntry(je);
		commissionPayable.setGlAccount("Commission Payable");
		commissionPayable.setType("CR");
		commissionPayable.setAmount(comm.getAmount());
		commissionPayable.save();
		
		// Save
		save();
		
	}
	
	/**
	 * @return the commission amount for this transaction.
	 * @throws DataException
	 */
	public double getCommissionAmount() throws DataException {
		double commissionAmount = 0.0;
		for(Sale s : getSales()) {
			if(s.getProduct().getClass().getSimpleName().equals("ConceptualProduct")) {
				ConceptualProduct cp = BusinessObjectDAO.getInstance().read(s.getProductId());
				commissionAmount += cp.getPrice() * cp.getCommissionRate();
			}
			if(s.getProduct().getClass().getSimpleName().equals("ForSale")) {
				ForSale fs = BusinessObjectDAO.getInstance().read(s.getProductId());
				commissionAmount += fs.getPrice() * fs.getFullCommissionRate();
			}
		}
		for(Rental r : getRentals()) {
			commissionAmount += r.getForRent().getFullCommissionRate() + r.getChargeAmount();
		}
		return commissionAmount;
	}
}

package edu.byu.isys413.data.views;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.ConceptualProduct;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Employee;
import edu.byu.isys413.data.models.PhysicalProduct;
import edu.byu.isys413.data.models.Product;
import edu.byu.isys413.data.models.Rental;
import edu.byu.isys413.data.models.Sale;
import edu.byu.isys413.data.models.Store;
import edu.byu.isys413.data.models.Transaction;

/**
 * A window to provide transaction functionality
 *
 */
public class TransactionView extends Shell {
	private Text txtFirstname;
	private Text txtLastname;
	private Text txtAddress;
	private Text txtEmail;
	private Text txtPhone;
	private Text txtSubtotal;
	private Text txtTax;
	private Text txtTotal;
	
	private TableViewer tableViewerSales;
	private TableViewer tableViewerRentals;
	
	private Transaction t;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
	private Text txtYesNoMembership;
	private Table table;
	private Table table_1;

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			SaleView shell = new SaleView(display);
//			shell.open();
//			shell.layout();
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch()) {
//					display.sleep();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public TransactionView(final Display display) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(TransactionView.class, "/images/logo_camera.png"));
		setMinimumSize(new Point(800, 455));
		setSize(669, 455);
		setText("MyStuff | Transaction Matrix");
		setLayout(new GridLayout(2, false));
		
		Group grpStandardTransactions = new Group(this, SWT.NONE);
		GridData gd_grpStandardTransactions = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_grpStandardTransactions.heightHint = 180;
		grpStandardTransactions.setLayoutData(gd_grpStandardTransactions);
		grpStandardTransactions.setText("Standard Transactions");
		grpStandardTransactions.setLayout(new GridLayout(1, false));
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(grpStandardTransactions, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		tableViewerSales = new TableViewer(scrolledComposite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = tableViewerSales.getTable();
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumnItemName = new TableViewerColumn(tableViewerSales, SWT.NONE);
		tableViewerColumnItemName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				try {
					Product p = s.getProduct();
					if(p.getClass().getSimpleName().equals("ConceptualProduct")) {
						return ((ConceptualProduct) p).getName();
					} else if(p.getClass().getSimpleName().equals("ForSale")) {
						return ((PhysicalProduct) p).getConceptualProduct().getName();
					} else {
						throw new DataException("Product was neither Conceptual nor Physical. It was: " + p.getClass().getSimpleName());
					}
				} catch (DataException e) {
					System.out.println(e.getMessage());
					return "(Unavailable)";
				}
			}
		});
		TableColumn tableColumn_5 = tableViewerColumnItemName.getColumn();
		tableColumn_5.setWidth(197);
		tableColumn_5.setText("Name");
		
		TableViewerColumn tableViewerColumnItemPrice = new TableViewerColumn(tableViewerSales, SWT.NONE);
		tableViewerColumnItemPrice.setLabelProvider(new ColumnLabelProvider() 
		{
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				try {
					return NumberFormat.getCurrencyInstance().format(s.getProduct().getPrice());
				} catch (DataException e) {
					return "(Unavailable)";
				}
			}
		});
		TableColumn tableColumn_6 = tableViewerColumnItemPrice.getColumn();
		tableColumn_6.setWidth(71);
		tableColumn_6.setText("Price");
		
		TableViewerColumn tableViewerQuantity = new TableViewerColumn(tableViewerSales, SWT.NONE);
		tableViewerQuantity.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				return s.getQuantity() + "";
			}
		});
		TableColumn tableColumn_7 = tableViewerQuantity.getColumn();
		tableColumn_7.setWidth(76);
		tableColumn_7.setText("Quantity");
		
		TableViewerColumn tableViewerColumnItemTotal = new TableViewerColumn(tableViewerSales, SWT.NONE);
		tableViewerColumnItemTotal.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				return NumberFormat.getCurrencyInstance().format(s.getChargeAmount());
			}
		});
		TableColumn tableColumn_8 = tableViewerColumnItemTotal.getColumn();
		tableColumn_8.setWidth(90);
		tableColumn_8.setText("Item Total");
		scrolledComposite_1.setContent(table_1);
		scrolledComposite_1.setMinSize(table_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Group grpCustomer = new Group(this, SWT.NONE);
		GridData gd_grpCustomer = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2);
		gd_grpCustomer.widthHint = 401;
		grpCustomer.setLayoutData(gd_grpCustomer);
		grpCustomer.setText("Customer");
		grpCustomer.setLayout(new GridLayout(2, false));
		
		Button btnLookupCustomer = new Button(grpCustomer, SWT.NONE);
		btnLookupCustomer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnLookupCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				CustomerLookupView clv = new CustomerLookupView(display);
				clv.open();
				clv.layout();
				clv.addDisposeListener(new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent arg0) {
						t.setCustomer(AppData.getInstance().getLookupCustomer());
						updateCustomerView();
					}});
			}
		});
		btnLookupCustomer.setText("Lookup Customer");
		
		Label lblFirstName = new Label(grpCustomer, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First Name");
		
		txtFirstname = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY);
		txtFirstname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLastName = new Label(grpCustomer, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Last Name");
		
		txtLastname = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY);
		txtLastname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAddress = new Label(grpCustomer, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText("Address");
		
		txtAddress = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI);
		GridData gd_txtAddress = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtAddress.heightHint = 30;
		txtAddress.setLayoutData(gd_txtAddress);
		
		Label lblEmail = new Label(grpCustomer, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email");
		
		txtEmail = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtEmail = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtEmail.widthHint = 150;
		txtEmail.setLayoutData(gd_txtEmail);
		
		Label lblPhone = new Label(grpCustomer, SWT.NONE);
		lblPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhone.setText("Phone");
		
		txtPhone = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY);
		txtPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMembership = new Label(grpCustomer, SWT.NONE);
		lblMembership.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMembership.setText("Membership");
		
		txtYesNoMembership = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY);
		txtYesNoMembership.setEditable(false);
		txtYesNoMembership.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_4 = new Composite(grpCustomer, SWT.NONE);
		GridLayout gl_composite_4 = new GridLayout(2, false);
		gl_composite_4.marginBottom = -5;
		gl_composite_4.marginRight = -5;
		gl_composite_4.marginLeft = -5;
		composite_4.setLayout(gl_composite_4);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
		
		Button btnNewCustomer = new Button(composite_4, SWT.NONE);
		GridData gd_btnNewCustomer = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_btnNewCustomer.widthHint = 130;
		btnNewCustomer.setLayoutData(gd_btnNewCustomer);
		btnNewCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Customer c;
				try {
					c = BusinessObjectDAO.getInstance().create("Customer");
					t.setCustomer(c);
					CustomerInfoView civ = new CustomerInfoView(display, c);
					civ.open();
					civ.layout();
					civ.addDisposeListener(new DisposeListener() {
						@Override
						public void widgetDisposed(DisposeEvent arg0) {
							updateCustomerView();
						}});
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnNewCustomer.setText("New Customer");
		
		Button btnEditCustomer = new Button(composite_4, SWT.NONE);
		GridData gd_btnEditCustomer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditCustomer.widthHint = 130;
		btnEditCustomer.setLayoutData(gd_btnEditCustomer);
		btnEditCustomer.setText("Edit Customer");
		
		Group grpRentalTransactions = new Group(this, SWT.NONE);
		GridData gd_grpRentalTransactions = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 3);
		gd_grpRentalTransactions.heightHint = 150;
		gd_grpRentalTransactions.widthHint = 465;
		grpRentalTransactions.setLayoutData(gd_grpRentalTransactions);
		grpRentalTransactions.setText("Rental Transactions");
		grpRentalTransactions.setLayout(new GridLayout(1, false));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(grpRentalTransactions, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		tableViewerRentals = new TableViewer(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewerRentals.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumnRentalItemName = new TableViewerColumn(tableViewerRentals, SWT.NONE);
		tableViewerColumnRentalItemName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Rental r = (Rental)element;
				try {
					return r.getForRent().getConceptualProduct().getName();
				} catch (DataException e) {
					return "(Unavailable)";
				}
			}
		});
		TableColumn tableColumn = tableViewerColumnRentalItemName.getColumn();
		tableColumn.setWidth(166);
		tableColumn.setText("Name");
		
		TableViewerColumn tableViewerColumnDateOut = new TableViewerColumn(tableViewerRentals, SWT.NONE);
		tableViewerColumnDateOut.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Rental r = (Rental)element;
				return sdf.format(r.getDateOut());
			}
		});
		TableColumn tableColumn_1 = tableViewerColumnDateOut.getColumn();
		tableColumn_1.setWidth(75);
		tableColumn_1.setText("Date Out");
		
		TableViewerColumn tableViewerDateDue = new TableViewerColumn(tableViewerRentals, SWT.NONE);
		tableViewerDateDue.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Rental r = (Rental)element;
				return sdf.format(r.getDateDue());
			}
		});
		TableColumn tableColumn_2 = tableViewerDateDue.getColumn();
		tableColumn_2.setWidth(80);
		tableColumn_2.setText("Date Due");
		
		TableViewerColumn tableViewerPricePerDay = new TableViewerColumn(tableViewerRentals, SWT.NONE);
		tableViewerPricePerDay.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Rental r = (Rental)element;
				try {
					return NumberFormat.getCurrencyInstance().format(r.getForRent().getConceptualProduct().getConceputalRental().getPricePerDay());
				} catch (DataException e) {
					return "(Unavailable)";
				}
			}
		});
		TableColumn tableColumn_3 = tableViewerPricePerDay.getColumn();
		tableColumn_3.setWidth(78);
		tableColumn_3.setText("Price Per Day");
		
		TableViewerColumn tableViewerColumnRentalItemTotal = new TableViewerColumn(tableViewerRentals, SWT.NONE);
		tableViewerColumnRentalItemTotal.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Rental r = (Rental)element;
				return NumberFormat.getCurrencyInstance().format(r.getChargeAmount());
			}
		});
		TableColumn tableColumn_4 = tableViewerColumnRentalItemTotal.getColumn();
		tableColumn_4.setWidth(61);
		tableColumn_4.setText("Item Total");
		scrolledComposite.setContent(table);
		scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Group composite = new Group(this, SWT.NONE);
		composite.setText("Transaction Information");
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		Button btnAddItem = new Button(composite, SWT.NONE);
		GridData gd_btnAddItem = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddItem.widthHint = 111;
		btnAddItem.setLayoutData(gd_btnAddItem);
		btnAddItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				final ScanProductView spv = new ScanProductView(display);
				spv.setTransaction(t);
				spv.open();
				spv.layout();
				spv.addShellListener(new ShellListener() {
					@Override
					public void shellActivated(ShellEvent arg0) {}
					@Override
					public void shellClosed(ShellEvent arg0) {
						updateProductsView();
					}
					@Override
					public void shellDeactivated(ShellEvent arg0) {}
					@Override
					public void shellDeiconified(ShellEvent arg0) {}
					@Override
					public void shellIconified(ShellEvent arg0) {}
				});
				spv.addDisposeListener(new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent arg0) {
						updateProductsView();
					}
				});
			}
		});
		btnAddItem.setText("Add Item");
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 2));
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginRight = -5;
		gl_composite_2.marginTop = -5;
		gl_composite_2.marginBottom = -5;
		composite_2.setLayout(gl_composite_2);
		
		Label lblSubtotal = new Label(composite_2, SWT.NONE);
		lblSubtotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSubtotal.setText("Subtotal");
		
		txtSubtotal = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		txtSubtotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTax = new Label(composite_2, SWT.NONE);
		lblTax.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTax.setText("Tax");
		
		txtTax = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		txtTax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTotal = new Label(composite_2, SWT.NONE);
		lblTotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTotal.setText("Total");
		
		txtTotal = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		txtTotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnRemoveItem = new Button(composite, SWT.NONE);
		GridData gd_btnRemoveItem = new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1);
		gd_btnRemoveItem.widthHint = 112;
		btnRemoveItem.setLayoutData(gd_btnRemoveItem);
		btnRemoveItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// Get selection from the table and remove it in the database. Then update the view.
				IStructuredSelection sel = (IStructuredSelection)tableViewerSales.getSelection();
				Sale toRemove = (Sale)sel.getFirstElement();
				try {
					BusinessObjectDAO.getInstance().delete(toRemove);
					updateProductsView();
				} catch (DataException e1) {
					System.out.println("Error in deletion");
				}
			}
		});
		btnRemoveItem.setText("Remove Item");
		
		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setLayout(new GridLayout(2, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnCancelTransaction = new Button(composite_3, SWT.NONE);
		GridData gd_btnCancelTransaction = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_btnCancelTransaction.widthHint = 139;
		btnCancelTransaction.setLayoutData(gd_btnCancelTransaction);
		btnCancelTransaction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dispose();
			}
		});
		btnCancelTransaction.setText("Cancel");
		
		Button btnSubmitTransaction = new Button(composite_3, SWT.NONE);
		GridData gd_btnSubmitTransaction = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSubmitTransaction.widthHint = 130;
		btnSubmitTransaction.setLayoutData(gd_btnSubmitTransaction);
		btnSubmitTransaction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					t.finalizeAndSave();
					dispose();
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnSubmitTransaction.setText("Submit");
		
		// Begin the transaction
		
		try {
			// Grab related objects
			Store store = AppData.getInstance().getStore();
			Employee emp = AppData.getInstance().getEmployee();
			
			// Create transaction and save.
			t = BusinessObjectDAO.getInstance().create("Transaction");
			t.setStore(store);
			t.setEmployee(emp);
			t.setDate(new Date());
			t.save();
			
		} catch (DataException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *Updates the customer view with the customer associate with the transaction
	 */
	private void updateCustomerView() {
		try {
			Customer c = t.getCustomer();
			if(c == null) throw new Exception("Customer was null.");
			txtFirstname.setText(c.getFirstName());
			txtLastname.setText(c.getLastName());
			txtPhone.setText(c.getPhone());
			txtAddress.setText(c.getAddress());
			txtEmail.setText(c.getEmail());
			if(c.getMembership() != null) {
				txtYesNoMembership.setText("Yes");
			} else {
				txtYesNoMembership.setText("No");
			}
		} catch (Exception e) {
			System.out.println("Not updating customer view: " + e.getMessage());
		}
	}
	
	/**
	 *Updates the products view with the products associated with the transaction
	 */
	private void updateProductsView() {
		try {
			// Update Sales table
			List<Sale> sales = t.getSales();
			tableViewerSales.setContentProvider(ArrayContentProvider.getInstance());
			tableViewerSales.setInput(sales);
			tableViewerSales.refresh();
			
			// Update Rentals table
			List<Rental> rentals = t.getRentals();
			tableViewerRentals.setContentProvider(ArrayContentProvider.getInstance());
			tableViewerRentals.setInput(rentals);
			tableViewerRentals.refresh();
			
			// Update totals display
			t.calculateTotals();
			txtSubtotal.setText(NumberFormat.getCurrencyInstance().format(t.getSubtotal()));
			txtTax.setText(NumberFormat.getCurrencyInstance().format(t.getTax()));
			txtTotal.setText(NumberFormat.getCurrencyInstance().format(t.getTotal()));
		} catch (Exception e) {
			System.out.println("Not updating products view: " + e.getMessage());
		}
	}

	/* @see org.eclipse.swt.widgets.Decorations#checkSubclass()*/
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

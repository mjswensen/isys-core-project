package edu.byu.isys413.data;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.FlowLayout;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;

public class SaleView extends Shell {
	private Text txtFirstname;
	private Text txtLastname;
	private Text txtAddress;
	private Text txtEmail;
	private Text txtPhone;
	private Table table;
	private Text txtSubtotal;
	private Text txtTax;
	private Text txtTotal;
	
	private TableViewer tableViewer;
	
	private Transaction t;

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
	public SaleView(final Display display) {
		super(display, SWT.SHELL_TRIM);
		setSize(708, 450);
		setText("Sales Transaction");
		setLayout(new GridLayout(2, false));
		
		Group grpCustomer = new Group(this, SWT.NONE);
		grpCustomer.setText("Customer");
		grpCustomer.setLayout(new GridLayout(2, false));
		
		Button btnLookupCustomer = new Button(grpCustomer, SWT.NONE);
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
		new Label(grpCustomer, SWT.NONE);
		
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
		txtEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPhone = new Label(grpCustomer, SWT.NONE);
		lblPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhone.setText("Phone");
		
		txtPhone = new Text(grpCustomer, SWT.BORDER | SWT.READ_ONLY);
		txtPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewCustomer = new Button(grpCustomer, SWT.NONE);
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
		
		Button btnEditCustomer = new Button(grpCustomer, SWT.NONE);
		btnEditCustomer.setText("Edit Customer");
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumnItemDesc = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = tableViewerColumnItemDesc.getColumn();
		tblclmnName.setWidth(197);
		tblclmnName.setText("Name");
		
		tableViewerColumnItemDesc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				try {
					return s.getProduct().getId();
				} catch (DataException e) {
					return "Unable to get product identifier.";
				}
			}
		});
		
		TableViewerColumn tableViewerColumnItemPrice = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPrice = tableViewerColumnItemPrice.getColumn();
		tblclmnPrice.setWidth(71);
		tblclmnPrice.setText("Price");
		new Label(this, SWT.NONE);
		
		tableViewerColumnItemPrice.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				try {
					return "$" + s.getProduct().getPrice();
				} catch (DataException e) {
					return "Unable to get product price.";
				}
			}
		});
		
		TableViewerColumn tableViewerColumnQuantity = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnQuantity = tableViewerColumnQuantity.getColumn();
		tblclmnQuantity.setWidth(76);
		tblclmnQuantity.setText("Quantity");
		
		tableViewerColumnQuantity.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				return s.getQuantity() + "";
			}
		});
		
		TableViewerColumn tableViewerColumnItemTotal = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnItemTotal = tableViewerColumnItemTotal.getColumn();
		tblclmnItemTotal.setWidth(90);
		tblclmnItemTotal.setText("Item Total");
		
		tableViewerColumnItemTotal.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Sale s = (Sale)element;
				return "$" + s.getChargeAmount();
			}
		});
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnAddItem = new Button(composite_1, SWT.NONE);
		btnAddItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				final ScanProductView spv = new ScanProductView(display);
				spv.open();
				spv.layout();
				spv.addShellListener(new ShellListener() {
					@Override
					public void shellActivated(ShellEvent arg0) {}
					@Override
					public void shellClosed(ShellEvent arg0) {
						try {
							Sale s = BusinessObjectDAO.getInstance().create("Sale");
							s.setTransaction(t);
							s.setProduct(spv.getProduct());
							s.setQuantity(spv.getQuantity());
							s.save();
							spv.dispose();
						} catch (DataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
		
		Button btnRemoveItem = new Button(composite_1, SWT.NONE);
		btnRemoveItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// Get selection from the table and remove it in the database. Then update the view.
				IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
				Sale toRemove = (Sale)sel.getFirstElement();
				try {
					BusinessObjectDAO.getInstance().delete(toRemove);
					updateProductsView();
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnRemoveItem.setText("Remove Item");
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		
		Label lblSubtotal = new Label(composite_2, SWT.NONE);
		lblSubtotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSubtotal.setText("Subtotal");
		
		txtSubtotal = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY);
		txtSubtotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTax = new Label(composite_2, SWT.NONE);
		lblTax.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTax.setText("Tax");
		
		txtTax = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY);
		txtTax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTotal = new Label(composite_2, SWT.NONE);
		lblTotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTotal.setText("Total");
		
		txtTotal = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY);
		txtTotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		composite_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Button btnCancelTransaction = new Button(composite_3, SWT.NONE);
		btnCancelTransaction.setText("Cancel Transaction");
		
		Button btnSubmitTransaction = new Button(composite_3, SWT.NONE);
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
		btnSubmitTransaction.setText("Submit Transaction");
		
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
	
	private void updateCustomerView() {
		try {
			Customer c = t.getCustomer();
			if(c == null) throw new Exception("Customer was null.");
			txtFirstname.setText(c.getFirstName());
			txtLastname.setText(c.getLastName());
			txtPhone.setText(c.getPhone());
			txtAddress.setText(c.getAddress());
			txtEmail.setText(c.getEmail());
		} catch (Exception e) {
			System.out.println("Not updating customer view: " + e.getMessage());
		}
	}
	
	private void updateProductsView() {
		try {
			// Update products table
			List<Sale> sales = t.getSales();
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(sales);
			tableViewer.refresh();
			
			// Update totals display
			t.calculateTotals();
			txtSubtotal.setText("$" + t.getSubtotal());
			txtTax.setText("$" + t.getTax());
			txtTotal.setText("$" + t.getTotal());
		} catch (Exception e) {
			System.out.println("Not updating products view: " + e.getMessage());
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

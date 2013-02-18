package edu.byu.isys413.data;

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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.FlowLayout;

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

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			SaleView shell = new SaleView(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public SaleView(Display display) {
		super(display, SWT.SHELL_TRIM);
		setSize(604, 384);
		setLayout(new GridLayout(2, false));
		
		Group grpCustomer = new Group(this, SWT.NONE);
		grpCustomer.setText("Customer");
		grpCustomer.setLayout(new GridLayout(2, false));
		
		Button btnLookupCustomer = new Button(grpCustomer, SWT.NONE);
		btnLookupCustomer.setText("Lookup Customer");
		new Label(grpCustomer, SWT.NONE);
		
		Label lblFirstName = new Label(grpCustomer, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First Name");
		
		txtFirstname = new Text(grpCustomer, SWT.BORDER);
		txtFirstname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLastName = new Label(grpCustomer, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Last Name");
		
		txtLastname = new Text(grpCustomer, SWT.BORDER);
		txtLastname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAddress = new Label(grpCustomer, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText("Address");
		
		txtAddress = new Text(grpCustomer, SWT.BORDER | SWT.MULTI);
		GridData gd_txtAddress = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtAddress.heightHint = 30;
		txtAddress.setLayoutData(gd_txtAddress);
		
		Label lblEmail = new Label(grpCustomer, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email");
		
		txtEmail = new Text(grpCustomer, SWT.BORDER);
		txtEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPhone = new Label(grpCustomer, SWT.NONE);
		lblPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhone.setText("Phone");
		
		txtPhone = new Text(grpCustomer, SWT.BORDER);
		txtPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewCustomer = new Button(grpCustomer, SWT.NONE);
		btnNewCustomer.setText("New Customer");
		
		Button btnEditCustomer = new Button(grpCustomer, SWT.NONE);
		btnEditCustomer.setText("Edit Customer");
		
		TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = tableViewerColumn.getColumn();
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPrice = tableViewerColumn_1.getColumn();
		tblclmnPrice.setWidth(100);
		tblclmnPrice.setText("Price");
		new Label(this, SWT.NONE);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnAddItem = new Button(composite_1, SWT.NONE);
		btnAddItem.setText("Add Item");
		
		Button btnRemoveItem = new Button(composite_1, SWT.NONE);
		btnRemoveItem.setText("Remove Item");
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		
		Label lblSubtotal = new Label(composite_2, SWT.NONE);
		lblSubtotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSubtotal.setText("Subtotal");
		
		txtSubtotal = new Text(composite_2, SWT.BORDER);
		txtSubtotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTax = new Label(composite_2, SWT.NONE);
		lblTax.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTax.setText("Tax");
		
		txtTax = new Text(composite_2, SWT.BORDER);
		txtTax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTotal = new Label(composite_2, SWT.NONE);
		lblTotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTotal.setText("Total");
		
		txtTotal = new Text(composite_2, SWT.BORDER);
		txtTotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnCancelTransaction = new Button(composite_3, SWT.NONE);
		btnCancelTransaction.setText("Cancel Transaction");
		
		Button btnSubmitTransaction = new Button(composite_3, SWT.NONE);
		btnSubmitTransaction.setText("Submit Transaction");
		
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Sales Transaction");
		setSize(581, 321);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

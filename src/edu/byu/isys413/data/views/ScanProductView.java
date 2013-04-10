package edu.byu.isys413.data.views;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Group;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.ConceptualProduct;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.ForRent;
import edu.byu.isys413.data.models.ForSale;
import edu.byu.isys413.data.models.Rental;
import edu.byu.isys413.data.models.Sale;
import edu.byu.isys413.data.models.SearchCriteria;
import edu.byu.isys413.data.models.Transaction;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ScanProductView extends Shell {
	
	private Transaction t;

	private Label labelErrorMsg;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ScanProductView shell = new ScanProductView(display);
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
	public ScanProductView(Display display) {
		super(display, SWT.SHELL_TRIM);
		setMinimumSize(new Point(420, 350));
		setImage(SWTResourceManager.getImage(ScanProductView.class, "/images/logo_camera.png"));
		setLayout(new GridLayout(1, false));
		
		Group grpStandard = new Group(this, SWT.NONE);
		GridData gd_grpStandard = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_grpStandard.widthHint = 380;
		grpStandard.setLayoutData(gd_grpStandard);
		grpStandard.setText("Purchase Standard");
		grpStandard.setLayout(new GridLayout(4, false));
		
		Label lblSku = new Label(grpStandard, SWT.NONE);
		lblSku.setText("SKU:");
		
		final Text txtSku = new Text(grpStandard, SWT.BORDER);
		txtSku.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(grpStandard, SWT.NONE);
		
		Label lblQuantity = new Label(grpStandard, SWT.NONE);
		lblQuantity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		lblQuantity.setText("Quantity:");
		
		final Text txtQty = new Text(grpStandard, SWT.BORDER);
		txtQty.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Button btnGoStoreProduct = new Button(grpStandard, SWT.NONE);
		GridData gd_btnGoStoreProduct = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGoStoreProduct.widthHint = 50;
		btnGoStoreProduct.setLayoutData(gd_btnGoStoreProduct);
		btnGoStoreProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					ConceptualProduct cp = BusinessObjectDAO.getInstance().searchForBO("ConceptualProduct", new SearchCriteria("sku", txtSku.getText()));
					int qty = Integer.parseInt(txtQty.getText());
					if(cp != null) {
						Sale s = BusinessObjectDAO.getInstance().create("Sale");
						s.setTransaction(t);
						s.setProduct(cp);
						s.setQuantity(qty);
						s.save();
						close();
					} else {
						throw new DataException("The SKU provided is not valid.");
					}
				} catch (DataException e1) {
					setErrorMsg(e1.getMessage());
				}
			}
		});
		btnGoStoreProduct.setText("Go");
		
		Group grpHighend = new Group(this, SWT.NONE);
		grpHighend.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpHighend.setText("Purchase High-end");
		grpHighend.setLayout(new GridLayout(3, false));
		
		Label lblSerialNumber = new Label(grpHighend, SWT.NONE);
		lblSerialNumber.setText("Serial Number:");
		
		final Text txtForSaleSerialnumber = new Text(grpHighend, SWT.BORDER);
		GridData gd_txtForSaleSerialnumber = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtForSaleSerialnumber.widthHint = 227;
		txtForSaleSerialnumber.setLayoutData(gd_txtForSaleSerialnumber);
		
		Button btnGoForSale = new Button(grpHighend, SWT.NONE);
		GridData gd_btnGoForSale = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_btnGoForSale.widthHint = 50;
		btnGoForSale.setLayoutData(gd_btnGoForSale);
		btnGoForSale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					ForSale fs = BusinessObjectDAO.getInstance().searchForBO("PhysicalProduct", new SearchCriteria("serialnum", txtForSaleSerialnumber.getText()));
					if(fs != null) {
						Sale s = BusinessObjectDAO.getInstance().create("Sale");
						s.setTransaction(t);
						s.setProduct(fs);
						s.setQuantity(1);
						s.save();
						close();
					} else {
						throw new DataException("The serial number provided is not for sale.");
					}
				} catch (DataException e1) {
					setErrorMsg(e1.getMessage());
				}
			}
		});
		btnGoForSale.setText("Go");
		
		Group grpRental = new Group(this, SWT.NONE);
		grpRental.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpRental.setText("Rental");
		grpRental.setLayout(new GridLayout(5, false));
		
		Label lblSerialNumber_1 = new Label(grpRental, SWT.NONE);
		lblSerialNumber_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSerialNumber_1.setText("Serial Number:");
		
		final Text txtForRentSerialnumber = new Text(grpRental, SWT.BORDER);
		txtForRentSerialnumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(grpRental, SWT.NONE);
		
		Label lblRentalPeriodin = new Label(grpRental, SWT.NONE);
		lblRentalPeriodin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 3, 1));
		lblRentalPeriodin.setText("Rental Period (in Days):");
		
		final Text txtRentalPeriod = new Text(grpRental, SWT.BORDER);
		txtRentalPeriod.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Button btnGoForRent = new Button(grpRental, SWT.NONE);
		GridData gd_btnGoForRent = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGoForRent.widthHint = 50;
		btnGoForRent.setLayoutData(gd_btnGoForRent);
		btnGoForRent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					ForRent fr = BusinessObjectDAO.getInstance().searchForBO("PhysicalProduct", new SearchCriteria("serialnum", txtForRentSerialnumber.getText()));
					int days = Integer.parseInt(txtRentalPeriod.getText());
					if(fr != null) {
						if(!fr.isAvailable()) throw new DataException("That rental is not available.");
						Rental r = BusinessObjectDAO.getInstance().create("Rental");
						r.setForRent(fr);
						r.setTransaction(t);
						r.setDateOut(new Date());
						r.setRentalPeriod(days);
						r.setReminderSent(false);
						r.save();
						close();
					} else {
						throw new DataException("The serial number provided is not for rent.");
					}
				} catch (DataException e1) {
					setErrorMsg(e1.getMessage());
				}
			}
		});
		btnGoForRent.setText("Go");
		
		Group grpStatusInformation = new Group(this, SWT.NONE);
		grpStatusInformation.setLayout(new GridLayout(1, false));
		grpStatusInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpStatusInformation.setText("Status Information");
		
		labelErrorMsg = new Label(grpStatusInformation, SWT.NONE);
		labelErrorMsg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dispose();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.widthHint = 150;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Cancel");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MyStuff | Scan Product");
		setSize(325, 304);

	}
	
	/**
	 * Sets the transaction so that this window may manipulate it.
	 * @param t
	 */
	public void setTransaction(Transaction t) {
		this.t = t;
	}

	/**
	 * Displays an error to the user if there was a problem selecting the product.
	 * @param msg
	 */
	public void setErrorMsg(String msg) {
		labelErrorMsg.setText(msg);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

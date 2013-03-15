package edu.byu.isys413.data.views;

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
import edu.byu.isys413.data.models.PhysicalProduct;
import edu.byu.isys413.data.models.Product;
import edu.byu.isys413.data.models.SearchCriteria;

public class ScanProductView extends Shell {
	private Text txtSku;
	private Text txtSerialnumber;
	
	private Product p;
	private Text txtQty;
	private int quantity = 1;

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
		setLayout(new GridLayout(1, false));
		
		Group grpStandard = new Group(this, SWT.NONE);
		GridData gd_grpStandard = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_grpStandard.widthHint = 380;
		grpStandard.setLayoutData(gd_grpStandard);
		grpStandard.setText("Standard");
		grpStandard.setLayout(new GridLayout(3, false));
		
		Label lblSku = new Label(grpStandard, SWT.NONE);
		lblSku.setText("SKU:");
		
		txtSku = new Text(grpStandard, SWT.BORDER);
		txtSku.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(grpStandard, SWT.NONE);
		
		Label lblQuantity = new Label(grpStandard, SWT.NONE);
		lblQuantity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblQuantity.setText("Quantity:");
		
		txtQty = new Text(grpStandard, SWT.BORDER);
		txtQty.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo = new Button(grpStandard, SWT.NONE);
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					ConceptualProduct cp = BusinessObjectDAO.getInstance().searchForBO("ConceptualProduct", new SearchCriteria("sku", txtSku.getText()));
					p = (Product)cp;
					quantity = Integer.parseInt(txtQty.getText());
					close();
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnGo.setText("Go");
		
		Group grpHighend = new Group(this, SWT.NONE);
		grpHighend.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpHighend.setText("High-end");
		grpHighend.setLayout(new GridLayout(3, false));
		
		Label lblSerialNumber = new Label(grpHighend, SWT.NONE);
		lblSerialNumber.setText("Serial Number:");
		
		txtSerialnumber = new Text(grpHighend, SWT.BORDER);
		GridData gd_txtSerialnumber = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtSerialnumber.widthHint = 227;
		txtSerialnumber.setLayoutData(gd_txtSerialnumber);
		
		Button btnGo_1 = new Button(grpHighend, SWT.NONE);
		btnGo_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					PhysicalProduct pp = BusinessObjectDAO.getInstance().searchForBO("PhysicalProduct", new SearchCriteria("serialnum", txtSerialnumber.getText()));
					p = (Product)pp;
					close();
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnGo_1.setText("Go");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Scan Product");
		setSize(450, 300);

	}
	
	/**
	 * @return the product selected by the window (either via sku or serial number). called by a close listener to return product back to SaleView.
	 */
	public Product getProduct() {
		return p;
	}
	
	/**
	 * @return the quantity of the conceptual product.
	 */
	public int getQuantity() {
		return quantity;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

package edu.byu.isys413.data;

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

public class ScanProductView extends Shell {
	private Text txtSku;
	private Text txtSerialnumber;
	
	private Product p;

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
		setLayout(new GridLayout(3, false));
		
		Label lblSku = new Label(this, SWT.NONE);
		lblSku.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSku.setText("SKU:");
		
		txtSku = new Text(this, SWT.BORDER);
		txtSku.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo = new Button(this, SWT.NONE);
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					ConceptualProduct cp = BusinessObjectDAO.getInstance().searchForBO("ConceptualProduct", new SearchCriteria("sku", txtSku.getText()));
					p = (Product)cp;
					close();
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnGo.setText("Go");
		
		Label lblSerialNumber = new Label(this, SWT.NONE);
		lblSerialNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSerialNumber.setText("Serial Number:");
		
		txtSerialnumber = new Text(this, SWT.BORDER);
		txtSerialnumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo_1 = new Button(this, SWT.NONE);
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
		setText("SWT Application");
		setSize(450, 300);

	}
	
	/**
	 * @return the product selected by the window (either via sku or serial number). called by a close listener to return product back to SaleView.
	 */
	public Product getProduct() {
		return p;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

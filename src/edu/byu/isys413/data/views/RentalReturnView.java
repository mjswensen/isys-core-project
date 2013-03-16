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

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.ForRent;
import edu.byu.isys413.data.models.Rental;
import edu.byu.isys413.data.models.SearchCriteria;

public class RentalReturnView extends Shell {
	private Text txtSerialnum;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			RentalReturnView shell = new RentalReturnView(display);
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
	public RentalReturnView(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new GridLayout(3, false));
		
		Label lblSerialNumber = new Label(this, SWT.NONE);
		lblSerialNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSerialNumber.setText("Serial Number:");
		
		txtSerialnum = new Text(this, SWT.BORDER);
		txtSerialnum.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo = new Button(this, SWT.NONE);
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					ForRent fr = BusinessObjectDAO.getInstance().searchForBO("PhysicalProduct", new SearchCriteria("serialnum", txtSerialnum.getText()));
					Rental r = fr.getCurrentRental();
					r.returnRental();
					close();
				} catch (DataException e1) {
					// TODO
				}
			}
		});
		btnGo.setText("Go");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Rental Return");
		setSize(450, 100);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

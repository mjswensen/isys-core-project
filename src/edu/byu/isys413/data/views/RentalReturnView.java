package edu.byu.isys413.data.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.ForRent;
import edu.byu.isys413.data.models.Rental;
import edu.byu.isys413.data.models.SearchCriteria;

/**
 * Enables employees to handle the return process for rented items.
 *
 */
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
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setMinimumSize(new Point(440, 235));
		setImage(SWTResourceManager.getImage(RentalReturnView.class, "/images/logo_camera.png"));
		setLayout(new GridLayout(3, false));
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		lblNewLabel_1.setText("DIRECTIONS");
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_label = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_label.widthHint = 159;
		label.setLayoutData(gd_label);
		
		Label lblNewLabel = new Label(this, SWT.WRAP);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("Please scan the serial number that is located on the rental item. Alternately, it is possible to use the provided text field to manually enter it in. After manually entering in the serial number, press \"Go\".");
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		gd_lblNewLabel.verticalIndent = 5;
		gd_lblNewLabel.heightHint = 70;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		
		Label lblSerialNumber = new Label(this, SWT.NONE);
		lblSerialNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSerialNumber.setText("Serial Number:");
		
		txtSerialnum = new Text(this, SWT.BORDER);
		txtSerialnum.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo = new Button(this, SWT.NONE);
		GridData gd_btnGo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGo.widthHint = 80;
		btnGo.setLayoutData(gd_btnGo);
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
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dispose();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.TOP, false, false, 3, 1);
		gd_btnNewButton.verticalIndent = 10;
		gd_btnNewButton.widthHint = 150;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Cancel");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MyStuff | Rental Return");
		setSize(440, 178);

	}

	/* @see org.eclipse.swt.widgets.Decorations#checkSubclass()*/
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

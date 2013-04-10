package edu.byu.isys413.data.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.SearchCriteria;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CustomerLookupView extends Shell {
	private Text txtLastname;
	private Text txtPhonenumber;
	private Label lblCustNotFound;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			CustomerLookupView shell = new CustomerLookupView(display);
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
	public CustomerLookupView(Display display) {
		super(display, SWT.SHELL_TRIM);
		setMinimumSize(new Point(350, 210));
		setImage(SWTResourceManager.getImage(CustomerLookupView.class, "/images/logo_camera.png"));
		setLayout(new GridLayout(1, false));
		
		Group grpLookupInformation = new Group(this, SWT.NONE);
		grpLookupInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpLookupInformation.setText("Customer Information");
		grpLookupInformation.setLayout(new GridLayout(3, false));
		
		Label lblLastName = new Label(grpLookupInformation, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Last Name:");
		
		txtLastname = new Text(grpLookupInformation, SWT.BORDER);
		txtLastname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo = new Button(grpLookupInformation, SWT.NONE);
		GridData gd_btnGo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGo.widthHint = 50;
		btnGo.setLayoutData(gd_btnGo);
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String lastName = txtLastname.getText();
				try {
					Customer c = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("lastname", "%" + lastName + "%", SearchCriteria.LIKE));
					if(c != null) {
						AppData.getInstance().setLookupCustomer(c);
						dispose();
					} else {
						lblCustNotFound.setText("CUSTOMER NOT FOUND");
					}
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGo.setText("Go");
		
		Label lblPhoneNumber = new Label(grpLookupInformation, SWT.NONE);
		lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhoneNumber.setText("Phone Number:");
		
		txtPhonenumber = new Text(grpLookupInformation, SWT.BORDER);
		txtPhonenumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo_1 = new Button(grpLookupInformation, SWT.NONE);
		GridData gd_btnGo_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGo_1.widthHint = 50;
		btnGo_1.setLayoutData(gd_btnGo_1);
		btnGo_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String phone = txtPhonenumber.getText();
				try {
					Customer c = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("phone", "%" + phone + "%", SearchCriteria.LIKE));
					if(c != null) {
						AppData.getInstance().setLookupCustomer(c);
						dispose();
					} else {
						// TODO: extra: show some feedback to the user that the lookup returned no results.
					}
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGo_1.setText("Go");
		
		Group grpStatusInformation = new Group(this, SWT.NONE);
		grpStatusInformation.setLayout(new GridLayout(1, false));
		grpStatusInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpStatusInformation.setText("Status Information");
		
		lblCustNotFound = new Label(grpStatusInformation, SWT.NONE);
		lblCustNotFound.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				dispose();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.widthHint = 100;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Cancel");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MyStuff | Customer Lookup");
		setSize(267, 210);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

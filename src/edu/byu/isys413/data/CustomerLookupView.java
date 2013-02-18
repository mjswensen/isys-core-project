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

public class CustomerLookupView extends Shell {
	private Text txtLastname;
	private Text txtPhonenumber;

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
		setLayout(new GridLayout(3, false));
		
		Label lblLastName = new Label(this, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Last Name:");
		
		txtLastname = new Text(this, SWT.BORDER);
		txtLastname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo = new Button(this, SWT.NONE);
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
						// TODO: extra: show some feedback to the user that the lookup returned no results.
					}
				} catch (DataException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGo.setText("Go");
		
		Label lblPhoneNumber = new Label(this, SWT.NONE);
		lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhoneNumber.setText("Phone Number:");
		
		txtPhonenumber = new Text(this, SWT.BORDER);
		txtPhonenumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnGo_1 = new Button(this, SWT.NONE);
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
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Customer Lookup");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

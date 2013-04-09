package edu.byu.isys413.data.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Employee;
import edu.byu.isys413.data.models.LDAP;
import edu.byu.isys413.data.models.SearchCriteria;

public class LoginView extends Shell {
	private Text txtNetid;
	private Text txtPassword;
	private int tryCount = 1;
	private Label errorLabel;

	/**
	 * Launch the application. Only used for testing.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			LoginView shell = new LoginView(display);
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
	public LoginView(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setLayout(new GridLayout(2, false));
		
		Label lblNetid = new Label(this, SWT.NONE);
		lblNetid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNetid.setText("NetID:");
		
		txtNetid = new Text(this, SWT.BORDER);
		txtNetid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(this, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password:");
		
		txtPassword = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		errorLabel = new Label(this, SWT.NONE);
		errorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		errorLabel.setText("");
		new Label(this, SWT.NONE);
		
		Button btnLogIn = new Button(this, SWT.NONE);
		btnLogIn.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseUp(MouseEvent e) {
				String netId = txtNetid.getText();
				String passwd = txtPassword.getText();
				
				LDAP ldap = new LDAP();
				if(ldap.authenticate(netId, passwd)){
					Employee emp = null;
					try {
						emp = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("netid", netId));
					} catch (DataException ex) {
						ex.printStackTrace();
					}
					AppData.getInstance().setLoggedIn(true);
		            AppData.getInstance().setEmployee(emp);
		            dispose();
				}
				else{
					
					if(tryCount <3){
						errorLabel.setText(("Incorrect username and/or password. Please try again.")); 
						tryCount++;
					}
					else{
						MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR); 
						messageBox.setMessage("Login failed. Please try again later."); 
						messageBox.open(); 
						dispose();
					}
				}
				
			}
		});
		btnLogIn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnLogIn.setText("Log In");
		
		
		createContents();
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Log In");
		setSize(425, 135);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

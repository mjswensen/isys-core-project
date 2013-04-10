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

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Membership;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CustomerInfoView extends Shell {
	private Text txtFirstname;
	private Text txtLastname;
	private Text txtPhone;
	private Text txtEmail;
	private Text txtAddress;
	private Button btnSave;
	private Composite composite;
	private Button btnCancel;
	private Group grpCustomerInformation;
	private Group grpMembershipInformation;
	private Label lblCreditCard;
	private Text txtCreditCard;
	
	private Membership member = null;

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			CustomerInfoView shell = new CustomerInfoView(display);
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
	public CustomerInfoView(Display display, final Customer c) {
		super(display, SWT.SHELL_TRIM);
		setMinimumSize(new Point(320, 310));
		setImage(SWTResourceManager.getImage(CustomerInfoView.class, "/images/logo_camera.png"));
		setLayout(new GridLayout(1, false));
		
		grpCustomerInformation = new Group(this, SWT.NONE);
		grpCustomerInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpCustomerInformation.setText("Personal Information");
		grpCustomerInformation.setLayout(new GridLayout(2, false));
		
		Label lblFirstName = new Label(grpCustomerInformation, SWT.NONE);
		lblFirstName.setText("First Name:");
		
		txtFirstname = new Text(grpCustomerInformation, SWT.BORDER);
		GridData gd_txtFirstname = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtFirstname.widthHint = 200;
		txtFirstname.setLayoutData(gd_txtFirstname);
		
		Label lblLastName = new Label(grpCustomerInformation, SWT.NONE);
		lblLastName.setText("Last Name:");
		
		txtLastname = new Text(grpCustomerInformation, SWT.BORDER);
		GridData gd_txtLastname = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtLastname.widthHint = 200;
		txtLastname.setLayoutData(gd_txtLastname);
		
		Label lblPhone = new Label(grpCustomerInformation, SWT.NONE);
		lblPhone.setText("Phone:");
		
		txtPhone = new Text(grpCustomerInformation, SWT.BORDER);
		GridData gd_txtPhone = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtPhone.widthHint = 100;
		txtPhone.setLayoutData(gd_txtPhone);
		
		Label lblEmail = new Label(grpCustomerInformation, SWT.NONE);
		lblEmail.setText("Email:");
		
		txtEmail = new Text(grpCustomerInformation, SWT.BORDER);
		GridData gd_txtEmail = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtEmail.widthHint = 200;
		txtEmail.setLayoutData(gd_txtEmail);
		
		Label lblAddress = new Label(grpCustomerInformation, SWT.NONE);
		lblAddress.setText("Address:");
		
		txtAddress = new Text(grpCustomerInformation, SWT.BORDER);
		GridData gd_txtAddress = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 2);
		gd_txtAddress.widthHint = 200;
		txtAddress.setLayoutData(gd_txtAddress);
		new Label(grpCustomerInformation, SWT.NONE);
		
		grpMembershipInformation = new Group(this, SWT.NONE);
		grpMembershipInformation.setLayout(new GridLayout(2, false));
		grpMembershipInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpMembershipInformation.setText("Membership Information");
		
		lblCreditCard = new Label(grpMembershipInformation, SWT.NONE);
		lblCreditCard.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCreditCard.setText("Credit Card");
		
		txtCreditCard = new Text(grpMembershipInformation, SWT.BORDER);
		txtCreditCard.setBackground(SWTResourceManager.getColor(255, 255, 255));
		txtCreditCard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				dispose();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_btnNewButton.widthHint = 100;
		btnCancel.setLayoutData(gd_btnNewButton);
		btnCancel.setText("Cancel");
		
		btnSave = new Button(composite, SWT.NONE);
		GridData gd_btnSave = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSave.widthHint = 100;
		btnSave.setLayoutData(gd_btnSave);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					c.setFirstName(txtFirstname.getText());
					c.setLastName(txtLastname.getText());
					c.setPhone(txtPhone.getText());
					c.setEmail(txtEmail.getText());
					c.setAddress(txtAddress.getText());
					c.save();
					if(!txtCreditCard.getText().equalsIgnoreCase("")) {
						// There is a credit card number in the field.
						String creditCard = txtCreditCard.getText();
						if(c.getMembership() == null) {
							// This customer doesn't have a membership yet, so let's create it.
							Membership m = BusinessObjectDAO.getInstance().create("Membership");
							m.setCustomer(c);
							m.setStartDate(new Date());
							m.setTrial(false);
							m.setCreditCard(creditCard);
							m.save();
							/* 
							 * Let's give the customer an online password now that they have a membership.
							 * The default password is their credit card number. They will later be able
							 * to log in online and change their password (in a future iteration of the system).
							 */
							c.setPassword(creditCard);
							c.save();
						} else {
							// This customer has a membership already, so let's update it.
							Membership m = c.getMembership();
							m.setCreditCard(creditCard);
						}
					}
				} catch (DataException e1) {
					// TODO
				}
				dispose();
			}
		});
		btnSave.setText("Save");
		createContents();
		
		// Populate fields with customer information.
		txtFirstname.setText(c.getFirstName());
		txtLastname.setText(c.getLastName());
		txtPhone.setText(c.getPhone());
		txtEmail.setText(c.getEmail());
		txtAddress.setText(c.getAddress());
		try {
			if(c.getMembership() != null) {
				txtCreditCard.setText(c.getMembership().getCreditCard());
			}
		} catch(DataException e) {}
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("MyStuff | Customer Information");
		setSize(320, 264);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

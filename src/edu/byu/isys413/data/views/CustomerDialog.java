/**
 * 
 */
package edu.byu.isys413.data.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Membership;

/**TODO: Add a description
 *
 * @author Morgan S. Young
 *
 *
 * Feb 18, 2013
 */
public class CustomerDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	
	private Customer cust = null;
	private Membership member = null;
	
	private Text textFirstName;
	private Text textPhone;
	private Text textAddress;
	
	private Text textEmail;
	private Text textCardNumber;
	private Text textMiddleName;
	private Text textlastName;
	
	Label lblCardNumber = null;
	Label lblExpDate = null;
	
	Label lblCustPassword = null;
	Label lblValCode = null;
	Label lblValidation = null;
	
	Button btnAddMembership = null;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CustomerDialog(Shell parent, int style, Customer cust) {
		super(parent, style);
		setText("SWT Dialog");
		this.cust = cust;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 * @throws DataException 
	 */
	public Object open() throws DataException {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 * @throws DataException 
	 */
	private void createContents() throws DataException {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setMinimumSize(new Point(400, 470));
		shell.setSize(400, 380);
		shell.setText("My Stuff | Group 9 Sec. 1");
		shell.setLayout(new GridLayout(2, false));
		
		Group grpEmployeeInformation = new Group(shell, SWT.NONE);
		grpEmployeeInformation.setLayout(new GridLayout(2, false));
		grpEmployeeInformation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2));
		grpEmployeeInformation.setText("Customer Information");
		
		Label lblFirstName = new Label(grpEmployeeInformation, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First Name");
		
		textFirstName = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textFirstName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textFirstName.widthHint = 165;
		textFirstName.setLayoutData(gd_textFirstName);
		
		Label lblMiddleName = new Label(grpEmployeeInformation, SWT.NONE);
		lblMiddleName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMiddleName.setText("Middle Name");
		
		textMiddleName = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textMiddleName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textMiddleName.widthHint = 165;
		textMiddleName.setLayoutData(gd_textMiddleName);
		
		Label lblLastname = new Label(grpEmployeeInformation, SWT.NONE);
		lblLastname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastname.setText("LastName");
		
		textlastName = new Text(grpEmployeeInformation, SWT.BORDER);
		textlastName.setText("");
		GridData gd_textlastName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textlastName.widthHint = 165;
		textlastName.setLayoutData(gd_textlastName);
		Label lblLastName = new Label(grpEmployeeInformation, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Phone");
		
		textPhone = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textPhone = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textPhone.widthHint = 100;
		textPhone.setLayoutData(gd_textPhone);
		
		Label lblEmail = new Label(grpEmployeeInformation, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email");
		
		textEmail = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textEmail = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textEmail.widthHint = 165;
		textEmail.setLayoutData(gd_textEmail);
		
		Label lbAddress = new Label(grpEmployeeInformation, SWT.NONE);
		lbAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbAddress.setText("Address");
		
		textAddress = new Text(grpEmployeeInformation, SWT.BORDER | SWT.WRAP);
		GridData gd_textAddress = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 2);
		gd_textAddress.widthHint = 165;
		textAddress.setLayoutData(gd_textAddress);
		new Label(grpEmployeeInformation, SWT.NONE);
		
		Group grpOnlineValidation = new Group(grpEmployeeInformation, SWT.NONE);
		grpOnlineValidation.setText("Online Account Information");
		grpOnlineValidation.setLayout(new GridLayout(2, false));
		grpOnlineValidation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		Label lblPassword = new Label(grpOnlineValidation, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password");
		
		lblCustPassword = new Label(grpOnlineValidation, SWT.NONE);
		
		Label lblValidationCode = new Label(grpOnlineValidation, SWT.NONE);
		lblValidationCode.setText("Validation Code");
		
		lblValCode = new Label(grpOnlineValidation, SWT.NONE);
		GridData gd_lblValCode = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblValCode.widthHint = 165;
		lblValCode.setLayoutData(gd_lblValCode);
		lblValCode.setSize(0, 15);
		
		Label lblIsValidated = new Label(grpOnlineValidation, SWT.NONE);
		lblIsValidated.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblIsValidated.setText("Is Validated?");
		
		lblValidation = new Label(grpOnlineValidation, SWT.NONE);
		
		Group grpMembershipInformation = new Group(shell, SWT.NONE);
		grpMembershipInformation.setLayout(new GridLayout(2, false));
		grpMembershipInformation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpMembershipInformation.setText("Membership Information");
		
		btnAddMembership = new Button(grpMembershipInformation, SWT.NONE);
		btnAddMembership.setEnabled(false);
		btnAddMembership.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnAddMembership.setText("+ Add Membership");
		
		Group grpCreditCardInformation = new Group(grpMembershipInformation, SWT.NONE);
		grpCreditCardInformation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpCreditCardInformation.setText("Credit Card Information");
		grpCreditCardInformation.setLayout(new GridLayout(2, false));
		
		lblCardNumber = new Label(grpCreditCardInformation, SWT.NONE);
		lblCardNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCardNumber.setText("Card#");
		
		textCardNumber = new Text(grpCreditCardInformation, SWT.BORDER);
		GridData gd_textCardNumber = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textCardNumber.widthHint = 165;
		textCardNumber.setLayoutData(gd_textCardNumber);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_btnNewButton.widthHint = 150;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Cancel");
		
		Button btnSave = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton_11 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnNewButton_11.widthHint = 150;
		btnSave.setLayoutData(gd_btnNewButton_11);
		btnSave.setText("Save");
		
		//********** Fill in the Fields **********
		
		if(cust != null)
		{
			textFirstName.setText(cust.getFirstName());
			textlastName.setText(cust.getLastName());
			textPhone.setText(cust.getPhone());
			textAddress.setText(cust.getAddress());		
			textEmail.setText(cust.getEmail());
			
			if(cust.getPassword() != null)
			{
				lblCustPassword.setText(cust.getPassword());
				lblValCode.setText(cust.getValidationCode());
			}//if
			
			lblValidation.setText(Boolean.toString(cust.isValid()));
						
			if(cust.getMembership() == null)
			{
				btnAddMembership.setEnabled(true);
			}//if
			
			else
			{
				textCardNumber.setText(cust.getMembership().getCreditCard());
			}//else
			
		}//if
		
		else
		{
			try 
			{
				cust = BusinessObjectDAO.getInstance().create("Customer");

				btnAddMembership.setEnabled(true);
			}//try
			
			catch(DataException de)
			{
				System.out.println("The New Customer could not be created");
			}//catch
		}//else
				
// ========== Buttons ==========
		
		btnAddMembership.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				try 
				{
					if(!textCardNumber.getText().equalsIgnoreCase(""))
					{
						member = BusinessObjectDAO.getInstance().create("Membership");
						member.setCustomer(cust);
						member.setCreditCard(textCardNumber.getText());
						
						btnAddMembership.setEnabled(false);
						
						//TODO enter in a lable messace "Membership Created"
					}//if
					
					else
					{
						//TODO enter in a lable messace "Membership not Created"
					}//else
				}//try
				
				catch (DataException ex) 
				{
					System.out.println("Membership Malfunctioning");
					//ex.printStackTrace();
				}//catch
			}
		});
		
		btnSave.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				try 
				{
					cust.setFirstName(textFirstName.getText());
					cust.setLastName(textlastName.getText());
					cust.setPhone(textPhone.getText());
					cust.setAddress(textAddress.getText());		
					cust.setEmail(textEmail.getText());
					
					if(member.isDirty())
					{
						member.save();
					}//if
					
					if(cust.isDirty())
					{
						cust.save();
					}//if
					
				}//try
				
				catch (DataException ex1) 
				{
					System.out.println("Customer or Membership could not be Saved");
				}//catch
			
				finally
				{
					shell.close();
				}//finally
			}
		});
		
		btnNewButton.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				shell.close();
			}
		});
		
	}//createContents
}//CustomerDetailDialog

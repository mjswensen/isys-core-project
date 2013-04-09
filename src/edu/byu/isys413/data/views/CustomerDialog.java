/**
 * 
 */
package edu.byu.isys413.data.views;

import java.util.ArrayList;
import java.util.List;

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

import edu.byu.isys413.data.BusinessObjectDAO;
import edu.byu.isys413.data.Customer;
import edu.byu.isys413.data.DataException;
import edu.byu.isys413.data.Membership;
import edu.byu.isys413.extra.UploadStates;
import edu.byu.isys413.extra.UsState;

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
	private Text textCity;
	private Text textZip;
	private Text textAddress;
	
	List<UsState> states = new ArrayList<UsState>();
	
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
		shell.setMinimumSize(new Point(400, 535));
		shell.setSize(400, 535);
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
		
		textAddress = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textAddress = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textAddress.widthHint = 165;
		textAddress.setLayoutData(gd_textAddress);
		
		Label lblCity = new Label(grpEmployeeInformation, SWT.NONE);
		lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCity.setText("City");
		
		textCity = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textCity = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textCity.widthHint = 100;
		textCity.setLayoutData(gd_textCity);
		
		Label lblSallary = new Label(grpEmployeeInformation, SWT.NONE);
		lblSallary.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSallary.setText("State");
		
		final Combo comboState = new Combo(grpEmployeeInformation, SWT.READ_ONLY);
		GridData gd_comboState = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboState.widthHint = 85;
		comboState.setLayoutData(gd_comboState);
		
		Label lblPosition = new Label(grpEmployeeInformation, SWT.NONE);
		lblPosition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPosition.setText("Zip Code");
		
		textZip = new Text(grpEmployeeInformation, SWT.BORDER);
		GridData gd_textZip = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_textZip.widthHint = 100;
		textZip.setLayoutData(gd_textZip);
		
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
		
		//********** Uploading States **********
		
		UploadStates stateXml = new UploadStates();
		this.states = stateXml.getStates();
		populateCombo(comboState);
		
		//********** Fill in the Fields **********
		
		if(cust != null)
		{
			textFirstName.setText(cust.getCustFirstName());
			textMiddleName.setText(cust.getCustMiddleName());
			textlastName.setText(cust.getCustLastName());
			textPhone.setText(cust.getCustPhone());
			textCity.setText(cust.getCustCity());
			textZip.setText(cust.getCustZip());
			textAddress.setText(cust.getCustAddress());		
			textEmail.setText(cust.getEmail());
			
			if(cust.getCustPassword() != null)
			{
				lblCustPassword.setText(cust.getCustPassword());
				lblValCode.setText(cust.getValidationCode());
			}//if
			
			lblValidation.setText(Boolean.toString(cust.isIsValidated()));
						
			if(cust.getCustMembershipID() == null || cust.getCustMembershipID().equalsIgnoreCase(""))
			{
				btnAddMembership.setEnabled(true);
			}//if
			
			else
			{
				textCardNumber.setText(cust.getMembership().getCardNumber());
			}//else
			
			for(int i = 0; i < states.size(); i++)
			{
				if(states.get(i).getLongName().equalsIgnoreCase(cust.getCustState()))
				{
					comboState.select(i);
					break;
				}//if
			}//for
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
						member.setCust(cust);
						member.setCardNumber(textCardNumber.getText());
						
						cust.setMembership(member);
						
						btnAddMembership.setEnabled(false);
						
						MiniDialog window = new MiniDialog(shell, SWT.APPLICATION_MODAL, "info", "The new membweship was added.");
						window.open();
					}//if
					
					else
					{
						MiniDialog window = new MiniDialog(shell, SWT.APPLICATION_MODAL, "warning", "The new membweship was not created. " +
								"Please enter valid credit card number in the \"Card#\" field.");
						window.open();
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
					cust.setCustFirstName(textFirstName.getText());
					cust.setCustMiddleName(textMiddleName.getText());
					cust.setCustLastName(textlastName.getText());
					cust.setCustPhone(textPhone.getText());
					cust.setCustCity(textCity.getText());
					cust.setCustZip(textZip.getText());
					cust.setCustAddress(textAddress.getText());		
					cust.setEmail(textEmail.getText());
					
					for(UsState us: states)
					{
						if(us.getShortName().equalsIgnoreCase(comboState.getText()))
						{
							cust.setCustState(us.getLongName());
						}//if
					}//for
					
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
	
	public void populateCombo(Combo combo)
	{
		
		for (int i = 0; i < states.size(); i++)
		{
			combo.add(states.get(i).getShortName());
		}
	}//populateCombo
}//CustomerDetailDialog

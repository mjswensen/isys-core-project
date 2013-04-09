/**
 * 
 */
package edu.byu.isys413.data.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.data.BusinessObjectDAO;
import edu.byu.isys413.data.Customer;
import edu.byu.isys413.data.DataException;
import edu.byu.isys413.extra.PassingClass;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

/**TODO: Add a description
 *
 * @author Morgan S. Young
 *
 *
 * Mar 5, 2013
 */
public class CustomerListDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table customerTable;
	private Text searchText;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CustomerListDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
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
		shell.setMinimumSize(new Point(485, 350));
		shell.setSize(485, 216);
		shell.setText("My Stuff | Group 9 Sec. 1");
		shell.setLayout(new GridLayout(1, false));
		
		Group grpPhysicalProductTo = new Group(shell, SWT.NONE);
		grpPhysicalProductTo.setLayout(new GridLayout(1, false));
		GridData gd_grpPhysicalProductTo = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpPhysicalProductTo.widthHint = 523;
		grpPhysicalProductTo.setLayoutData(gd_grpPhysicalProductTo);
		grpPhysicalProductTo.setText("Customers");
		
		Composite composite_1 = new Composite(grpPhysicalProductTo, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		composite_1.setLayout(new GridLayout(4, false));
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("Search By:");
		
		Combo searchCombo = new Combo(composite_1, SWT.READ_ONLY);
		GridData gd_searchCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_searchCombo.widthHint = 150;
		searchCombo.setLayoutData(gd_searchCombo);
		
		Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("Search:");
		
		searchText = new Text(composite_1, SWT.BORDER);
		GridData gd_searchText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_searchText.widthHint = 150;
		searchText.setLayoutData(gd_searchText);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(grpPhysicalProductTo, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		final TableViewer custTableViewer = new TableViewer(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		customerTable = custTableViewer.getTable();
		customerTable.setLinesVisible(true);
		customerTable.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getId();
			}//getText
		});
		TableColumn tableColumn = tableViewerColumn.getColumn();
		tableColumn.setWidth(150);
		tableColumn.setText("Customer ID");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustMembershipID();
			}//getText
		});
		TableColumn tableColumn_1 = tableViewerColumn_1.getColumn();
		tableColumn_1.setWidth(120);
		tableColumn_1.setText("Membership ID");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustFirstName();
			}//getText
		});
		TableColumn tableColumn_2 = tableViewerColumn_2.getColumn();
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("First Name");
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_9.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustMiddleName();
			}//getText
		});
		TableColumn tblclmnMiddleName = tableViewerColumn_9.getColumn();
		tblclmnMiddleName.setWidth(100);
		tblclmnMiddleName.setText("Middle Name");
		
		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_10.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustLastName();
			}//getText
		});
		TableColumn tblclmnLastName = tableViewerColumn_10.getColumn();
		tblclmnLastName.setWidth(100);
		tblclmnLastName.setText("Last Name");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustPhone();
			}//getText
		});
		TableColumn tableColumn_3 = tableViewerColumn_3.getColumn();
		tableColumn_3.setWidth(120);
		tableColumn_3.setText("Phone Number");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getEmail();
			}//getText
		});
		TableColumn tableColumn_4 = tableViewerColumn_4.getColumn();
		tableColumn_4.setWidth(200);
		tableColumn_4.setText("Email");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_5.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustAddress();
			}//getText
		});
		TableColumn tableColumn_5 = tableViewerColumn_5.getColumn();
		tableColumn_5.setWidth(120);
		tableColumn_5.setText("Address");
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_6.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustCity();
			}//getText
		});
		TableColumn tableColumn_6 = tableViewerColumn_6.getColumn();
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("City");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_7.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustState();
			}//getText
		});
		TableColumn tableColumn_7 = tableViewerColumn_7.getColumn();
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("State");
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(custTableViewer, SWT.NONE);
		tableViewerColumn_8.setLabelProvider(new ColumnLabelProvider() 
		{
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getCustZip();
			}//getText
		});
		TableColumn tableColumn_8 = tableViewerColumn_8.getColumn();
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("Zip Code");
		
		scrolledComposite.setContent(customerTable);
		scrolledComposite.setMinSize(customerTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
		Composite composite = new Composite(grpPhysicalProductTo, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Button btnCancel = new Button(composite, SWT.NONE);
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 150;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		
		Button btnOk = new Button(composite, SWT.NONE);
		GridData gd_btnOk = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnOk.widthHint = 150;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");
		
		//========== Table Viewer ==========
		
		custTableViewer.setContentProvider(new ArrayContentProvider());
		custTableViewer.setInput(BusinessObjectDAO.getInstance().searchForAll("Customer"));
		
		//========== Buttons ==========
		
		btnCancel.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				try 
				{
					PassingClass.getInstance().setObj(null);
				} 
				
				catch (DataException ex) 
				{
					System.out.println("Customer item not set");
					ex.printStackTrace();
				}
				
				shell.close();
			}
		});
		
		btnOk.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				IStructuredSelection selection = (IStructuredSelection) custTableViewer.getSelection();
				Customer cust = (Customer) selection.getFirstElement();
				
				try 
				{
					PassingClass.getInstance().setObj(cust);
				} 
				
				catch (DataException ex) 
				{
					System.out.println("Customer item not set");
					ex.printStackTrace();
				}
				
				shell.close();
			}
		});
	}

}

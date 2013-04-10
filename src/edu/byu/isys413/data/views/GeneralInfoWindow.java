/**
 * GEBERAL INFO WINDOW
 */
package edu.byu.isys413.data.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Customer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Employee;
import edu.byu.isys413.data.models.Store;
import edu.byu.isys413.data.models.StoreProduct;



/** This window allows you to manage everything from customers to computers
 *
 * @author Morgan S. Young
 *
 *
 * Feb 18, 2013
 */
public class GeneralInfoWindow extends Dialog {

	protected Object result;
	protected Shell shlMyStuff;
	private Table empTable;
	private Table storeTable;
	private Table conProdTable;
	private Table storeProdStoreTable;
	private Table custTable;
	private Text conProdSearchBox;
	private Text storeSearchBox;
	private Text storeProdSearchBox;
	private Text empSearchBox;
	private Text custSearchBox;
	private static int tab = 0;
	private Text conRentSearch;
	private Table conRentTable;
	private Table physRentTable;
	private Text physRentSearch;
	private Table table;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 * @param tab 
	 */
	public GeneralInfoWindow(Shell parent, int style, int tab) {
		super(parent, style);
		setText("SWT Dialog");
		GeneralInfoWindow.tab = tab;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 * @throws DataException 
	 */
	public Object open() throws DataException {
		createContents();
		shlMyStuff.open();
		shlMyStuff.layout();
		Display display = getParent().getDisplay();
		while (!shlMyStuff.isDisposed()) {
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
		shlMyStuff = new Shell();
		shlMyStuff.setImage(SWTResourceManager.getImage(GeneralInfoWindow.class, "/images/logo_camera.png"));
		shlMyStuff.setMinimumSize(new Point(900, 500));
		shlMyStuff.setSize(801, 501);
		shlMyStuff.setText("My Stuff | Management Window");
		shlMyStuff.setLayout(new GridLayout(1, false));
		
		CTabFolder tabFolder = new CTabFolder(shlMyStuff, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
//========== Customer Tab ==========
		
		CTabItem custTab = new CTabItem(tabFolder, SWT.NONE);
		custTab.setImage(SWTResourceManager.getImage(GeneralInfoWindow.class, "/images/customers.png"));
		custTab.setText("Customers");
		
		Composite composite_13 = new Composite(tabFolder, SWT.NONE);
		custTab.setControl(composite_13);
		composite_13.setLayout(new GridLayout(5, false));
		
		Composite composite_10 = new Composite(composite_13, SWT.NONE);
		composite_10.setLayout(new GridLayout(4, false));
		composite_10.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));
		
		Label lblSearchBy_3 = new Label(composite_10, SWT.NONE);
		lblSearchBy_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearchBy_3.setText("Search By:");
		
		Combo custColumCombo = new Combo(composite_10, SWT.READ_ONLY);
		GridData gd_custColumCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_custColumCombo.widthHint = 150;
		custColumCombo.setLayoutData(gd_custColumCombo);
		
		Label lblSearch_2 = new Label(composite_10, SWT.NONE);
		lblSearch_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearch_2.setText("Search:");
		
		custSearchBox = new Text(composite_10, SWT.BORDER);
		GridData gd_custSearchBox = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_custSearchBox.widthHint = 150;
		custSearchBox.setLayoutData(gd_custSearchBox);
		
		Group group = new Group(composite_13, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 2));
		group.setText("Customer Table");
		group.setLayout(new GridLayout(1, false));
		
		ScrolledComposite scrolledComposite_5 = new ScrolledComposite(group, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_scrolledComposite_5 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_scrolledComposite_5.widthHint = 432;
		gd_scrolledComposite_5.heightHint = 267;
		scrolledComposite_5.setLayoutData(gd_scrolledComposite_5);
		scrolledComposite_5.setExpandVertical(true);
		scrolledComposite_5.setExpandHorizontal(true);
		
		final TableViewer CustTableViewer = new TableViewer(scrolledComposite_5, SWT.BORDER | SWT.FULL_SELECTION);
		custTable = CustTableViewer.getTable();
		custTable.setLinesVisible(true);
		custTable.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getLastName() + ", " + cust.getFirstName();
			}
		});
		TableColumn tblclmnName_2 = tableViewerColumn_4.getColumn();
		tblclmnName_2.setWidth(150);
		tblclmnName_2.setText("Name");
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_9.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getPhone();
			}
		});
		TableColumn tblclmnPhoneNimber = tableViewerColumn_9.getColumn();
		tblclmnPhoneNimber.setWidth(120);
		tblclmnPhoneNimber.setText("Phone Number");
		
		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_10.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getEmail();
			}
		});
		TableColumn tblclmnEmail = tableViewerColumn_10.getColumn();
		tblclmnEmail.setWidth(200);
		tblclmnEmail.setText("Email");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_5.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getAddress();
			}
		});
		TableColumn tblclmnAddress_1 = tableViewerColumn_5.getColumn();
		tblclmnAddress_1.setWidth(120);
		tblclmnAddress_1.setText("Address");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				String answer = "NO";
				
				try 
				{
					if(cust.getMembership() == null)
					{
						
						answer = "YES";
					}
				} 
				
				catch (DataException ex) 
				{
					System.out.println("Problem with Membership");
					ex.printStackTrace();
				}
				
				return answer;
			}
		});
		TableColumn isMember = tableViewerColumn_3.getColumn();
		isMember.setWidth(100);
		isMember.setText("Membership?");
		
		TableViewerColumn tableViewerColumn_50 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_50.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getPassword();
			}
		});
		TableColumn tblclmnOnlinePassword = tableViewerColumn_50.getColumn();
		tblclmnOnlinePassword.setWidth(110);
		tblclmnOnlinePassword.setText("Online Password");
		
		TableViewerColumn tableViewerColumn_51 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_51.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return cust.getValidationCode();
			}
		});
		TableColumn tblclmnValidationCode = tableViewerColumn_51.getColumn();
		tblclmnValidationCode.setWidth(120);
		tblclmnValidationCode.setText("Validation Code");
		
		TableViewerColumn tableViewerColumn_52 = new TableViewerColumn(CustTableViewer, SWT.NONE);
		tableViewerColumn_52.setLabelProvider(new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) 
			{
				Customer cust = (Customer) element;
				return Boolean.toString(cust.isValid());
			}
		});
		TableColumn tblclmnIsValidated = tableViewerColumn_52.getColumn();
		tblclmnIsValidated.setWidth(100);
		tblclmnIsValidated.setText("Validated?");
		scrolledComposite_5.setContent(custTable);
		scrolledComposite_5.setMinSize(custTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite_5.setMinSize(new Point(866, 45));
		
		Button btnEditCustomer = new Button(composite_13, SWT.NONE);
		GridData gd_btnEditSelectedEmployee_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditSelectedEmployee_1.widthHint = 200;
		btnEditCustomer.setLayoutData(gd_btnEditSelectedEmployee_1);
		btnEditCustomer.setText("Edit Selected Customer");
		
		btnEditCustomer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				IStructuredSelection selection = (IStructuredSelection) CustTableViewer.getSelection();
				Customer cust = (Customer) selection.getFirstElement();
				
				CustomerInfoView window = new CustomerInfoView(Display.getDefault(), cust);
				
				window.open();
				
				CustTableViewer.refresh();
			}
		});
		new Label(composite_13, SWT.NONE);
		new Label(composite_13, SWT.NONE);
		new Label(composite_13, SWT.NONE);
		
		Button btnCustExit = new Button(composite_13, SWT.NONE);
		
		GridData gd_btnNewButton_1 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 150;
		btnCustExit.setLayoutData(gd_btnNewButton_1);
		btnCustExit.setText("Exit");
		
		//********** Customer Table Viewer **********
		
		CustTableViewer.setContentProvider(new ArrayContentProvider());
		CustTableViewer.setInput(BusinessObjectDAO.getInstance().searchForAll("Customer"));
		
		btnCustExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				shlMyStuff.close();
			}
		});
		
//========== Employee Tab ==========
		
		CTabItem empTab = new CTabItem(tabFolder, SWT.NONE);
		empTab.setImage(SWTResourceManager.getImage(GeneralInfoWindow.class, "/images/employee.png"));
		empTab.setText("Employees");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		empTab.setControl(composite);
		composite.setLayout(new GridLayout(8, false));
		
		Composite composite_8 = new Composite(composite, SWT.NONE);
		composite_8.setLayout(new GridLayout(4, false));
		composite_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		
		Label lblSearchBy_2 = new Label(composite_8, SWT.NONE);
		lblSearchBy_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearchBy_2.setText("Search By:");
		
		Combo EmpColumnCombo = new Combo(composite_8, SWT.READ_ONLY);
		GridData gd_EmpColumnCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_EmpColumnCombo.widthHint = 150;
		EmpColumnCombo.setLayoutData(gd_EmpColumnCombo);
		
		Label lblSearch_1 = new Label(composite_8, SWT.NONE);
		lblSearch_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearch_1.setText("Search:");
		
		empSearchBox = new Text(composite_8, SWT.BORDER);
		GridData gd_empSearchBox = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_empSearchBox.widthHint = 150;
		empSearchBox.setLayoutData(gd_empSearchBox);
		
		Group grpEmployees = new Group(composite, SWT.NONE);
		grpEmployees.setLayout(new GridLayout(1, false));
		grpEmployees.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		grpEmployees.setText("Employees");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(grpEmployees, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		TableViewer EmpTableViewer = new TableViewer(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		empTable = EmpTableViewer.getTable();
		empTable.setHeaderVisible(true);
		empTable.setLinesVisible(true);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getId();
			}
		});
		TableColumn tblclmnEmployeeId = tableViewerColumn.getColumn();
		tblclmnEmployeeId.setWidth(200);
		tblclmnEmployeeId.setText("Employee ID");
		
		TableViewerColumn empName = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		empName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getLastName() + ", " + emp.getFirstName() + " " + emp.getMiddleName();
			}
		});
		TableColumn tblclmnName = empName.getColumn();
		tblclmnName.setWidth(120);
		tblclmnName.setText("Name");
		
		TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		tableViewerColumn_12.setLabelProvider(new ColumnLabelProvider() 
		{
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getNetid();
			}
		});
		TableColumn tblclmnUserName = tableViewerColumn_12.getColumn();
		tblclmnUserName.setWidth(100);
		tblclmnUserName.setText("User Name");
		
		TableViewerColumn empStore = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		empStore.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getStoreId();
			}
		});
		TableColumn tblclmnStore = empStore.getColumn();
		tblclmnStore.setWidth(200);
		tblclmnStore.setText("Store ID");
		
		TableViewerColumn hireDate = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		hireDate.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.format(emp.getHireDate());
			}
		});
		TableColumn tblclmnHireDate = hireDate.getColumn();
		tblclmnHireDate.setWidth(100);
		tblclmnHireDate.setText("Hire Date");
		
		TableViewerColumn empPhone = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		empPhone.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getPhone();
			}
		});
		TableColumn tblclmnPhone = empPhone.getColumn();
		tblclmnPhone.setWidth(120);
		tblclmnPhone.setText("Phone");
		
		TableViewerColumn empSalary = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		empSalary.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return Double.toString(emp.getSalary());
			}
		});
		TableColumn tblclmnSalary = empSalary.getColumn();
		tblclmnSalary.setWidth(100);
		tblclmnSalary.setText("Salary");
		
		TableViewerColumn empPosition = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		empPosition.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getPositionId();
			}
		});
		TableColumn tblclmnPodsition = empPosition.getColumn();
		tblclmnPodsition.setWidth(100);
		tblclmnPodsition.setText("PositionID");
		
		TableViewerColumn empDivision = new TableViewerColumn(EmpTableViewer, SWT.NONE);
		empDivision.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Employee emp = (Employee) element;
				return emp.getDivisionId();
			}
		});
		TableColumn tblclmnDivision = empDivision.getColumn();
		tblclmnDivision.setWidth(120);
		tblclmnDivision.setText("DivisionID");
		scrolledComposite.setContent(empTable);
		scrolledComposite.setMinSize(empTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button addNewEmp = new Button(composite, SWT.NONE);
		GridData gd_addNewEmp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_addNewEmp.widthHint = 200;
		addNewEmp.setLayoutData(gd_addNewEmp);
		addNewEmp.setText("+ Add New Employee");
		
		// ========== Buttons ==========
		
		// ***** Employee Buttons *****
		
		addNewEmp.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
//				DetailScreenEmployee empWindow = new DetailScreenEmployee();
//				empWindow.open();
			}
		});
		
		Button btnEditSelectedEmployee = new Button(composite, SWT.NONE);
		GridData gd_btnEditSelectedEmployee = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditSelectedEmployee.widthHint = 200;
		btnEditSelectedEmployee.setLayoutData(gd_btnEditSelectedEmployee);
		btnEditSelectedEmployee.setText("Edit Selected Employee");
		new Label(composite, SWT.NONE);
		
		Button btnEmpExit = new Button(composite, SWT.NONE);
		GridData gd_btnExit = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 5, 1);
		gd_btnExit.widthHint = 150;
		btnEmpExit.setLayoutData(gd_btnExit);
		btnEmpExit.setText("Exit");
		
// ========== TableViewer Population ==========
		
		EmpTableViewer.setContentProvider(new ArrayContentProvider());
		EmpTableViewer.setInput(BusinessObjectDAO.getInstance().searchForAll("Employee"));
		
		btnEditSelectedEmployee.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
//				DetailScreenEmployee empWindow = new DetailScreenEmployee();
//				empWindow.open();
			}
		});
				
		btnEmpExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				shlMyStuff.close();
			}
		});
		
//========== Store Tab ==========
		
		CTabItem storeTab = new CTabItem(tabFolder, SWT.NONE);
		storeTab.setImage(SWTResourceManager.getImage(GeneralInfoWindow.class, "/images/store.png"));
		storeTab.setText("Stores");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		storeTab.setControl(composite_2);
		composite_2.setLayout(new GridLayout(5, false));
		
		Composite composite_7 = new Composite(composite_2, SWT.NONE);
		composite_7.setLayout(new GridLayout(4, false));
		composite_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Label lblSearchBy = new Label(composite_7, SWT.NONE);
		lblSearchBy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearchBy.setText("Search By:");
		
		Combo storeColumnCombo = new Combo(composite_7, SWT.READ_ONLY);
		GridData gd_storeColumnCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_storeColumnCombo.widthHint = 150;
		storeColumnCombo.setLayoutData(gd_storeColumnCombo);
		
		Label lblSearch = new Label(composite_7, SWT.NONE);
		lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearch.setText("Search:");
		
		storeSearchBox = new Text(composite_7, SWT.BORDER);
		GridData gd_storeSearchBox = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_storeSearchBox.widthHint = 150;
		storeSearchBox.setLayoutData(gd_storeSearchBox);
		
		Group grpProductsInSelected = new Group(composite_2, SWT.NONE);
		grpProductsInSelected.setLayout(new GridLayout(2, true));
		GridData gd_grpProductsInSelected = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2);
		gd_grpProductsInSelected.widthHint = 390;
		grpProductsInSelected.setLayoutData(gd_grpProductsInSelected);
		grpProductsInSelected.setText("Store Products");
		
		Button btnAddProdToStore = new Button(grpProductsInSelected, SWT.NONE);
		btnAddProdToStore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddProdToStore.setText("+ Add Product to Selected Store");
		
		
		
		btnAddProdToStore.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
//				DetailScreenStoreProduct storeProductScreen = new DetailScreenStoreProduct();
//				storeProductScreen.open();
			}
		});
		
		Button btnEditStoreProd = new Button(grpProductsInSelected, SWT.NONE);
		btnEditStoreProd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEditStoreProd.setText(" Edit Store Product Details");
		
		ScrolledComposite scrolledComposite_3 = new ScrolledComposite(grpProductsInSelected, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		scrolledComposite_3.setExpandHorizontal(true);
		scrolledComposite_3.setExpandVertical(true);
		
		TableViewer StoreProdTableViewer = new TableViewer(scrolledComposite_3, SWT.BORDER | SWT.FULL_SELECTION);
		storeProdStoreTable = StoreProdTableViewer.getTable();
		storeProdStoreTable.setHeaderVisible(true);
		storeProdStoreTable.setLinesVisible(true);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				StoreProduct storeProd = (StoreProduct) element;
				return storeProd.getId();
			}
		});
		TableColumn tblclmnStoreProdID = tableViewerColumn_1.getColumn();
		tblclmnStoreProdID.setWidth(100);
		tblclmnStoreProdID.setText("Store Product ID");
		
		TableViewerColumn storeProdID = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		storeProdID.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				StoreProduct storeProd = (StoreProduct) element;
				return storeProd.getConceptualProductId();
			}
		});
		TableColumn tblclmnStoreProdConProdID = storeProdID.getColumn();
		tblclmnStoreProdConProdID.setWidth(100);
		tblclmnStoreProdConProdID.setText("Product ID");
		
		TableViewerColumn tableViewerColumn_42 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdSku = tableViewerColumn_42.getColumn();
		tblclmnStoreProdSku.setWidth(100);
		tblclmnStoreProdSku.setText("SKU");
		
		TableViewerColumn tableViewerColumn_37 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdProductName = tableViewerColumn_37.getColumn();
		tblclmnStoreProdProductName.setWidth(100);
		tblclmnStoreProdProductName.setText("Product Name");
		
		TableViewerColumn tableViewerColumn_38 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdDescription = tableViewerColumn_38.getColumn();
		tblclmnStoreProdDescription.setWidth(100);
		tblclmnStoreProdDescription.setText("Description");
		
		TableViewerColumn tableViewerColumn_40 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdAvgCost = tableViewerColumn_40.getColumn();
		tblclmnStoreProdAvgCost.setWidth(100);
		tblclmnStoreProdAvgCost.setText("Avg Cost");
		
		TableViewerColumn tableViewerColumn_39 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdManufacturer = tableViewerColumn_39.getColumn();
		tblclmnStoreProdManufacturer.setWidth(100);
		tblclmnStoreProdManufacturer.setText("Manufacturer");
		
		TableViewerColumn tableViewerColumn_41 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdCommRate = tableViewerColumn_41.getColumn();
		tblclmnStoreProdCommRate.setWidth(110);
		tblclmnStoreProdCommRate.setText("Commission Rate");
		
		TableViewerColumn tableViewerColumn_45 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdCategoryId = tableViewerColumn_45.getColumn();
		tblclmnStoreProdCategoryId.setWidth(100);
		tblclmnStoreProdCategoryId.setText("Category ID");
		
		TableViewerColumn tableViewerColumn_46 = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		TableColumn tblclmnStoreProdVendorId = tableViewerColumn_46.getColumn();
		tblclmnStoreProdVendorId.setWidth(100);
		tblclmnStoreProdVendorId.setText("Vendor ID");
		
		TableViewerColumn storeProdQty = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		storeProdQty.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				StoreProduct storeProd = (StoreProduct) element;
				return Integer.toString(storeProd.getQuantityOnHand());
			}
		});
		TableColumn tblclmnStoreProdQtyOnHand = storeProdQty.getColumn();
		tblclmnStoreProdQtyOnHand.setWidth(100);
		tblclmnStoreProdQtyOnHand.setText("QTY on Hand");
		
		TableViewerColumn storeProdShelf = new TableViewerColumn(StoreProdTableViewer, SWT.NONE);
		storeProdShelf.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				StoreProduct storeProd = (StoreProduct) element;
				return storeProd.getShelfLocation();
			}
		});
		TableColumn tblclmnStoreProdShelfLocation = storeProdShelf.getColumn();
		tblclmnStoreProdShelfLocation.setWidth(100);
		tblclmnStoreProdShelfLocation.setText("Shelf Location");
		scrolledComposite_3.setContent(storeProdStoreTable);
		scrolledComposite_3.setMinSize(storeProdStoreTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite composite_9 = new Composite(grpProductsInSelected, SWT.NONE);
		composite_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		composite_9.setLayout(new GridLayout(2, false));
		
		Label lblSearchBy_1 = new Label(composite_9, SWT.NONE);
		lblSearchBy_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearchBy_1.setText("Search By:");
		
		Combo storeProdColumnCombo = new Combo(composite_9, SWT.READ_ONLY);
		GridData gd_storeProdColumnCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_storeProdColumnCombo.widthHint = 150;
		storeProdColumnCombo.setLayoutData(gd_storeProdColumnCombo);
		
		Label lblNewLabel_8 = new Label(composite_9, SWT.NONE);
		lblNewLabel_8.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_8.setText("Search:");
		
		storeProdSearchBox = new Text(composite_9, SWT.BORDER);
		storeProdSearchBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		//********** Store Product Table Viewer **********
		
		StoreProdTableViewer.setContentProvider(new ArrayContentProvider());
		StoreProdTableViewer.setInput(BusinessObjectDAO.getInstance().searchForAll("StoreProduct"));
				
		Group grpStores = new Group(composite_2, SWT.NONE);
		grpStores.setLayout(new GridLayout(1, false));
		GridData gd_grpStores = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_grpStores.widthHint = 400;
		grpStores.setLayoutData(gd_grpStores);
		grpStores.setText("Stores");
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(grpStores, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_scrolledComposite_1.widthHint = 415;
		scrolledComposite_1.setLayoutData(gd_scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		final TableViewer StoreTableViewer = new TableViewer(scrolledComposite_1, SWT.BORDER | SWT.FULL_SELECTION);
		storeTable = StoreTableViewer.getTable();
		storeTable.setHeaderVisible(true);
		storeTable.setLinesVisible(true);
		
		TableViewerColumn storeID = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeID.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getId();
			}
		});
		TableColumn tblclmnStoreId = storeID.getColumn();
		tblclmnStoreId.setWidth(100);
		tblclmnStoreId.setText("Store ID");
		
		TableViewerColumn storeLocation = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeLocation.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getLocation();
			}
		});
		TableColumn tblclmnLocation = storeLocation.getColumn();
		tblclmnLocation.setWidth(100);
		tblclmnLocation.setText("Location");
		
		TableViewerColumn storeManager = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeManager.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getManagerId();
			}
		});
		TableColumn tblclmnManager = storeManager.getColumn();
		tblclmnManager.setWidth(120);
		tblclmnManager.setText("Manager");
		
		TableViewerColumn storePhone = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storePhone.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getPhone();
			}
		});
		TableColumn tblclmnPhone_1 = storePhone.getColumn();
		tblclmnPhone_1.setWidth(100);
		tblclmnPhone_1.setText("Phone Number");
		
		TableViewerColumn storeAddress = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeAddress.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getAddress();
			}
		});
		TableColumn tblclmnAddress = storeAddress.getColumn();
		tblclmnAddress.setWidth(120);
		tblclmnAddress.setText("Address");
		
		TableViewerColumn storeCity = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeCity.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getCity();
			}
		});
		TableColumn tblclmnCity = storeCity.getColumn();
		tblclmnCity.setWidth(100);
		tblclmnCity.setText("City");
		
		TableViewerColumn storeState = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeState.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getState();
			}
		});
		TableColumn tblclmnState = storeState.getColumn();
		tblclmnState.setWidth(100);
		tblclmnState.setText("State");
		
		TableViewerColumn storeZip = new TableViewerColumn(StoreTableViewer, SWT.NONE);
		storeZip.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) 
			{
				Store store = (Store) element;
				return store.getZip();
			}
		});
		TableColumn tblclmnZipCode = storeZip.getColumn();
		tblclmnZipCode.setWidth(100);
		tblclmnZipCode.setText("Zip Code");
		scrolledComposite_1.setContent(storeTable);
		scrolledComposite_1.setMinSize(storeTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnAddStore = new Button(composite_2, SWT.NONE);
		GridData gd_btnNewButton_11 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_11.widthHint = 200;
		btnAddStore.setLayoutData(gd_btnNewButton_11);
		btnAddStore.setText("+ Add New Store");
		
		Button btnEditSelectedStore = new Button(composite_2, SWT.NONE);
		GridData gd_btnEditSelectedStore = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditSelectedStore.widthHint = 200;
		btnEditSelectedStore.setLayoutData(gd_btnEditSelectedStore);
		btnEditSelectedStore.setText("Edit Selected Store");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		Button btnStoreExit = new Button(composite_2, SWT.NONE);
		GridData gd_btnStoreExit = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnStoreExit.widthHint = 150;
		btnStoreExit.setLayoutData(gd_btnStoreExit);
		btnStoreExit.setText("Exit");
		
		//========== Store Table Viewer ==========
		
		StoreTableViewer.setContentProvider(new ArrayContentProvider());
		StoreTableViewer.setInput(BusinessObjectDAO.getInstance().searchForAll("Store"));
		
		// ***** Store Buttons *****
		
		btnAddStore.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
//				DetailScreenStore storeScreen = new DetailScreenStore(shell, 0, null);
//				storeScreen.open();
			}
		});
		
		btnEditSelectedStore.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				IStructuredSelection selection = (IStructuredSelection) StoreTableViewer.getSelection();
				Store store = (Store) selection.getFirstElement();
				
//				DetailScreenStore storeScreen = new DetailScreenStore(shell, 0, store);
//				storeScreen.open();
			}
		});
		
		btnStoreExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				shlMyStuff.close();
			}
		});
		
//========== Conceptual Products Tab ==========
		
		CTabItem conProdTab = new CTabItem(tabFolder, SWT.NONE);
		conProdTab.setImage(SWTResourceManager.getImage(GeneralInfoWindow.class, "/images/conProd.png"));
		conProdTab.setText("Products");
		
		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		conProdTab.setControl(composite_4);
		composite_4.setLayout(new GridLayout(6, false));
		
		Composite composite_6 = new Composite(composite_4, SWT.NONE);
		composite_6.setLayout(new GridLayout(4, false));
		composite_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 5, 1));
		
		Label lblNewLabel_5 = new Label(composite_6, SWT.NONE);
		lblNewLabel_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_5.setText("Search By:");
		
		Combo conProdColumnCombo = new Combo(composite_6, SWT.READ_ONLY);
		GridData gd_conProdColumnCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_conProdColumnCombo.widthHint = 150;
		conProdColumnCombo.setLayoutData(gd_conProdColumnCombo);
		
		Label lblNewLabel_4 = new Label(composite_6, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("Search:");
		
		conProdSearchBox = new Text(composite_6, SWT.BORDER);
		GridData gd_conProdSearchBox = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_conProdSearchBox.widthHint = 150;
		conProdSearchBox.setLayoutData(gd_conProdSearchBox);
		
		Group grpStoresThatCarry = new Group(composite_4, SWT.NONE);
		GridData gd_grpStoresThatCarry = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd_grpStoresThatCarry.widthHint = 475;
		grpStoresThatCarry.setLayoutData(gd_grpStoresThatCarry);
		grpStoresThatCarry.setLayout(new GridLayout(2, true));
		grpStoresThatCarry.setText("Physical Products");
		
		Button btnAddNew = new Button(grpStoresThatCarry, SWT.NONE);
		btnAddNew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddNew.setText("+ Add New Physical Product");
		
		Button btnEditSelectedPhysical = new Button(grpStoresThatCarry, SWT.NONE);
		btnEditSelectedPhysical.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEditSelectedPhysical.setText("Edit Selected Physical Product");
		
		TableViewer tableViewer_2 = new TableViewer(grpStoresThatCarry, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer_2.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TableViewerColumn tableViewerColumn_34 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnStatus_1 = tableViewerColumn_34.getColumn();
		tblclmnStatus_1.setWidth(100);
		tblclmnStatus_1.setText("Status");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnProductId_2 = tableViewerColumn_2.getColumn();
		tblclmnProductId_2.setWidth(100);
		tblclmnProductId_2.setText("Product ID");
		
		TableViewerColumn tableViewerColumn_30 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnSerialNumber = tableViewerColumn_30.getColumn();
		tblclmnSerialNumber.setWidth(100);
		tblclmnSerialNumber.setText(" Serial Number");
		
		TableViewerColumn tableViewerColumn_36 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnNewused = tableViewerColumn_36.getColumn();
		tblclmnNewused.setWidth(100);
		tblclmnNewused.setText("New/Used");
		
		TableViewerColumn tableViewerColumn_31 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnShelfLocation_1 = tableViewerColumn_31.getColumn();
		tblclmnShelfLocation_1.setWidth(100);
		tblclmnShelfLocation_1.setText("Shelf Location");
		
		TableViewerColumn tableViewerColumn_35 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnFullCommissionRate = tableViewerColumn_35.getColumn();
		tblclmnFullCommissionRate.setWidth(100);
		tblclmnFullCommissionRate.setText("Full Commission Rate");
		
		TableViewerColumn tableViewerColumn_33 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnCost_1 = tableViewerColumn_33.getColumn();
		tblclmnCost_1.setWidth(100);
		tblclmnCost_1.setText("Cost");
		
		TableViewerColumn tableViewerColumn_32 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnDatePurchased_1 = tableViewerColumn_32.getColumn();
		tblclmnDatePurchased_1.setWidth(100);
		tblclmnDatePurchased_1.setText("Date Purchased");
		
		Composite composite_3 = new Composite(grpStoresThatCarry, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		composite_3.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel_7 = new Label(composite_3, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_7.setText("Search By:");
		
		Combo storeProdColumCombo2 = new Combo(composite_3, SWT.READ_ONLY);
		GridData gd_storeProdColumCombo2 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_storeProdColumCombo2.widthHint = 150;
		storeProdColumCombo2.setLayoutData(gd_storeProdColumCombo2);
		
		Label lblNewLabel_6 = new Label(composite_3, SWT.NONE);
		lblNewLabel_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_6.setText("Search:");
		
		Text storeProdSearchBox2 = new Text(composite_3, SWT.BORDER);
		storeProdSearchBox2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				
		Group grpProducts = new Group(composite_4, SWT.NONE);
		GridData gd_grpProducts = new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1);
		gd_grpProducts.widthHint = 533;
		grpProducts.setLayoutData(gd_grpProducts);
		grpProducts.setText("Conceptual Products");
		grpProducts.setLayout(new GridLayout(1, false));
		
		ScrolledComposite scrolledComposite_2 = new ScrolledComposite(grpProducts, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite_2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_scrolledComposite_2.widthHint = 391;
		scrolledComposite_2.setLayoutData(gd_scrolledComposite_2);
		scrolledComposite_2.setExpandHorizontal(true);
		scrolledComposite_2.setExpandVertical(true);
		
		TableViewer ConProdTableViewer = new TableViewer(scrolledComposite_2, SWT.BORDER | SWT.FULL_SELECTION);
		conProdTable = ConProdTableViewer.getTable();
		conProdTable.setLinesVisible(true);
		conProdTable.setHeaderVisible(true);
		
		TableViewerColumn prodID = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdID = prodID.getColumn();
		tblclmnConProdID.setWidth(100);
		tblclmnConProdID.setText("Product ID");
		
		TableViewerColumn tableViewerColumn_43 = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdSku = tableViewerColumn_43.getColumn();
		tblclmnConProdSku.setWidth(100);
		tblclmnConProdSku.setText("SKU");
		
		TableViewerColumn prodName = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdName = prodName.getColumn();
		tblclmnConProdName.setWidth(100);
		tblclmnConProdName.setText("Product Name");
		
		TableViewerColumn prodDesc = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdDescription = prodDesc.getColumn();
		tblclmnConProdDescription.setWidth(100);
		tblclmnConProdDescription.setText("Description");
		
		TableViewerColumn prodAvgCost = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdAvgCost = prodAvgCost.getColumn();
		tblclmnConProdAvgCost.setWidth(100);
		tblclmnConProdAvgCost.setText("Avg. Cost");
		
		TableViewerColumn prodManu = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdManufacturer = prodManu.getColumn();
		tblclmnConProdManufacturer.setWidth(100);
		tblclmnConProdManufacturer.setText("Manufacturer");
		
		TableViewerColumn tableViewerColumn_47 = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdCommRate = tableViewerColumn_47.getColumn();
		tblclmnConProdCommRate.setWidth(110);
		tblclmnConProdCommRate.setText("Commission Rate");
		
		TableViewerColumn prodCategory = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdCategoryId = prodCategory.getColumn();
		tblclmnConProdCategoryId.setWidth(100);
		tblclmnConProdCategoryId.setText("Category ID");
		
		TableViewerColumn prodVendor = new TableViewerColumn(ConProdTableViewer, SWT.NONE);
		TableColumn tblclmnConProdVendorId = prodVendor.getColumn();
		tblclmnConProdVendorId.setWidth(100);
		tblclmnConProdVendorId.setText("Vendor ID");
		scrolledComposite_2.setContent(conProdTable);
		scrolledComposite_2.setMinSize(conProdTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite composite_5 = new Composite(composite_4, SWT.NONE);
		
		Button btnAddNewConProduct = new Button(composite_5, SWT.NONE);
		btnAddNewConProduct.setBounds(0, 0, 200, 25);
		btnAddNewConProduct.setText("+ Add New Conceptual Product");
		
		Button btnEditSelectedConProduct = new Button(composite_4, SWT.NONE);
		GridData gd_btnEditSelectedConProduct = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditSelectedConProduct.widthHint = 200;
		btnEditSelectedConProduct.setLayoutData(gd_btnEditSelectedConProduct);
		btnEditSelectedConProduct.setText("Edit Selected Conceptual Product");
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		
		Button btnConProdExit = new Button(composite_4, SWT.NONE);
		GridData gd_btnConProdExit = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnConProdExit.widthHint = 150;
		btnConProdExit.setLayoutData(gd_btnConProdExit);
		btnConProdExit.setText("Exit");
		
		//********** Conceptual Product Table Viewer **********
		
//		ConProdTableViewer.setContentProvider(new ArrayContentProvider());
//		ConProdTableViewer.setInput(BusinessObjectDAO.getInstance().searchForAll("ConceptualProduct"));
		
		//********** Conceptual Product Buttons **********
		
		btnAddNewConProduct.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
//				DetailScreenProduct productScreen = new DetailScreenProduct();
//				productScreen.open();
			}
		});
		
		btnEditSelectedConProduct.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
//				DetailScreenProduct productScreen = new DetailScreenProduct();
//				productScreen.open();
			}
		});
		
		btnConProdExit.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				System.exit(0);
			}
		});
		
		// ***** Product Buttons *****
		
//========== Tab Selector ==========
		
		tabFolder.setSelection(tab);
		
		CTabItem tbtmRentalPool = new CTabItem(tabFolder, SWT.NONE);
		tbtmRentalPool.setImage(SWTResourceManager.getImage(GeneralInfoWindow.class, "/images/rent.png"));
		tbtmRentalPool.setText("Rentals");
		
		Composite composite_11 = new Composite(tabFolder, SWT.NONE);
		tbtmRentalPool.setControl(composite_11);
		composite_11.setLayout(new GridLayout(4, false));
		
		Composite composite_12 = new Composite(composite_11, SWT.NONE);
		composite_12.setLayout(new GridLayout(4, false));
		composite_12.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		Label lblSearchBy_4 = new Label(composite_12, SWT.NONE);
		lblSearchBy_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearchBy_4.setText("Search By:");
		
		Combo conRentCombo = new Combo(composite_12, SWT.READ_ONLY);
		GridData gd_conRentCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_conRentCombo.widthHint = 150;
		conRentCombo.setLayoutData(gd_conRentCombo);
		
		Label lblSearch_3 = new Label(composite_12, SWT.NONE);
		lblSearch_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearch_3.setText("Search:");
		
		conRentSearch = new Text(composite_12, SWT.BORDER);
		GridData gd_conRentSearch = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_conRentSearch.widthHint = 150;
		conRentSearch.setLayoutData(gd_conRentSearch);
		
		Group grpPhysicalRentals = new Group(composite_11, SWT.NONE);
		grpPhysicalRentals.setText("Physical Rentals");
		grpPhysicalRentals.setLayout(new GridLayout(2, true));
		GridData gd_grpPhysicalRentals = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 2);
		gd_grpPhysicalRentals.widthHint = 390;
		grpPhysicalRentals.setLayoutData(gd_grpPhysicalRentals);
		
		Button btnAddNew_1 = new Button(grpPhysicalRentals, SWT.NONE);
		btnAddNew_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddNew_1.setText("+ Add New Physical Rental");
		
		Button button_2 = new Button(grpPhysicalRentals, SWT.NONE);
		button_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		button_2.setText("Edit Selected Physical Rental");
		
		ScrolledComposite scrolledComposite_6 = new ScrolledComposite(grpPhysicalRentals, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		scrolledComposite_6.setExpandHorizontal(true);
		scrolledComposite_6.setExpandVertical(true);
		
		TableViewer tableViewer_1 = new TableViewer(scrolledComposite_6, SWT.BORDER | SWT.FULL_SELECTION);
		physRentTable = tableViewer_1.getTable();
		physRentTable.setLinesVisible(true);
		physRentTable.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn_27 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnStatus = tableViewerColumn_27.getColumn();
		tblclmnStatus.setWidth(100);
		tblclmnStatus.setText("Status");
		
		TableViewerColumn tableViewerColumn_22 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnRentalId = tableViewerColumn_22.getColumn();
		tblclmnRentalId.setWidth(100);
		tblclmnRentalId.setText("Rental ID");
		
		TableViewerColumn tableViewerColumn_23 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_23.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Serial Number");
		
		TableViewerColumn tableViewerColumn_29 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnTimesRented = tableViewerColumn_29.getColumn();
		tblclmnTimesRented.setWidth(100);
		tblclmnTimesRented.setText("Times Rented");
		
		TableViewerColumn tableViewerColumn_24 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnShelfLocale = tableViewerColumn_24.getColumn();
		tblclmnShelfLocale.setWidth(100);
		tblclmnShelfLocale.setText("Shelf Location");
		
		TableViewerColumn tableViewerColumn_28 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnCommissionRate_1 = tableViewerColumn_28.getColumn();
		tblclmnCommissionRate_1.setWidth(110);
		tblclmnCommissionRate_1.setText("Full Commission Rate");
		
		TableViewerColumn tableViewerColumn_26 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnCost = tableViewerColumn_26.getColumn();
		tblclmnCost.setWidth(100);
		tblclmnCost.setText("Cost");
		
		TableViewerColumn tableViewerColumn_25 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnDatePurchased = tableViewerColumn_25.getColumn();
		tblclmnDatePurchased.setWidth(100);
		tblclmnDatePurchased.setText("Date Purchased");
		scrolledComposite_6.setContent(physRentTable);
		scrolledComposite_6.setMinSize(physRentTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite composite_14 = new Composite(grpPhysicalRentals, SWT.NONE);
		composite_14.setLayout(new GridLayout(2, false));
		composite_14.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 2, 1));
		
		Label label = new Label(composite_14, SWT.NONE);
		label.setText("Search By:");
		
		Combo physRentCombo = new Combo(composite_14, SWT.READ_ONLY);
		GridData gd_physRentCombo = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_physRentCombo.widthHint = 150;
		physRentCombo.setLayoutData(gd_physRentCombo);
		
		Label label_1 = new Label(composite_14, SWT.NONE);
		label_1.setText("Search:");
		
		physRentSearch = new Text(composite_14, SWT.BORDER);
		physRentSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite_14, SWT.NONE);
		
		Button button = new Button(composite_14, SWT.NONE);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		button.setText(" Mark Item for Sale");
		
		Group grpConceptualRentals = new Group(composite_11, SWT.NONE);
		grpConceptualRentals.setText("Conceptual Rentals");
		grpConceptualRentals.setLayout(new GridLayout(1, false));
		grpConceptualRentals.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		ScrolledComposite scrolledComposite_7 = new ScrolledComposite(grpConceptualRentals, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite_7.setExpandHorizontal(true);
		scrolledComposite_7.setExpandVertical(true);
		
		TableViewer tableViewer = new TableViewer(scrolledComposite_7, SWT.BORDER | SWT.FULL_SELECTION);
		conRentTable = tableViewer.getTable();
		conRentTable.setLinesVisible(true);
		conRentTable.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentID = tableViewerColumn_13.getColumn();
		tblclmnConRentID.setWidth(100);
		tblclmnConRentID.setText("Rental ID");
		
		TableViewerColumn tableViewerColumn_44 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentConProdId = tableViewerColumn_44.getColumn();
		tblclmnConRentConProdId.setWidth(100);
		tblclmnConRentConProdId.setText("Product ID");
		
		TableViewerColumn tableViewerColumn_19 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentSku = tableViewerColumn_19.getColumn();
		tblclmnConRentSku.setWidth(100);
		tblclmnConRentSku.setText("SKU");
		
		TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentProdName = tableViewerColumn_14.getColumn();
		tblclmnConRentProdName.setWidth(100);
		tblclmnConRentProdName.setText("Product Name");
		
		TableViewerColumn tableViewerColumn_15 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentDescription = tableViewerColumn_15.getColumn();
		tblclmnConRentDescription.setWidth(100);
		tblclmnConRentDescription.setText("Description");
		
		TableViewerColumn tableViewerColumn_17 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentAvgCost = tableViewerColumn_17.getColumn();
		tblclmnConRentAvgCost.setWidth(100);
		tblclmnConRentAvgCost.setText("Avg  Cost");
		
		TableViewerColumn tableViewerColumn_16 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentManufacturer = tableViewerColumn_16.getColumn();
		tblclmnConRentManufacturer.setWidth(100);
		tblclmnConRentManufacturer.setText("Manufacturer");
		
		TableViewerColumn tableViewerColumn_18 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentCommRate = tableViewerColumn_18.getColumn();
		tblclmnConRentCommRate.setWidth(100);
		tblclmnConRentCommRate.setText("Commission Rate");
		
		TableViewerColumn tableViewerColumn_48 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentCategoryId = tableViewerColumn_48.getColumn();
		tblclmnConRentCategoryId.setWidth(100);
		tblclmnConRentCategoryId.setText("Category ID");
		
		TableViewerColumn tableViewerColumn_49 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentVendorId = tableViewerColumn_49.getColumn();
		tblclmnConRentVendorId.setWidth(100);
		tblclmnConRentVendorId.setText("Vendor ID");
		
		TableViewerColumn tableViewerColumn_20 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentPricePerDay = tableViewerColumn_20.getColumn();
		tblclmnConRentPricePerDay.setWidth(100);
		tblclmnConRentPricePerDay.setText("Price Per Day");
		
		TableViewerColumn tableViewerColumn_21 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConRentRepCost = tableViewerColumn_21.getColumn();
		tblclmnConRentRepCost.setWidth(100);
		tblclmnConRentRepCost.setText("Replacement Price");
		scrolledComposite_7.setContent(conRentTable);
		scrolledComposite_7.setMinSize(conRentTable.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnAddNewConRent = new Button(composite_11, SWT.NONE);
		GridData gd_btnAddNewConRent = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddNewConRent.widthHint = 200;
		btnAddNewConRent.setLayoutData(gd_btnAddNewConRent);
		btnAddNewConRent.setText("+ Add New Conceptual Rental");
		
		Button btnEditConRent = new Button(composite_11, SWT.NONE);
		GridData gd_btnEditConRent = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnEditConRent.widthHint = 200;
		btnEditConRent.setLayoutData(gd_btnEditConRent);
		btnEditConRent.setText("Edit Selected Conceptual Rental");
		new Label(composite_11, SWT.NONE);
		
		Button btnExit = new Button(composite_11, SWT.NONE);
		GridData gd_btnConRentExit = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnConRentExit.widthHint = 150;
		btnExit.setLayoutData(gd_btnConRentExit);
		btnExit.setText("Exit");
		
		switch(tab)
		{
			case 1:		tabFolder.setSelection(1);
						break;
			case 2:		tabFolder.setSelection(2);
						break;
			case 3:		tabFolder.setSelection(3);
						break;
			case 4:		tabFolder.setSelection(4);
						break;
			default:	tabFolder.setSelection(0);
		}
		
	}//createContents
}//GeneralInfoWindow

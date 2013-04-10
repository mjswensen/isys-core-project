package edu.byu.isys413.data.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Computer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.SearchCriteria;

/**
 * This is the main portal, and allows employees to navigate to any function in the system.
 *
 */
public class MainView {

	protected Shell shlMystuffMain;
	protected Display display;
	
	private static final int CUSTOMER_TAB = 0;
	private static final int EMPLOYEE_TAB = 1;
	private static final int STORE_TAB = 2;
	private static final int PRODUCTS_TAB = 3;
	private static final int RENTALS_TAB = 4;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainView window = new MainView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlMystuffMain.open();
		shlMystuffMain.layout();
		while (!shlMystuffMain.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMystuffMain = new Shell();
		shlMystuffMain.setImage(SWTResourceManager.getImage(MainView.class, "/images/logo_camera.png"));
		shlMystuffMain.setMinimumSize(new Point(900, 600));
		shlMystuffMain.setSize(450, 300);
		shlMystuffMain.setText("MyStuff | Main Window");
		shlMystuffMain.setLayout(new GridLayout(1, false));
		
		displayLoginPrompt();
		
		Menu menu = new Menu(shlMystuffMain, SWT.BAR);
		shlMystuffMain.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("File");
		
		Menu menu_3 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_3);
		
		MenuItem mntmExit = new MenuItem(menu_3, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				System.exit(0);
				
			}
		});
		mntmExit.setImage(SWTResourceManager.getImage(MainView.class, "/images/exit.png"));
		mntmExit.setText("Exit");
		
		MenuItem mntmManagement = new MenuItem(menu, SWT.CASCADE);
		mntmManagement.setText("Manage");
		
		Menu menu_2 = new Menu(mntmManagement);
		mntmManagement.setMenu(menu_2);
		
		MenuItem mntmManageEntities = new MenuItem(menu_2, SWT.NONE);
		mntmManageEntities.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				GeneralInfoWindow window = new GeneralInfoWindow(shlMystuffMain, SWT.APPLICATION_MODAL, 0);
				try {
					window.open();
				} catch (DataException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		mntmManageEntities.setImage(SWTResourceManager.getImage(MainView.class, "/images/customers.png"));
		mntmManageEntities.setText("Customers...");
		
		MenuItem mntmEmployees = new MenuItem(menu_2, SWT.NONE);
		mntmEmployees.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				GeneralInfoWindow window = new GeneralInfoWindow(shlMystuffMain, SWT.APPLICATION_MODAL, 1);
				try {
					window.open();
				} catch (DataException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		mntmEmployees.setImage(SWTResourceManager.getImage(MainView.class, "/images/employee.png"));
		mntmEmployees.setText("Employees...");
		
		MenuItem mntmStore = new MenuItem(menu_2, SWT.NONE);
		mntmStore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				GeneralInfoWindow window = new GeneralInfoWindow(shlMystuffMain, SWT.APPLICATION_MODAL, 2);
				try {
					window.open();
				} catch (DataException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		mntmStore.setImage(SWTResourceManager.getImage(MainView.class, "/images/store.png"));
		mntmStore.setText("Store...");
		
		MenuItem mntmProducts = new MenuItem(menu_2, SWT.NONE);
		mntmProducts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				GeneralInfoWindow window = new GeneralInfoWindow(shlMystuffMain, SWT.APPLICATION_MODAL, 3);
				try {
					window.open();
				} catch (DataException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		mntmProducts.setImage(SWTResourceManager.getImage(MainView.class, "/images/conProd.png"));
		mntmProducts.setText("Products...");
		
		MenuItem mntmRentals = new MenuItem(menu_2, SWT.NONE);
		mntmRentals.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				GeneralInfoWindow window = new GeneralInfoWindow(shlMystuffMain, SWT.APPLICATION_MODAL, 4);
				try {
					window.open();
				} catch (DataException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		mntmRentals.setImage(SWTResourceManager.getImage(MainView.class, "/images/rent.png"));
		mntmRentals.setText("Rentals...");
		
		MenuItem mntmSales = new MenuItem(menu, SWT.CASCADE);
		mntmSales.setText("Sales");
		
		Menu menu_1 = new Menu(mntmSales);
		mntmSales.setMenu(menu_1);
		
		MenuItem mntmProcessTransaction = new MenuItem(menu_1, SWT.NONE);
		mntmProcessTransaction.setImage(SWTResourceManager.getImage(MainView.class, "/images/cash.png"));
		mntmProcessTransaction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TransactionView transactionView = new TransactionView(display);
				transactionView.open();
				transactionView.layout();
			}
		});
		mntmProcessTransaction.setText("Process Transaction...");
		
		MenuItem mntmReturnRental = new MenuItem(menu_1, SWT.NONE);
		mntmReturnRental.setImage(SWTResourceManager.getImage(MainView.class, "/images/returnrent.png"));
		mntmReturnRental.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RentalReturnView rentalReturnView = new RentalReturnView(display);
				rentalReturnView.open();
				rentalReturnView.layout();
			}
		});
		mntmReturnRental.setText("Return Rental...");
		
		shlMystuffMain.setMaximized(true);
		
		CLabel label = new CLabel(shlMystuffMain, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setTopMargin(0);
		label.setBottomMargin(0);
		label.setRightMargin(0);
		label.setLeftMargin(0);
		label.setImage(SWTResourceManager.getImage(MainView.class, "/images/meyer_1.png"));
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		label.setText("");
		
		// Get store information.
		try {
			Computer comp = BusinessObjectDAO.getInstance().searchForBO("Computer", new SearchCriteria("mac", "00:26:bb:17:56:ec"));
			AppData.getInstance().setStore(comp.getStore());
		} catch (DataException e1) {
			System.out.println("Unable to get store from current computer.");
			// TODO: extra: add drop-down to login window with stores.
		}

	}

	/**
	 *Displays LoginView
	 */
	private void displayLoginPrompt() {
		LoginView loginView = new LoginView(display);
		loginView.open();
		loginView.layout();
	}
}

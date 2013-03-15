package edu.byu.isys413.data.views;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.data.models.BusinessObjectDAO;
import edu.byu.isys413.data.models.Computer;
import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.SearchCriteria;

public class MainView {

	protected Shell shell;
	protected Display display;

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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmSales = new MenuItem(menu, SWT.CASCADE);
		mntmSales.setText("Sales");
		
		Menu menu_1 = new Menu(mntmSales);
		mntmSales.setMenu(menu_1);
		
		MenuItem mntmProcessTransaction = new MenuItem(menu_1, SWT.NONE);
		mntmProcessTransaction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SaleView saleView = new SaleView(display);
				saleView.open();
				saleView.layout();
			}
		});
		mntmProcessTransaction.setText("Process Transaction...");
		
		MenuItem mntmManagement = new MenuItem(menu, SWT.CASCADE);
		mntmManagement.setText("Management");
		
		Menu menu_2 = new Menu(mntmManagement);
		mntmManagement.setMenu(menu_2);
		
		MenuItem mntmManageEntities = new MenuItem(menu_2, SWT.NONE);
		mntmManageEntities.setText("Manage Entities...");
		
		shell.setMaximized(true);
		
		// Get store information.
		try {
			Computer comp = BusinessObjectDAO.getInstance().searchForBO("Computer", new SearchCriteria("mac", "00:26:bb:17:56:ec"));
			AppData.getInstance().setStore(comp.getStore());
		} catch (DataException e1) {
			System.out.println("Unable to get store from current computer.");
			// TODO: extra: add drop-down to login window with stores.
		}
		
		displayLoginPrompt();

	}

	private void displayLoginPrompt() {
		LoginView loginView = new LoginView(display);
		loginView.open();
		loginView.layout();
	}
}

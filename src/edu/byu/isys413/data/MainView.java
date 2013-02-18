package edu.byu.isys413.data;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;

public class MainView {

	protected Shell shell;
	protected Display display;
	private boolean isLoggedIn = false;

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
		mntmProcessTransaction.setText("Process Transaction...");
		
		MenuItem mntmManagement = new MenuItem(menu, SWT.CASCADE);
		mntmManagement.setText("Management");
		
		Menu menu_2 = new Menu(mntmManagement);
		mntmManagement.setMenu(menu_2);
		
		MenuItem mntmManageEntities = new MenuItem(menu_2, SWT.NONE);
		mntmManageEntities.setText("Manage Entities...");
		
		displayLoginPrompt();

	}

	private void displayLoginPrompt() {
		LoginView loginView = new LoginView(display);
		loginView.open();
		loginView.layout();
		loginView.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				isLoggedIn = true;
			}
		});
	}

}

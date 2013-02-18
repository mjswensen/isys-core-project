package edu.byu.isys413.data;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

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

public class LoginView extends Shell {
	private Text txtNetid;
	private Text txtPassword;

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
		
		Button btnLogIn = new Button(this, SWT.NONE);
		btnLogIn.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseUp(MouseEvent e) {
				String netId = txtNetid.getText();
				String passwd = txtPassword.getText();
				try {
		            @SuppressWarnings("rawtypes")
					Hashtable env = new Hashtable();
		            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		            env.put(Context.PROVIDER_URL, "ldaps://ldap.byu.edu/");
		            env.put(Context.SECURITY_AUTHENTICATION, "simple");
		            env.put(Context.SECURITY_PRINCIPAL, "uid=" + netId + ", ou=People, o=byu.edu");
		            env.put(Context.SECURITY_CREDENTIALS, passwd);
		            @SuppressWarnings("unused")
					DirContext ctx = new InitialDirContext(env);
		            closeLoginPrompt();
		        } catch (NamingException e1) {
		            // TODO: extra: display some feedback to the user.
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
		setSize(325, 135);

	}
	
	private void closeLoginPrompt() {
		this.dispose();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

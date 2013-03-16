package edu.byu.isys413.data.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.byu.isys413.data.models.*;
import edu.byu.isys413.data.util.BatchEmail;
import edu.byu.isys413.data.web.*;

public class CreateAndVerifyAccount implements Action {

	/** No-arg constructor per Dr. Albrecht's instruction in Action.java */
	public CreateAndVerifyAccount() {}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getParameter("vc") != null && !request.getParameter("vc").equals("")) {
			// The user is arriving here via the emailed verification link.
			
			String vc = request.getParameter("vc");
			Customer cust = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("validationcode", vc));
			if(cust == null) {
				// Incorrect verification code provided
				request.setAttribute("message", "Incorrect verification code provided.");
				request.setAttribute("messageType", "error");
				return "message.jsp";
			} else {
				cust.setValid(true);
				cust.save();
				request.getSession().setAttribute("cust", cust);
				return "products.jsp";
			}
		} else {
			// The user is arriving here to create the account initially.
			
			// Make sure they provided all the required fields.
			for(String name : new String[] {"firstname", "lastname", "email", "password", "address1", "city", "state", "zip", "creditcard"}) {
				if(request.getParameter(name) == null || request.getParameter(name).equals("")) {
					request.setAttribute("message", "Please provide a value for " + name + ".");
					request.setAttribute("messageType", "error");
					return "message.jsp";
				}
			}
			
			// Build the address string for the customer object. In reality, these would definitely be saved in different DB fields.
			StringBuilder sb = new StringBuilder();
			sb.append(request.getParameter("address1"));
			sb.append("\n");
			if(!request.getParameter("address2").equals("")) {
				sb.append(request.getParameter("address2"));
				sb.append("\n");
			}
			sb.append(request.getParameter("city"));
			sb.append(", ");
			sb.append(request.getParameter("state"));
			sb.append(request.getParameter(" "));
			sb.append(request.getParameter("zip"));
			
			// Create Customer and Membership Objects
			Customer c = BusinessObjectDAO.getInstance().create("Customer");
			c.setFirstName(request.getParameter("firstname"));
			c.setLastName(request.getParameter("lastname"));
			c.setAddress(sb.toString());
			c.setEmail(request.getParameter("email"));
			
			Membership m = BusinessObjectDAO.getInstance().create("Membership");
			m.setCustomer(c);
			m.setCreditCard(request.getParameter("creditcard"));
			m.setStartDate(new Date());
			
			// Generate validation code and send email.
			String validationCode = GUID.generate();
			c.setValidationCode(validationCode);
			c.setValid(false);
			
			StringBuilder msg = new StringBuilder();
			msg.append("Dear ");
			msg.append(c.getFirstName());
			msg.append(", \n\n");
			msg.append("Thank you for signing up for our site. To verify your account, please visit the URL below:\n\n");
			msg.append("http://localhost:8080/MyStuffSprint/edu.byu.isys413.data.actions.CreateAndVerifyAccount.action?vc=");
			msg.append(validationCode);
			msg.append("\n\n");
			msg.append("Best,\n");
			msg.append("MyStuff");
			
			// Send email
			BatchEmail.send("smtp.mail.for.testing@gmail.com", "SMTP Mailer", c.getEmail(), "Verify Your Online Account", msg.toString());
			
			// Save Customer and Membership objects
			c.save();
			m.save();
			
			// Display message to user
			request.setAttribute("message", "Please check your email inbox for a link to verify your account.");
			request.setAttribute("messateType", "info");
			return "message.jsp";
		}
		
	}

}

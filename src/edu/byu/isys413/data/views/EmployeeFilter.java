package edu.byu.isys413.data.views;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.ibm.icu.text.SimpleDateFormat;

import edu.byu.isys413.data.models.DataException;
import edu.byu.isys413.data.models.Employee;

public class EmployeeFilter extends ViewerFilter {

	public static String[] options = {
		"---",// 0
		"Last Name contains",// 1
		"First Name contains",// 2
		"Username contains",// 3
		"Store contains",// 4
		"Hire Date is on or after",// 5
		"Hire Date is on or before",// 6
		"Phone contains",// 7
		"Salary is greater than or equal to",// 8
		"Salary is less than or equal to"// 9
	};
	
	private String filter;
	private String selectedOption;
	
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @param selectedOption the selectedOption to set
	 */
	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	/**
	 * Converts the filter text to a regex appropriate for "contains."
	 */
	private void makeFilterRegex() {
		filter = ".*" + filter + ".*";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Employee e = (Employee) element;
		if(selectedOption.equals(options[0])) {
			return true;
		} else if(selectedOption.equals(options[1])) {
			makeFilterRegex();
			return e.getLastName().matches(filter);
		} else if(selectedOption.equals(options[2])) {
			makeFilterRegex();
			return e.getFirstName().matches(filter);
		} else if(selectedOption.equals(options[3])) {
			makeFilterRegex();
			return e.getNetid().matches(filter);
		} else if(selectedOption.equals(options[4])) {
			makeFilterRegex();
			try {
				return e.getStore().getLocation().matches(filter);
			} catch(DataException e1) {
				return false;
			}
		} else if(selectedOption.equals(options[5])) {
			try {
				Date filterDate = new SimpleDateFormat("yyyy-MM-dd").parse(filter);
				return e.getHireDate().compareTo(filterDate) >= 0;
			} catch (ParseException e1) {
				return true;
			}
		} else if(selectedOption.equals(options[6])) {
			try {
				Date filterDate = new SimpleDateFormat("yyyy-MM-dd").parse(filter);
				return e.getHireDate().compareTo(filterDate) <= 0;
			} catch (ParseException e1) {
				return true;
			}
		} else if(selectedOption.equals(options[7])) {
			makeFilterRegex();
			return e.getPhone().matches(filter);
		} else if(selectedOption.equals(options[8])) {
			try {
				double filterSalary = Double.parseDouble(filter);
				return e.getSalary() >= filterSalary;
			} catch (Exception e1) {
				return true;
			}
		} else if(selectedOption.equals(options[9])) {
			try {
				double filterSalary = Double.parseDouble(filter);
				return e.getSalary() <= filterSalary;
			} catch (Exception e1) {
				return true;
			}
		} else {
			return true;
		}
	}

}

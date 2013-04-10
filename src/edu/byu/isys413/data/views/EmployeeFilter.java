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
		"Last Name contains",// 0
		"First Name contains",// 1
		"Username contains",// 2
		"Store contains",// 3
		"Hire Date is on or after",// 4
		"Hire Date is on or before",// 5
		"Phone contains",// 6
		"Salary is greater than or equal to",// 7
		"Salary is less than or equal to"// 8
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
			makeFilterRegex();
			return e.getLastName().toLowerCase().matches(filter.toLowerCase());
		} else if(selectedOption.equals(options[1])) {
			makeFilterRegex();
			return e.getFirstName().toLowerCase().matches(filter.toLowerCase());
		} else if(selectedOption.equals(options[2])) {
			makeFilterRegex();
			return e.getNetid().toLowerCase().matches(filter.toLowerCase());
		} else if(selectedOption.equals(options[3])) {
			makeFilterRegex();
			try {
				return e.getStore().getLocation().toLowerCase().matches(filter.toLowerCase());
			} catch(DataException e1) {
				return false;
			}
		} else if(selectedOption.equals(options[4])) {
			try {
				Date filterDate = new SimpleDateFormat("yyyy-MM-dd").parse(filter);
				return e.getHireDate().compareTo(filterDate) >= 0;
			} catch (ParseException e1) {
				return true;
			}
		} else if(selectedOption.equals(options[5])) {
			try {
				Date filterDate = new SimpleDateFormat("yyyy-MM-dd").parse(filter);
				return e.getHireDate().compareTo(filterDate) <= 0;
			} catch (ParseException e1) {
				return true;
			}
		} else if(selectedOption.equals(options[6])) {
			makeFilterRegex();
			return e.getPhone().toLowerCase().matches(filter.toLowerCase());
		} else if(selectedOption.equals(options[7])) {
			try {
				double filterSalary = Double.parseDouble(filter);
				return e.getSalary() >= filterSalary;
			} catch (Exception e1) {
				return true;
			}
		} else if(selectedOption.equals(options[8])) {
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

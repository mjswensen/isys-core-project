package edu.byu.isys413.data.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.byu.isys413.data.models.Employee;

public class EmployeeFilter extends ViewerFilter {

	private String filter;
	
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Employee e = (Employee) element;
		return e.getLastName().matches(filter);
	}

}

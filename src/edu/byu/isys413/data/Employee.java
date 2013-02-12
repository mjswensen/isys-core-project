/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.


package edu.byu.isys413.data;

import java.util.Date;

/**
 * An employee.
 */
public class Employee extends BusinessObject {

    @BusinessObjectField
    private String firstName;
    @BusinessObjectField
    private String middleName;
    @BusinessObjectField
    private String lastName;
    @BusinessObjectField
    private Date hireDate = null;
    @BusinessObjectField
    private String phone;
    @BusinessObjectField
    private double salary;
    @BusinessObjectField
    private String storeId;
    @BusinessObjectField
    private Store store;
    @BusinessObjectField
    private String positionId;
    @BusinessObjectField
    private String divisionId;
    
    /** Creates a new instance of BusinessObject */
	public Employee(String id) {
		super(id);
	}

}

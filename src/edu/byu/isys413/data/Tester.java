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

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Tests for the program.  
 *
 * See http://www.junit.org/apidocs/org/junit/Assert.html for the
 * available assertions you can make.
 * 
 * @version 1.2
 */
public class Tester {

	// for comparing dates
	SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	public Tester() throws Exception {
		CreateDB.main(null);
	}
	
	/** Test the Store BO */
	@Test
	public void TestStore() throws Exception {
		// Grab associated objects
		Employee manager = BusinessObjectDAO.getInstance().read("employee1");
		
		// Test create/save
		Store st = BusinessObjectDAO.getInstance().create("Store", "1store");
		st.setLocation("South Provo");
		st.setManager(manager);
		st.setPhone("801-398-1837");
		st.setAddress("200 N 300 W");
		st.setCity("Provo");
		st.setState("UT");
		st.setZip("76842");
		st.save();
	
		// Test read from cache
		Store st2 = BusinessObjectDAO.getInstance().read("1store");
		assertSame(st, st2);
		
		// Test read from DB
		Cache.getInstance().clear();
		Store st3 = BusinessObjectDAO.getInstance().read("1store");
		assertEquals(st.getId(), st3.getId());
		assertEquals(st.getLocation(), st3.getLocation());
		assertEquals(st.getManagerId(), st3.getManagerId());
		assertEquals(st.getAddress(), st3.getAddress());
		assertEquals(st.getCity(), st3.getCity());
		assertEquals(st.getState(), st3.getState());
		assertEquals(st.getZip(), st3.getZip());
		
		// Test delete
		BusinessObjectDAO.getInstance().delete(st);
		
		// To be sure it's deleted, create another with same id.
		Store st4 = BusinessObjectDAO.getInstance().create("Store", "1store");
		st4.setLocation("North Provo");
		st4.setManager(manager);
		st4.setPhone("801-398-1855");
		st4.setAddress("1200 N 300 E");
		st4.setCity("Provo");
		st4.setState("UT");
		st4.setZip("76333");
		st4.save();
		
		// Employee will test search methods
	}

	/** Test the Employee BO */
	@Test
	public void TestEmployee() throws Exception {
		// Grab associated objects
		Store store = BusinessObjectDAO.getInstance().read("store1");
		
		Employee emp = BusinessObjectDAO.getInstance().create("Employee", "1employee");
		emp.setFirstName("Mark");
		emp.setMiddleName("James");
		emp.setLastName("Peterson");
		emp.setHireDate(new Date());
		emp.setPhone("541-124-1244");
		emp.setSalary(40000.0);
		emp.setStore(store);
		emp.save();

		// since emp is in the Cache, this tests reading from the cache
		Employee emp2 = BusinessObjectDAO.getInstance().read("1employee");
		assertSame(emp, emp2);

		// now clear the cache (you'd never do this in the real world)
		// then we can test reading from the database
		Cache.getInstance().clear();
		Employee emp3 = BusinessObjectDAO.getInstance().read("1employee");
		assertEquals(emp.getId().trim(), emp3.getId().trim());
		assertEquals(emp.getFirstName(), emp3.getFirstName());
		assertEquals(emp.getMiddleName(), emp3.getMiddleName());
		assertEquals(emp.getLastName(), emp3.getLastName());
		assertEquals(SDF.format(emp.getHireDate()), SDF.format(emp3.getHireDate()));
		assertEquals(emp.getPhone(), emp3.getPhone());
		assertTrue(emp.getSalary() - emp3.getSalary() < 0.1);
		assertEquals(emp.getStoreId(), emp3.getStoreId());

		// test deleting
		BusinessObjectDAO.getInstance().delete(emp);

		// create another one with the same id (the other should be deleted)
		Employee emp4 = BusinessObjectDAO.getInstance().create("Employee", "1employee");
		emp4.setFirstName("Matthew");
		emp4.setMiddleName("Joshua");
		emp4.setLastName("Davidson");
		emp4.setHireDate(new Date());
		emp4.setPhone("541-623-1988");
		emp4.setSalary(40000.50);
		emp4.setStore(store);
		emp4.save();

		// test the search methods
		List<Employee> emps = BusinessObjectDAO.getInstance().searchForAll("Employee");
		assertEquals(emps.size(), 3);  // 2 from CreateDB, Matthew above
		Employee emp1 = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("id", "employee1"));
		assertEquals(emp1.getId().trim(), "employee1");
		List<Employee> emps2 = BusinessObjectDAO.getInstance().searchForList("Employee", new SearchCriteria("lastname", "%s%", SearchCriteria.LIKE));
		assertEquals(emps2.size(), 3);

	}

	// /** Test the 1-M relationship between Person and Dog (a person can have many dogs) */
	// @Test
	// public void TestPersonDogs() throws Exception {
	// 	// this person will own three dogs
	// 	Person p = BusinessObjectDAO.getInstance().create("Person", "personA");
	// 	p.setFirstName("Jack");
	// 	p.setLastName("O'Neill");
	// 	p.setPhone("555-555-1234");
	// 	p.save();

	// 	// first dog
	// 	Dog d1 = BusinessObjectDAO.getInstance().create("Dog", "dogA");
	// 	d1.setBreed("Bassetoodle");
	// 	d1.setDogName("Flipper");
	// 	d1.setPerson(p);
	// 	d1.save();

	// 	// second dog
	// 	Dog d2 = BusinessObjectDAO.getInstance().create("Dog", "dogB");
	// 	d2.setBreed("Pug");
	// 	d2.setDogName("Buddy");
	// 	d2.setPerson(p);
	// 	d2.save();

	// 	// third dog
	// 	Dog d3 = BusinessObjectDAO.getInstance().create("Dog", "dogC");
	// 	d3.setBreed("Siberian Husky ");
	// 	d3.setDogName("Doc");
	// 	d3.setPerson(p);
	// 	d3.save();

	// 	// retrieve the three dogs from the Person object
	// 	List<Dog> dogs = p.getDogs();
	// 	assertEquals(dogs.size(), 3);
	// 	System.out.println(dogs.get(0).getPerson().getId());
	// 	assertSame(dogs.get(0).getPerson(), p);
	// 	assertSame(dogs.get(1).getPerson(), p);
	// 	assertSame(dogs.get(2).getPerson(), p);
	// }    

	// /** Test the M-M relationship between Person and Car */
	// @Test
	// public void TestPersonCar() throws Exception {
	// 	// this test assumes that certain people and cars are already in the database
	// 	// in the DB, person1 owns only car1, person2 owns car1, car2, car3
	// 	Person person1 = BusinessObjectDAO.getInstance().read("person1");
	// 	Person person2 = BusinessObjectDAO.getInstance().read("person2");
	// 	Car car1 = BusinessObjectDAO.getInstance().read("car1");
	// 	Car car2 = BusinessObjectDAO.getInstance().read("car2");
	// 	Car car3 = BusinessObjectDAO.getInstance().read("car2");

	// 	// test person1's cars
	// 	assertEquals(person1.getCars().size(), 1);
	// 	assertSame(person1.getCars().get(0), car1);

	// 	// test person2's cars
	// 	List<Car> cars = person2.getCars();
	// 	assertEquals(cars.size(), 3);
	// 	assertTrue(cars.contains(car1));
	// 	assertTrue(cars.contains(car2));
	// 	assertTrue(cars.contains(car3));

	// 	// test car1's owners
	// 	List<Person> owners = car1.getOwners();
	// 	assertEquals(owners.size(), 2);
	// 	assertTrue(owners.contains(person1));
	// 	assertTrue(owners.contains(person2));
	// }    



}
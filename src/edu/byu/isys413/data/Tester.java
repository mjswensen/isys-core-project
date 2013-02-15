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
		st.setSalesTaxRate(0.0625);
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
		assertTrue(st.getSalesTaxRate() - st3.getSalesTaxRate() < 0.1);
		
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
		st4.setSalesTaxRate(0.075);
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
		assertEquals(emps.size(), 7);  // 6 from CreateDB, Matthew above
		Employee emp1 = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("id", "employee1"));
		assertEquals(emp1.getId().trim(), "employee1");
		List<Employee> emps2 = BusinessObjectDAO.getInstance().searchForList("Employee", new SearchCriteria("lastname", "%s%", SearchCriteria.LIKE));
		assertEquals(emps2.size(), 5);

	}
	
	/** Test the ConceptualProduct BO (Also tests the Product BO) */
	@Test
	public void TestConceptualProduct() throws Exception {
		// Test create
		ConceptualProduct p = BusinessObjectDAO.getInstance().create("ConceptualProduct","1conceptualProduct");
		p.setPrice(300.0);
		p.setName("Awesome Camera");
		p.setDescription("Point and shoot");
		p.setManufacturer("Japan");
		p.setAverageCost(250.00);
		p.save();
		
		// Test read from cache
		ConceptualProduct p2 = BusinessObjectDAO.getInstance().read("1conceptualProduct");
		assertSame(p, p2);
		
		// Test read from DB
		Cache.getInstance().clear();
		ConceptualProduct p3 = BusinessObjectDAO.getInstance().read("1conceptualProduct");
		assertEquals(p.getId(), p3.getId());
		assertTrue(p.getPrice() - p3.getPrice() < 0.1);
		assertEquals(p.getName(), p3.getName());
		assertEquals(p.getDescription(), p3.getDescription());
		assertEquals(p.getManufacturer(), p3.getManufacturer());
		assertTrue(p.getAverageCost() - p3.getAverageCost() < 0.1);
		
		// Test delete
		BusinessObjectDAO.getInstance().delete(p);
		ConceptualProduct p4 = BusinessObjectDAO.getInstance().create("ConceptualProduct","1conceptualProduct");
		p4.setPrice(300.50);
		p4.setName("Cool Camera");
		p4.setDescription("Point and snap");
		p4.setManufacturer("China");
		p4.setAverageCost(250.50);
		p4.save();
	}
	
	/** Test the PhysicalProduct BO (Also tests the Product BO) */
	@Test
	public void TestPhysicalProduct() throws Exception {
		// Grab associated objects
		Store st = BusinessObjectDAO.getInstance().read("store1");
		ConceptualProduct cp = BusinessObjectDAO.getInstance().read("conceptualProduct1");
		
		// Test create
		PhysicalProduct p = BusinessObjectDAO.getInstance().create("PhysicalProduct", "1physicalProduct");
		p.setPrice(599.99);
		p.setStore(st);
		p.setConceptualProduct(cp);
		p.setSerialNum("8ASDF7JASDF");
		p.setShelfLocation("Top 4th");
		p.setPurchased(new Date());
		p.setCost(400.0);
		p.setStatus("new");
		p.setCommissionRate(0.04);
		p.save();
		
		// Test read from cache
		PhysicalProduct p2 = BusinessObjectDAO.getInstance().read("1physicalProduct");
		assertSame(p, p2);
		
		// Test read from DB
		Cache.getInstance().clear();
		PhysicalProduct p3 = BusinessObjectDAO.getInstance().read("1physicalProduct");
		assertEquals(p.getId(), p3.getId());
		assertTrue(p.getPrice() - p3.getPrice() < 0.1);
		assertEquals(p.getStoreId(), p3.getStoreId());
		assertEquals(p.getConceptualProductId(), p3.getConceptualProductId());
		assertEquals(p.getSerialNum(), p3.getSerialNum());
		assertEquals(p.getShelfLocation(), p3.getShelfLocation());
		assertEquals(SDF.format(p.getPurchased()), SDF.format(p3.getPurchased()));
		assertTrue(p.getCost() - p3.getCost() < 0.1);
		assertEquals(p.getStatus(), p3.getStatus());
		assertTrue(p.getCommissionRate() - p3.getCommissionRate() < 0.1);
		
		// Test delete
		BusinessObjectDAO.getInstance().delete(p);
		// Previous three test cases test ability to rewrite a new record with same ID.
	}
	
	/** Test the StoreProduct (tests the M-M relationship between Stores and ConceptualProducts). */
	@Test
	public void TestStoreProduct() throws Exception {
		// This test assumes that certain Stores and ConceptualProducts are already in the database.
		Store store1 = BusinessObjectDAO.getInstance().read("store1");
		Store store2 = BusinessObjectDAO.getInstance().read("store2");
		ConceptualProduct conceptualProduct1 = BusinessObjectDAO.getInstance().read("conceptualProduct1");
		ConceptualProduct conceptualProduct2 = BusinessObjectDAO.getInstance().read("conceptualProduct2");
		ConceptualProduct conceptualProduct3 = BusinessObjectDAO.getInstance().read("conceptualProduct3");
		
		// Test store1's products.
		assertEquals(store1.getProducts().size(), 3);
		assertSame(store1.getProducts().get(0), conceptualProduct1);
		assertSame(store1.getProducts().get(1), conceptualProduct2);
		assertSame(store1.getProducts().get(2), conceptualProduct3);
		
		// Test store2's products.
		assertEquals(store2.getProducts().size(), 2);
		assertTrue(store2.getProducts().contains(conceptualProduct1));
		assertTrue(store2.getProducts().contains(conceptualProduct2));
		
		// Test conceptualProuct1's stores.
		assertEquals(conceptualProduct1.getStores().size(), 2);
		assertTrue(conceptualProduct1.getStores().contains(store1));
		assertTrue(conceptualProduct1.getStores().contains(store2));
	}
	
	/** Test the Customer */
	@Test
	public void TestCustomer() throws Exception {
		// Test create/save
		Customer cust = BusinessObjectDAO.getInstance().create("Customer", "1customer");
		cust.setFirstName("Billy");
		cust.setLastName("Fredette");
		cust.setPhone("324-230-0234");
		cust.setAddress("465 N 300 E Provo UT 87364");
		cust.save();
	
		// Test read from cache
		Customer cust2 = BusinessObjectDAO.getInstance().read("1customer");
		assertSame(cust, cust2);
		
		// Test read from DB
		Cache.getInstance().clear();
		Customer cust3 = BusinessObjectDAO.getInstance().read("1customer");
		assertEquals(cust.getId(), cust3.getId());
		assertEquals(cust.getFirstName(), cust3.getFirstName());
		assertEquals(cust.getLastName(), cust3.getLastName());
		assertEquals(cust.getPhone(), cust3.getPhone());
		assertEquals(cust.getAddress(), cust3.getAddress());
		
		// Test delete
		BusinessObjectDAO.getInstance().delete(cust);
	}
	
	/** Test the Transaction. Tests the 1-M relationship between transactions and sales. */
	@Test
	public void TestTransaction() throws Exception {
		// Grab associated objects
		Customer cust = BusinessObjectDAO.getInstance().read("customer1");
		Store store = BusinessObjectDAO.getInstance().read("store1");
		Employee emp = BusinessObjectDAO.getInstance().read("employee1");
		ConceptualProduct prod1 = BusinessObjectDAO.getInstance().read("conceptualProduct3");
		PhysicalProduct prod2 = BusinessObjectDAO.getInstance().read("physicalProduct1");
		
		// Test create
		Transaction trans = BusinessObjectDAO.getInstance().create("Transaction", "1transaction");
		trans.setCustomer(cust);
		trans.setStore(store);
		trans.setEmployee(emp);
		trans.setDate(new Date());
		
		// Test adding sales to the transaction.
		Sale sale = BusinessObjectDAO.getInstance().create("Sale", "1sale");
		sale.setProduct((Product)prod1);
		sale.setQuantity(5);
		trans.addSale(sale);
		Sale sale2 = BusinessObjectDAO.getInstance().create("Sale", "2sale");
		sale2.setProduct((Product)prod2);
		sale2.setQuantity(1);
		trans.addSale(sale2);
		
		// Test subtotal, tax, and total
		double subtotal = prod1.getPrice() * sale.getQuantity() + prod2.getPrice() * sale2.getQuantity();
		assertTrue(trans.getSubtotal() - subtotal < 0.1);
		double tax = subtotal * store.getSalesTaxRate();
		assertTrue(trans.getTax() - tax < 0.1);
		double total = tax + subtotal;
		assertTrue(trans.getTotal() - total < 0.1);
		
		trans.save();
		sale.save();
		sale2.save();
		
		// Test read from cache
		Transaction trans2 = BusinessObjectDAO.getInstance().read("1transaction");
		assertSame(trans, trans2);
		
		// Test read from DB
		Cache.getInstance().clear();
		Transaction trans3 = BusinessObjectDAO.getInstance().read("1transaction");
		assertEquals(trans.getId(), trans3.getId());
		assertSame(trans.getCustomer(), trans3.getCustomer());
		assertSame(trans.getStore(), trans3.getStore());
		assertSame(trans.getEmployee(), trans3.getEmployee());
		assertEquals(SDF.format(trans.getDate()), SDF.format(trans3.getDate()));
		assertTrue(trans.getSubtotal() - trans3.getSubtotal() < 0.1);
		assertTrue(trans.getTax() - trans3.getTax() < 0.1);
		assertTrue(trans.getTotal() - trans3.getTotal() < 0.1);
		
		// Test delete.
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

	   



}
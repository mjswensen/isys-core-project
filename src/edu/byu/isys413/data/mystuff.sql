# ------------------------------------------------------------
# Drop tables
# ------------------------------------------------------------

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS generalledger;
DROP TABLE IF EXISTS storeproduct;
DROP TABLE IF EXISTS sale;
DROP TABLE IF EXISTS revenuesource;
DROP TABLE IF EXISTS commission;
DROP TABLE IF EXISTS debitcredit;
DROP TABLE IF EXISTS journalentry;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS physicalproduct;
DROP TABLE IF EXISTS conceptualproduct;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS businessobject;

SET foreign_key_checks = 1;


# ------------------------------------------------------------
# Create database structure
# ------------------------------------------------------------


# Create table businessobject
# ------------------------------------------------------------


CREATE TABLE businessobject (
  id CHAR(40) PRIMARY KEY,
  botype VARCHAR(250)
);



# Create table store
# ------------------------------------------------------------


CREATE TABLE store (
  id CHAR(40) PRIMARY KEY,
  location VARCHAR(50),
  managerid CHAR(40),
  phone VARCHAR(20),
  address VARCHAR(100),
  city VARCHAR(100),
  state CHAR(2),
  zip VARCHAR(12),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# Create table employee
# ------------------------------------------------------------


CREATE TABLE employee (
  id CHAR(40) PRIMARY KEY,
  firstname VARCHAR(50),
  middlename VARCHAR(50),
  lastname VARCHAR(50),
  hiredate date,
  phone VARCHAR(20),
  salary NUMERIC(10,2),
  storeid CHAR(40) REFERENCES store (id),
  positionid CHAR(40),
  divisionid CHAR(40),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);


# Now that both store and employee tables are created, and since they reference each other, add FK to store for managerid.
# ------------------------------------------------------------


ALTER TABLE store ADD FOREIGN KEY (managerid) REFERENCES employee (id);


# Create table product
# ------------------------------------------------------------


CREATE TABLE product (
  id CHAR(40) PRIMARY KEY,
  price NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# Create table conceptualproduct
# ------------------------------------------------------------


CREATE TABLE conceptualproduct (
  id CHAR(40) PRIMARY KEY,
  name VARCHAR(50),
  description TEXT,
  manufacturer VARCHAR(50),
  averagecost NUMERIC(10,2),
  categoryid CHAR(40),
  vendorid CHAR(40),
  FOREIGN KEY (id) REFERENCES product (id)
);



# Create table physicalproduct
# ------------------------------------------------------------


CREATE TABLE physicalproduct (
  id CHAR(40) PRIMARY KEY,
  storeid CHAR(40),
  conceptualproductid CHAR(40),
  serialnum VARCHAR(100),
  shelflocation VARCHAR(100),
  purchased DATETIME,
  cost NUMERIC(10,2),
  status VARCHAR(100),
  commissionrate DOUBLE,
  FOREIGN KEY (id) REFERENCES product (id),
  FOREIGN KEY (storeid) REFERENCES store (id),
  FOREIGN KEY (conceptualproductid) REFERENCES conceptualproduct (id)
);



# Create table storeproduct
# ------------------------------------------------------------


CREATE TABLE storeproduct (
  id CHAR(40) PRIMARY KEY,
  conceptualproductid CHAR(40),
  storeid CHAR(40),
  quantityonhand int(11),
  shelflocation VARCHAR(50),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (conceptualproductid) REFERENCES conceptualproduct (id),
  FOREIGN KEY (storeid) REFERENCES store (id)
);



# Create table customer
# ------------------------------------------------------------


CREATE TABLE customer (
  id CHAR(40) PRIMARY KEY,
  firstname VARCHAR(100),
  lastname VARCHAR(100),
  phone VARCHAR(20),
  address TEXT,
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# Create table transaction
# ------------------------------------------------------------


CREATE TABLE transaction (
  id CHAR(40) PRIMARY KEY,
  customerid CHAR(40),
  storeid CHAR(40),
  employeeid CHAR(40),
  date DATETIME,
  subtotal NUMERIC(10,2),
  tax NUMERIC(10,2),
  total NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (customerid) REFERENCES customer (id),
  FOREIGN KEY (storeid) REFERENCES store (id),
  FOREIGN KEY (employeeid) REFERENCES employee (id)
);



# Create table journalentry
# ------------------------------------------------------------


CREATE TABLE journalentry (
  id CHAR(40) PRIMARY KEY,
  transactionid CHAR(40),
  date DATETIME,
  isposted TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (transactionid) REFERENCES transaction (id)
);



# Create table debitcredit
# ------------------------------------------------------------


CREATE TABLE debitcredit (
  id CHAR(40) PRIMARY KEY,
  journalentryid CHAR(40),
  type enum('DR','CR'),
  glaccount VARCHAR(50),
  amount NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (journalentryid) REFERENCES journalentry (id)
);



# Create table commission
# ------------------------------------------------------------


CREATE TABLE commission (
  id CHAR(40) PRIMARY KEY,
  transactionid CHAR(40),
  employeeid CHAR(40),
  amount NUMERIC(10,2),
  date DATETIME,
  ispaid TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (transactionid) REFERENCES transaction (id),
  FOREIGN KEY (employeeid) REFERENCES employee (id)
);



# Create table revenuesource
# ------------------------------------------------------------


CREATE TABLE revenuesource (
  id CHAR(40) PRIMARY KEY,
  transactionid CHAR(40),
  chargeamount NUMERIC(10,2),
  type VARCHAR(30),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (transactionid) REFERENCES transaction (id)
);



# Create table sale
# ------------------------------------------------------------


CREATE TABLE sale (
  id CHAR(40) PRIMARY KEY,
  productid CHAR(40),
  quantity int(11),
  FOREIGN KEY (id) REFERENCES revenuesource (id)
);



# Create table generalledger
# ------------------------------------------------------------


CREATE TABLE generalledger (
  id CHAR(40) PRIMARY KEY,
  account VARCHAR(50),
  balance NUMERIC(10,2),
  type enum('DR','CR'),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# ------------------------------------------------------------
# Populate tables with testing data
# ------------------------------------------------------------



# Populate table businessobject
# ------------------------------------------------------------


INSERT INTO `businessobject` (`id`, `botype`)
VALUES
  ('commission1','edu.byu.isys413.data.Commission'),
  ('conceptualProduct1','edu.byu.isys413.data.ConceptualProduct'),
  ('customer1','edu.byu.isys413.data.Customer'),
  ('debitCredit1','edu.byu.isys413.data.DebitCredit'),
  ('debitCredit2','edu.byu.isys413.data.DebitCredit'),
  ('debitCredit3','edu.byu.isys413.data.DebitCredit'),
  ('debitCredit4','edu.byu.isys413.data.DebitCredit'),
  ('employee1','edu.byu.isys413.data.Employee'),
  ('employee2','edu.byu.isys413.data.Employee'),
  ('glAccount1','edu.byu.isys413.data.GeneralLedger'),
  ('glAccount2','edu.byu.isys413.data.GeneralLedger'),
  ('glAccount3','edu.byu.isys413.data.GeneralLedger'),
  ('glAccount4','edu.byu.isys413.data.GeneralLedger'),
  ('glAccount5','edu.byu.isys413.data.GeneralLedger'),
  ('journalEntry1','edu.byu.isys413.data.JournalEntry'),
  ('physicalProduct1','edu.byu.isys413.data.PhysicalProduct'),
  ('sale1','edu.byu.isys413.data.Sale'),
  ('store1','edu.byu.isys413.data.Store'),
  ('storeProduct1','edu.byu.isys413.data.StoreProduct'),
  ('transaction1','edu.byu.isys413.data.Transaction');



# Populate table store
# ------------------------------------------------------------


INSERT INTO `store` (`id`, `location`, `managerid`, `phone`, `address`, `city`, `state`, `zip`)
VALUES
  ('store1','Orem',NULL,'801-123-1234','1600 N 800 E','Orem','UT','84720');



# Populate table employee
# ------------------------------------------------------------


INSERT INTO `employee` (`id`, `firstname`, `middlename`, `lastname`, `hiredate`, `phone`, `salary`, `storeid`, `positionid`, `divisionid`)
VALUES
  ('employee1','John','Peter','Appleseed','2007-02-07','801-347-2473',65000.00,'store1',NULL,NULL),
  ('employee2','Mark','Leo','Jackson','2008-06-23','801-238-4721',44000.00,'store1',NULL,NULL);


# Now that both store and employee tables are created, and since they reference each other, add FK to store for managerid.
# ------------------------------------------------------------


UPDATE store SET managerid = 'employee1' WHERE id = 'store1';



# Populate table product
# ------------------------------------------------------------


INSERT INTO `product` (`id`, `price`)
VALUES
  ('conceptualProduct1',499.99),
  ('physicalProduct1',499.99);



# Populate table conceptualproduct
# ------------------------------------------------------------


INSERT INTO `conceptualproduct` (`id`, `name`, `description`, `manufacturer`, `averagecost`, `categoryid`, `vendorid`)
VALUES
  ('conceptualProduct1','EOS','Entry-level digital camera.','Canon',300.00,NULL,NULL);



# Populate table physicalproduct
# ------------------------------------------------------------


INSERT INTO `physicalproduct` (`id`, `storeid`, `conceptualproductid`, `serialnum`, `shelflocation`, `purchased`, `cost`, `status`, `commissionrate`)
VALUES
  ('physicalProduct1','store1','conceptualProduct1','JSDF79SF9S8CX','Aisle 5','2012-12-03 08:00:00',300.00,NULL,0.04);



# Populate table storeproduct
# ------------------------------------------------------------


INSERT INTO `storeproduct` (`id`, `conceptualproductid`, `storeid`, `quantityonhand`, `shelflocation`)
VALUES
  ('storeProduct1','conceptualProduct1','store1',5,'Aisle 5');


# Populate table customer
# ------------------------------------------------------------


INSERT INTO `customer` (`id`, `firstname`, `lastname`, `phone`, `address`)
VALUES
  ('customer1','Jeff','Johnson','801-148-1844','627 N 300 W\nProvo, UT 84601');



# Populate table transaction
# ------------------------------------------------------------


INSERT INTO `transaction` (`id`, `customerid`, `storeid`, `employeeid`, `date`, `subtotal`, `tax`, `total`)
VALUES
  ('transaction1','customer1','store1','employee2','2012-08-08 12:59:04',499.99,32.50,532.49);



# Populate table journalentry
# ------------------------------------------------------------


INSERT INTO `journalentry` (`id`, `transactionid`, `date`, `isposted`)
VALUES
  ('journalEntry1','transaction1','2012-08-08 12:59:04',0);



# Populate table debitcredit
# ------------------------------------------------------------


INSERT INTO `debitcredit` (`id`, `journalentryid`, `type`, `glaccount`, `amount`)
VALUES
  ('debitCredit1','journalEntry1','DR','Cash',532.49),
  ('debitCredit2','journalEntry1','CR','Commission Payable',20.00),
  ('debitCredit3','journalEntry1','CR','Tax Payable',32.50),
  ('debitCredit4','journalEntry1','CR','sale Revenue',479.99);



# Populate table commission
# ------------------------------------------------------------


INSERT INTO `commission` (`id`, `transactionid`, `employeeid`, `amount`, `date`, `ispaid`)
VALUES
  ('commission1','transaction1','employee2',20.00,'2012-08-08 12:59:04',0);



# Populate table revenuesource
# ------------------------------------------------------------


INSERT INTO `revenuesource` (`id`, `transactionid`, `chargeamount`, `type`)
VALUES
  ('sale1','transaction1',532.49,'Sale');



# Populate table sale
# ------------------------------------------------------------


INSERT INTO `sale` (`id`, `productid`, `quantity`)
VALUES
  ('sale1','physicalProduct1',1);



# Populate table generalledger
# ------------------------------------------------------------


INSERT INTO `generalledger` (`id`, `account`, `balance`, `type`)
VALUES
  ('glAccount1','Cash',25000.00,'DR'),
  ('glAccount2','Commission Payable',3300.00,'CR'),
  ('glAccount3','Commission Expense',0.00,'DR'),
  ('glAccount4','sale Revenue',125000.00,'DR'),
  ('glAccount5','Tax Payable',15000.00,'CR');



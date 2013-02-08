# ------------------------------------------------------------
# Drop tables
# ------------------------------------------------------------

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS general_ledger;
DROP TABLE IF EXISTS store_products;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS revenue_sources;
DROP TABLE IF EXISTS commissions;
DROP TABLE IF EXISTS debit_credit;
DROP TABLE IF EXISTS journal_entries;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS stores;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS physical_products;
DROP TABLE IF EXISTS conceptual_products;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS business_objects;

SET foreign_key_checks = 1;


# ------------------------------------------------------------
# Create database structure
# ------------------------------------------------------------


# Create table business_objects
# ------------------------------------------------------------


CREATE TABLE business_objects (
  id CHAR(40) PRIMARY KEY,
  botype VARCHAR(250)
);



# Create table stores
# ------------------------------------------------------------


CREATE TABLE stores (
  id CHAR(40) PRIMARY KEY,
  location VARCHAR(50),
  manager CHAR(40),
  phone VARCHAR(20),
  address VARCHAR(100),
  city VARCHAR(100),
  state CHAR(2),
  zip VARCHAR(12),
  FOREIGN KEY (id) REFERENCES business_objects (id)
);



# Create table employees
# ------------------------------------------------------------


CREATE TABLE employees (
  id CHAR(40) PRIMARY KEY,
  first_name VARCHAR(50),
  middle_name VARCHAR(50),
  last_name VARCHAR(50),
  hire_date date,
  phone VARCHAR(20),
  salary NUMERIC(10,2),
  store_id CHAR(40) REFERENCES stores (id),
  position_id CHAR(40),
  division_id CHAR(40),
  FOREIGN KEY (id) REFERENCES business_objects (id)
);


# Now that both stores and employees tables are created, and since they reference each other, add FK to stores for manager.
# ------------------------------------------------------------


ALTER TABLE stores ADD FOREIGN KEY (manager) REFERENCES employees (id);


# Create table products
# ------------------------------------------------------------


CREATE TABLE products (
  id CHAR(40) PRIMARY KEY,
  price NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES business_objects (id)
);



# Create table conceptual_products
# ------------------------------------------------------------


CREATE TABLE conceptual_products (
  id CHAR(40) PRIMARY KEY,
  name VARCHAR(50),
  description TEXT,
  manufacturer VARCHAR(50),
  average_cost NUMERIC(10,2),
  category_id CHAR(40),
  vendor_id CHAR(40),
  FOREIGN KEY (id) REFERENCES products (id)
);



# Create table physical_products
# ------------------------------------------------------------


CREATE TABLE physical_products (
  id CHAR(40) PRIMARY KEY,
  store_id CHAR(40),
  conceptual_product_id CHAR(40),
  serial_num VARCHAR(100),
  shelf_location VARCHAR(100),
  purchased DATETIME,
  cost NUMERIC(10,2),
  status VARCHAR(100),
  commission_rate DOUBLE,
  FOREIGN KEY (id) REFERENCES products (id),
  FOREIGN KEY (store_id) REFERENCES stores (id),
  FOREIGN KEY (conceptual_product_id) REFERENCES conceptual_products (id)
);



# Create table store_products
# ------------------------------------------------------------


CREATE TABLE store_products (
  id CHAR(40) PRIMARY KEY,
  conceptual_product_id CHAR(40),
  store_id CHAR(40),
  quantity_on_hand int(11),
  shelf_location VARCHAR(50),
  FOREIGN KEY (id) REFERENCES business_objects (id),
  FOREIGN KEY (conceptual_product_id) REFERENCES conceptual_products (id),
  FOREIGN KEY (store_id) REFERENCES stores (id)
);



# Create table customers
# ------------------------------------------------------------


CREATE TABLE customers (
  id CHAR(40) PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  phone VARCHAR(20),
  address TEXT,
  FOREIGN KEY (id) REFERENCES business_objects (id)
);



# Create table transactions
# ------------------------------------------------------------


CREATE TABLE transactions (
  id CHAR(40) PRIMARY KEY,
  customer_id CHAR(40),
  store_id CHAR(40),
  employee_id CHAR(40),
  date DATETIME,
  subtotal NUMERIC(10,2),
  tax NUMERIC(10,2),
  total NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES business_objects (id),
  FOREIGN KEY (customer_id) REFERENCES customers (id),
  FOREIGN KEY (store_id) REFERENCES stores (id),
  FOREIGN KEY (employee_id) REFERENCES employees (id)
);



# Create table journal_entries
# ------------------------------------------------------------


CREATE TABLE journal_entries (
  id CHAR(40) PRIMARY KEY,
  transaction_id CHAR(40),
  date DATETIME,
  is_posted TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES business_objects (id),
  FOREIGN KEY (transaction_id) REFERENCES transactions (id)
);



# Create table debit_credit
# ------------------------------------------------------------


CREATE TABLE debit_credit (
  id CHAR(40) PRIMARY KEY,
  journal_entry_id CHAR(40),
  type enum('DR','CR'),
  gl_account VARCHAR(50),
  amount NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES business_objects (id),
  FOREIGN KEY (journal_entry_id) REFERENCES journal_entries (id)
);



# Create table commissions
# ------------------------------------------------------------


CREATE TABLE commissions (
  id CHAR(40) PRIMARY KEY,
  transaction_id CHAR(40),
  employee_id CHAR(40),
  amount NUMERIC(10,2),
  date DATETIME,
  is_paid TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES business_objects (id),
  FOREIGN KEY (transaction_id) REFERENCES transactions (id),
  FOREIGN KEY (employee_id) REFERENCES employees (id)
);



# Create table revenue_sources
# ------------------------------------------------------------


CREATE TABLE revenue_sources (
  id CHAR(40) PRIMARY KEY,
  transaction_id CHAR(40),
  charge_amount NUMERIC(10,2),
  type VARCHAR(30),
  FOREIGN KEY (id) REFERENCES business_objects (id),
  FOREIGN KEY (transaction_id) REFERENCES transactions (id)
);



# Create table sales
# ------------------------------------------------------------


CREATE TABLE sales (
  id CHAR(40) PRIMARY KEY,
  product_id CHAR(40),
  quantity int(11),
  FOREIGN KEY (id) REFERENCES business_objects (id)
);



# Create table general_ledger
# ------------------------------------------------------------


CREATE TABLE general_ledger (
  id CHAR(40) PRIMARY KEY,
  account VARCHAR(50),
  balance NUMERIC(10,2),
  type enum('DR','CR'),
  FOREIGN KEY (id) REFERENCES business_objects (id)
);



# ------------------------------------------------------------
# Populate tables with testing data
# ------------------------------------------------------------



# Populate table business_objects
# ------------------------------------------------------------


INSERT INTO `business_objects` (`id`, `botype`)
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



# Populate table stores
# ------------------------------------------------------------


INSERT INTO `stores` (`id`, `location`, `manager`, `phone`, `address`, `city`, `state`, `zip`)
VALUES
  ('store1','Orem',NULL,'801-123-1234','1600 N 800 E','Orem','UT','84720');



# Populate table employees
# ------------------------------------------------------------


INSERT INTO `employees` (`id`, `first_name`, `middle_name`, `last_name`, `hire_date`, `phone`, `salary`, `store_id`, `position_id`, `division_id`)
VALUES
  ('employee1','John','Peter','Appleseed','2007-02-07','801-347-2473',65000.00,'store1',NULL,NULL),
  ('employee2','Mark','Leo','Jackson','2008-06-23','801-238-4721',44000.00,'store1',NULL,NULL);


# Now that both stores and employees tables are created, and since they reference each other, add FK to store for manager.
# ------------------------------------------------------------


UPDATE stores SET manager = 'employee1' WHERE id = 'store1';



# Populate table products
# ------------------------------------------------------------


INSERT INTO `products` (`id`, `price`)
VALUES
  ('conceptualProduct1',499.99),
  ('physicalProduct1',499.99);



# Populate table conceptual_products
# ------------------------------------------------------------


INSERT INTO `conceptual_products` (`id`, `name`, `description`, `manufacturer`, `average_cost`, `category_id`, `vendor_id`)
VALUES
  ('conceptualProduct1','EOS','Entry-level digital camera.','Canon',300.00,NULL,NULL);



# Populate table physical_products
# ------------------------------------------------------------


INSERT INTO `physical_products` (`id`, `store_id`, `conceptual_product_id`, `serial_num`, `shelf_location`, `purchased`, `cost`, `status`, `commission_rate`)
VALUES
  ('physicalProduct1','store1','conceptualProduct1','JSDF79SF9S8CX','Aisle 5','2012-12-03 08:00:00',300.00,NULL,0.04);



# Populate table store_products
# ------------------------------------------------------------


INSERT INTO `store_products` (`id`, `conceptual_product_id`, `store_id`, `quantity_on_hand`, `shelf_location`)
VALUES
  ('storeProduct1','conceptualProduct1','store1',5,'Aisle 5');


# Populate table customers
# ------------------------------------------------------------


INSERT INTO `customers` (`id`, `first_name`, `last_name`, `phone`, `address`)
VALUES
  ('customer1','Jeff','Johnson','801-148-1844','627 N 300 W\nProvo, UT 84601');



# Populate table transactions
# ------------------------------------------------------------


INSERT INTO `transactions` (`id`, `customer_id`, `store_id`, `employee_id`, `date`, `subtotal`, `tax`, `total`)
VALUES
  ('transaction1','customer1','store1','employee2','2012-08-08 12:59:04',499.99,32.50,532.49);



# Populate table journal_entries
# ------------------------------------------------------------


INSERT INTO `journal_entries` (`id`, `transaction_id`, `date`, `is_posted`)
VALUES
  ('journalEntry1','transaction1','2012-08-08 12:59:04',0);



# Populate table debit_credit
# ------------------------------------------------------------


INSERT INTO `debit_credit` (`id`, `journal_entry_id`, `type`, `gl_account`, `amount`)
VALUES
  ('debitCredit1','journalEntry1','DR','Cash',532.49),
  ('debitCredit2','journalEntry1','CR','Commission Payable',20.00),
  ('debitCredit3','journalEntry1','CR','Tax Payable',32.50),
  ('debitCredit4','journalEntry1','CR','Sales Revenue',479.99);



# Populate table commissions
# ------------------------------------------------------------


INSERT INTO `commissions` (`id`, `transaction_id`, `employee_id`, `amount`, `date`, `is_paid`)
VALUES
  ('commission1','transaction1','employee2',20.00,'2012-08-08 12:59:04',0);



# Populate table revenue_sources
# ------------------------------------------------------------


INSERT INTO `revenue_sources` (`id`, `transaction_id`, `charge_amount`, `type`)
VALUES
  ('sale1','transaction1',532.49,'Sale');



# Populate table sales
# ------------------------------------------------------------


INSERT INTO `sales` (`id`, `product_id`, `quantity`)
VALUES
  ('Sale1','physicalProduct1',1);



# Populate table general_ledger
# ------------------------------------------------------------


INSERT INTO `general_ledger` (`id`, `account`, `balance`, `type`)
VALUES
  ('glAccount1','Cash',25000.00,'DR'),
  ('glAccount2','Commission Payable',3300.00,'CR'),
  ('glAccount3','Commission Expense',0.00,'DR'),
  ('glAccount4','Sales Revenue',125000.00,'DR'),
  ('glAccount5','Tax Payable',15000.00,'CR');



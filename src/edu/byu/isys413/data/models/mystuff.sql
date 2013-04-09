# ------------------------------------------------------------ ;
# Drop tables ;
# ------------------------------------------------------------ ;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS printorder;
DROP TABLE IF EXISTS print;
DROP TABLE IF EXISTS picture;
DROP TABLE IF EXISTS generalledger;
DROP TABLE IF EXISTS storeproduct;
DROP TABLE IF EXISTS fee;
DROP TABLE IF EXISTS rental;
DROP TABLE IF EXISTS sale;
DROP TABLE IF EXISTS revenuesource;
DROP TABLE IF EXISTS commission;
DROP TABLE IF EXISTS debitcredit;
DROP TABLE IF EXISTS journalentry;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS computer;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS membership;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS forsale;
DROP TABLE IF EXISTS forrent;
DROP TABLE IF EXISTS physicalproduct;
DROP TABLE IF EXISTS conceptualrental;
DROP TABLE IF EXISTS conceptualproduct;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS businessobject;

SET foreign_key_checks = 1;


# ------------------------------------------------------------ ;
# Create database structure ;
# ------------------------------------------------------------ ;


# Create table businessobject ;
# ------------------------------------------------------------ ;


CREATE TABLE businessobject (
  id CHAR(40) PRIMARY KEY,
  botype VARCHAR(250)
);



# Create table store ;
# ------------------------------------------------------------ ;


CREATE TABLE store (
  id CHAR(40) PRIMARY KEY,
  location VARCHAR(50),
  managerid CHAR(40),
  phone VARCHAR(20),
  address VARCHAR(100),
  city VARCHAR(100),
  state CHAR(2),
  zip VARCHAR(12),
  salestaxrate NUMERIC(6,5),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# Create table employee ;
# ------------------------------------------------------------ ;


CREATE TABLE employee (
  id CHAR(40) PRIMARY KEY,
  netid VARCHAR(40),
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


# Now that both store and employee tables are created, and since they reference each other, add FK to store for managerid. ;
# ------------------------------------------------------------ ;


ALTER TABLE store ADD FOREIGN KEY (managerid) REFERENCES employee (id);


# Create table computer ;
# ------------------------------------------------------------ ;


CREATE TABLE computer (
  id CHAR(40),
  storeid CHAR(40),
  mac CHAR(17),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (storeid) REFERENCES store (id)
);


# Create table product ;
# ------------------------------------------------------------ ;


CREATE TABLE product (
  id CHAR(40) PRIMARY KEY,
  price NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# Create table conceptualproduct ;
# ------------------------------------------------------------ ;


CREATE TABLE conceptualproduct (
  id CHAR(40) PRIMARY KEY,
  name VARCHAR(50),
  description TEXT,
  manufacturer VARCHAR(50),
  averagecost NUMERIC(10,2),
  commissionrate NUMERIC (6,5),
  sku VARCHAR(20),
  categoryid CHAR(40),
  vendorid CHAR(40),
  FOREIGN KEY (id) REFERENCES product (id)
);


# Create table conceptualrental ;
# ------------------------------------------------------------ ;

CREATE TABLE conceptualrental (
  id CHAR(40) PRIMARY KEY,
  priceperday NUMERIC(10,2),
  replacementprice NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES conceptualproduct (id)
);


# Create table physicalproduct ;
# ------------------------------------------------------------ ;


CREATE TABLE physicalproduct (
  id CHAR(40) PRIMARY KEY,
  storeid CHAR(40),
  conceptualproductid CHAR(40),
  serialnum VARCHAR(100),
  shelflocation VARCHAR(100),
  purchased DATETIME,
  cost NUMERIC(10,2),
  available TINYINT(4),
  commissionrate NUMERIC(6,5),
  type ENUM('ForSale','ForRent'),
  FOREIGN KEY (id) REFERENCES product (id),
  FOREIGN KEY (storeid) REFERENCES store (id),
  FOREIGN KEY (conceptualproductid) REFERENCES conceptualproduct (id)
);

# Create table storeproduct ;
# ------------------------------------------------------------ ;


CREATE TABLE storeproduct (
  id CHAR(40) PRIMARY KEY,
  conceptualproductid CHAR(40),
  storeid CHAR(40),
  quantityonhand INT(11),
  shelflocation VARCHAR(50),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (conceptualproductid) REFERENCES conceptualproduct (id),
  FOREIGN KEY (storeid) REFERENCES store (id)
);



# Create table customer ;
# ------------------------------------------------------------ ;


CREATE TABLE customer (
  id CHAR(40) PRIMARY KEY,
  firstname VARCHAR(100),
  lastname VARCHAR(100),
  phone VARCHAR(20),
  email VARCHAR(50),
  address TEXT,
  password VARCHAR(50),
  validationcode CHAR(40),
  valid TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES businessobject (id)
);



# Create table customer ;
# ------------------------------------------------------------ ;


CREATE TABLE membership (
  id CHAR(40) PRIMARY KEY,
  customerid CHAR(40),
  creditcard VARCHAR(20),
  startdate DATETIME,
  expiredate DATETIME,
  trial TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (customerid) REFERENCES customer (id)
);



# Create table transaction ;
# ------------------------------------------------------------ ;


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



# Create table journalentry ;
# ------------------------------------------------------------ ;


CREATE TABLE journalentry (
  id CHAR(40) PRIMARY KEY,
  transactionid CHAR(40),
  date DATETIME,
  posted TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (transactionid) REFERENCES transaction (id)
);



# Create table debitcredit ;
# ------------------------------------------------------------ ;


CREATE TABLE debitcredit (
  id CHAR(40) PRIMARY KEY,
  journalentryid CHAR(40),
  type enum('DR','CR'),
  glaccount VARCHAR(50),
  amount NUMERIC(10,2),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (journalentryid) REFERENCES journalentry (id)
);



# Create table commission ;
# ------------------------------------------------------------ ;


CREATE TABLE commission (
  id CHAR(40) PRIMARY KEY,
  transactionid CHAR(40),
  employeeid CHAR(40),
  amount NUMERIC(10,2),
  date DATETIME,
  paid TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (transactionid) REFERENCES transaction (id),
  FOREIGN KEY (employeeid) REFERENCES employee (id)
);



# Create table revenuesource ;
# ------------------------------------------------------------ ;


CREATE TABLE revenuesource (
  id CHAR(40) PRIMARY KEY,
  transactionid CHAR(40),
  chargeamount NUMERIC(10,2),
  type VARCHAR(30),
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (transactionid) REFERENCES transaction (id)
);



# Create table sale ;
# ------------------------------------------------------------ ;


CREATE TABLE sale (
  id CHAR(40) PRIMARY KEY,
  productid CHAR(40),
  quantity int(11),
  FOREIGN KEY (id) REFERENCES revenuesource (id),
  FOREIGN KEY (productid) REFERENCES product (id)
);



# Create table rental ;
# ------------------------------------------------------------ ;


CREATE TABLE rental (
  id CHAR(40) PRIMARY KEY,
  forrentid CHAR(40),
  dateout DATETIME,
  datein DATETIME,
  datedue DATETIME,
  workordernumber VARCHAR(50),
  remindersent TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES revenuesource (id)
);


# Create table forrent ;
# ------------------------------------------------------------ ;

CREATE TABLE forrent (
  id CHAR(40) PRIMARY KEY,
  currentrentalid CHAR(40),
  timesrented INT(11),
  FOREIGN KEY (id) REFERENCES physicalproduct (id),
  FOREIGN KEY (currentrentalid) REFERENCES rental(id)
);


# Now that both rental and forrent tables are created, and since they reference each other, add FK to rental for forrentid. ;
# ------------------------------------------------------------ ;


ALTER TABLE rental ADD FOREIGN KEY (forrentid) REFERENCES forrent (id);


# Create table fee ;
# ------------------------------------------------------------ ;


CREATE TABLE fee (
  id CHAR(40) PRIMARY KEY,
  rentalid CHAR(40),
  amount NUMERIC(10,2),
  waived TINYINT(4) DEFAULT 0,
  FOREIGN KEY (id) REFERENCES revenuesource (id),
  FOREIGN KEY (rentalid) REFERENCES rental (id)
);


# Create table forsale ;
# ------------------------------------------------------------ ;


CREATE TABLE forsale (
  id CHAR(40) PRIMARY KEY,
  used TINYINT(4),
  FOREIGN KEY (id) REFERENCES physicalproduct (id)
);


# Create table generalledger ;
# ------------------------------------------------------------ ;


CREATE TABLE generalledger (
  id CHAR(40) PRIMARY KEY,
  account VARCHAR(50),
  balance NUMERIC(10,2),
  type enum('DR','CR'),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);


# Create table picture ;
# ------------------------------------------------------------ ;


CREATE TABLE picture (
  id CHAR(40) PRIMARY KEY,
  customerid CHAR(40),
  caption VARCHAR(100),
  picdata LONGTEXT,
  FOREIGN KEY (id) REFERENCES businessobject (id),
  FOREIGN KEY (customerid) REFERENCES customer (id)
);



# Create table print ;
# ------------------------------------------------------------ ;

CREATE TABLE print (
  id CHAR(40) PRIMARY KEY,
  price NUMERIC(10,2),
  size VARCHAR(45),
  type VARCHAR(45),
  FOREIGN KEY (id) REFERENCES businessobject (id)
);


# Create table printorder ;
# ------------------------------------------------------------ ;


CREATE TABLE printorder (
  id CHAR(40) PRIMARY KEY,
  pictureid CHAR(40),
  printid CHAR(40),
  quantity INT(11),
  FOREIGN KEY (id) REFERENCES revenuesource (id),
  FOREIGN KEY (pictureid) REFERENCES picture (id),
  FOREIGN KEY (printid) REFERENCES print (id)
);


# ------------------------------------------------------------ ;
# Populate tables with testing data ;
# ------------------------------------------------------------ ;



# Populate table businessobject ;
# ------------------------------------------------------------ ;


INSERT INTO `businessobject` (`id`, `botype`)
VALUES
  ('commission1','edu.byu.isys413.data.models.Commission'),
  ('conceptualProduct1','edu.byu.isys413.data.models.ConceptualProduct'),
  ('conceptualProduct2','edu.byu.isys413.data.models.ConceptualProduct'),
  ('conceptualProduct3','edu.byu.isys413.data.models.ConceptualProduct'),
  ('conceptualRental1','edu.byu.isys413.data.models.ConceptualRental'),
  ('customer1','edu.byu.isys413.data.models.Customer'),
  ('customer2','edu.byu.isys413.data.models.Customer'),
  ('membership1','edu.byu.isys413.data.models.Membership'),
  ('debitCredit1','edu.byu.isys413.data.models.DebitCredit'),
  ('debitCredit2','edu.byu.isys413.data.models.DebitCredit'),
  ('debitCredit3','edu.byu.isys413.data.models.DebitCredit'),
  ('debitCredit4','edu.byu.isys413.data.models.DebitCredit'),
  ('debitCredit5','edu.byu.isys413.data.models.DebitCredit'),
  ('employee1','edu.byu.isys413.data.models.Employee'),
  ('employee2','edu.byu.isys413.data.models.Employee'),
  ('employee3','edu.byu.isys413.data.models.Employee'),
  ('employee4','edu.byu.isys413.data.models.Employee'),
  ('employee5','edu.byu.isys413.data.models.Employee'),
  ('employee6','edu.byu.isys413.data.models.Employee'),
  ('glAccount1','edu.byu.isys413.data.models.GeneralLedger'),
  ('glAccount2','edu.byu.isys413.data.models.GeneralLedger'),
  ('glAccount3','edu.byu.isys413.data.models.GeneralLedger'),
  ('glAccount4','edu.byu.isys413.data.models.GeneralLedger'),
  ('glAccount5','edu.byu.isys413.data.models.GeneralLedger'),
  ('journalEntry1','edu.byu.isys413.data.models.JournalEntry'),
  ('forSale1','edu.byu.isys413.data.models.ForSale'),
  ('forSale2','edu.byu.isys413.data.models.ForSale'),
  ('forRent1','edu.byu.isys413.data.models.ForRent'),
  ('forRent2','edu.byu.isys413.data.models.ForRent'),
  ('forRent3','edu.byu.isys413.data.models.ForRent'),
  ('forRent4','edu.byu.isys413.data.models.ForRent'),
  ('sale1','edu.byu.isys413.data.models.Sale'),
  ('rental1','edu.byu.isys413.data.models.Rental'),
  ('rental2','edu.byu.isys413.data.models.Rental'),
  ('rental3','edu.byu.isys413.data.models.Rental'),
  ('fee1','edu.byu.isys413.data.models.Fee'),
  ('store1','edu.byu.isys413.data.models.Store'),
  ('store2','edu.byu.isys413.data.models.Store'),
  ('store3','edu.byu.isys413.data.models.Store'),
  ('computer1','edu.byu.isys413.data.models.Computer'),
  ('computer2','edu.byu.isys413.data.models.Computer'),
  ('computer3','edu.byu.isys413.data.models.Computer'),
  ('computer4','edu.byu.isys413.data.models.Computer'),
  ('storeProduct1','edu.byu.isys413.data.models.StoreProduct'),
  ('storeProduct2','edu.byu.isys413.data.models.StoreProduct'),
  ('storeProduct3','edu.byu.isys413.data.models.StoreProduct'),
  ('storeProduct4','edu.byu.isys413.data.models.StoreProduct'),
  ('storeProduct5','edu.byu.isys413.data.models.StoreProduct'),
  ('transaction1','edu.byu.isys413.data.models.Transaction'),
  ('picture1','edu.byu.isys413.data.models.Picture'),
  ('picture2','edu.byu.isys413.data.models.Picture'),
  ('picture3','edu.byu.isys413.data.models.Picture'),
  ('print1','edu.byu.isys413.data.models.Print'),
  ('printorder1','edu.byu.isys413.data.models.PrintOrder');



# Populate table store ;
# ------------------------------------------------------------ ;


INSERT INTO `store` (`id`, `location`, `managerid`, `phone`, `address`, `city`, `state`, `zip`, `salestaxrate`)
VALUES
  ('store1','Orem',NULL,'801-123-1234','1600 N 800 E','Orem','UT','84720', 0.0625),
  ('store2','Sandy',NULL,'801-321-5423','1800 N 200 E','Sandy','UT','82474', 0.033),
  ('store3','Riverton',NULL,'801-342-9243','1200 N 900 E','Riverton','UT','20948', 0.0755);



# Populate table employee ;
# ------------------------------------------------------------ ;


INSERT INTO `employee` (`id`, `netid`, `firstname`, `middlename`, `lastname`, `hiredate`, `phone`, `salary`, `storeid`, `positionid`, `divisionid`)
VALUES
  ('employee1','mswensen','Matt','Joseph','Swensen','2007-02-07','801-347-2473',65000.00,'store1',NULL,NULL),
  ('employee2','vipermsy','Mark','Leo','Jackson','2008-06-23','801-238-4721',44000.00,'store1',NULL,NULL),
  ('employee3','jmj9210','Jack','Gi','Johnson','2006-06-01','801-522-4423',49000.00,'store2',NULL,NULL),
  ('employee4','lijuny','Andrea','LuEtta','Terribilini','2006-07-01','801-248-4441',40000.00,'store2',NULL,NULL),
  ('employee5','','Kyle','Jacob','Armstrong','2008-11-13','801-233-4111',50000.00,'store3',NULL,NULL),
  ('employee6','','Richard','Alfred','Yankee','2002-05-21','801-233-4221',64000.00,'store3',NULL,NULL);


# Now that both store and employee tables are created, and since they reference each other, add FK to store for managerid. ;
# ------------------------------------------------------------ ;


UPDATE store SET managerid = 'employee1' WHERE id = 'store1';
UPDATE store SET managerid = 'employee3' WHERE id = 'store2';
UPDATE store SET managerid = 'employee5' WHERE id = 'store3';


# Populate table computer ;
# ------------------------------------------------------------ ;


INSERT INTO `computer` (`id`, `storeid`, `mac`)
VALUES
  ('computer1','store1','00:26:bb:17:56:ec'),
  ('computer2','store1','d8:a2:5e:8c:25:5b'),
  ('computer3','store1','CC-AF-78-03-4C-65'),
  ('computer4','store1','f0:b4:79:1f:ee:45');



# Populate table product ;
# ------------------------------------------------------------ ;


INSERT INTO `product` (`id`, `price`)
VALUES
  ('conceptualProduct1',499.99),
  ('conceptualProduct2',29.49),
  ('conceptualProduct3',9.99),
  ('conceptualRental1',399.99),
  ('forSale1',499.99),
  ('forSale2',499.99),
  ('forRent1',399.99),
  ('forRent2',399.99),
  ('forRent3',399.99),
  ('forRent4',399.99);;



# Populate table conceptualproduct ;
# ------------------------------------------------------------ ;


INSERT INTO `conceptualproduct` (`id`, `name`, `description`, `manufacturer`, `averagecost`, `commissionrate`, `sku`, `categoryid`, `vendorid`)
VALUES
  ('conceptualProduct1','EOS','Entry-level digital camera','Canon',300.00,0.05,NULL,NULL,NULL),
  ('conceptualProduct2','Keychain','MyStuff logo keychain','Rocky',10.00,0.01,'123456789',NULL,NULL),
  ('conceptualProduct3','Analog Film','Medium roll of film','Canon',5.00,0.02,'234567891',NULL,NULL),
  ('conceptualRental1','Nikon 500','Intermediate digital camera','Nikon',200.00,0.06,NULL,NULL,NULL);



# Populate table conceptualrental ;
# ------------------------------------------------------------ ;


INSERT INTO `conceptualrental` (`id`, `priceperday`, `replacementprice`)
VALUES
  ('conceptualRental1',25.00,449.99);



# Populate table physicalproduct ;
# ------------------------------------------------------------ ;


INSERT INTO `physicalproduct` (`id`, `storeid`, `conceptualproductid`, `serialnum`, `shelflocation`, `purchased`, `cost`, `available`, `commissionrate`, `type`)
VALUES
  ('forSale1','store1','conceptualProduct1','JSDF79SF9S8CX','Aisle 5','2012-12-03 08:00:00',300.00,1,0.04,'ForSale'),
  ('forSale2','store2','conceptualProduct1','UF8ASCSDJFA8C','Aisle 5','2012-12-03 08:00:00',300.00,1,0.04,'ForSale'),
  ('forRent1','store1','conceptualRental1','URLSUFS74WLSG','Aisle 1','2012-11-07 10:30:00',200.00,0,0.04,'ForRent'),
  ('forRent2','store1','conceptualRental1','POIR52CSUWARE','Aisle 1','2012-11-07 10:30:00',200.00,0,0.04,'ForRent'),
  ('forRent3','store1','conceptualRental1','7S9FJSLKDFA7C','Aisle 1','2012-11-07 10:30:00',200.00,1,0.04,'ForRent'),
  ('forRent4','store1','conceptualRental1','ABC123','Aisle 1','2012-11-07 10:30:00',200.00,1,0.04,'ForRent');


# Populate table forsale ;
# ------------------------------------------------------------ ;


INSERT INTO `forsale` (`id`, `used`)
VALUES
  ('forSale1',0),
  ('forSale2',0);


# Populate table storeproduct ;
# ------------------------------------------------------------ ;


INSERT INTO `storeproduct` (`id`, `conceptualproductid`, `storeid`, `quantityonhand`, `shelflocation`)
VALUES
  ('storeProduct1','conceptualProduct1','store1',5,'Aisle 5'),
  ('storeProduct2','conceptualProduct2','store1',25,'Aisle 10'),
  ('storeProduct3','conceptualProduct3','store1',50,'Aisle 5'),
  ('storeProduct4','conceptualProduct1','store2',7,'Aisle 5'),
  ('storeProduct5','conceptualProduct2','store2',27,'Aisle 10');


# Populate table customer ;
# ------------------------------------------------------------ ;


INSERT INTO `customer` (`id`, `firstname`, `lastname`, `phone`, `email`, `address`, `password`, `validationcode`, `valid`)
VALUES
  ('customer1','Jeff','Johnson','801-148-1844','cust1@me.com','627 N 300 W\nProvo, UT 84601','pass','validatethiscode', 1),
  ('customer2','Bennett','Swensen','801-444-1234','cust2@me.com','645 N 300 W\nProvo, UT 84601','passy','validatethisnewcode', 0);


# Populate table membership ;
# ------------------------------------------------------------ ;


INSERT INTO `membership` (`id`, `customerid`, `creditcard`, `startdate`, `expiredate`, `trial`)
VALUES
  ('membership1','customer1','1234123412341234','2013-03-06 16:30:00', NULL, 0);


# Populate table transaction ;
# ------------------------------------------------------------ ;


INSERT INTO `transaction` (`id`, `customerid`, `storeid`, `employeeid`, `date`, `subtotal`, `tax`, `total`)
VALUES
  ('transaction1','customer1','store1','employee2','2012-08-08 12:59:04',499.99,32.50,532.49);



# Populate table journalentry ;
# ------------------------------------------------------------ ;


INSERT INTO `journalentry` (`id`, `transactionid`, `date`, `posted`)
VALUES
  ('journalEntry1','transaction1','2012-08-08 12:59:04',0);



# Populate table debitcredit ;
# ------------------------------------------------------------ ;


INSERT INTO `debitcredit` (`id`, `journalentryid`, `type`, `glaccount`, `amount`)
VALUES
  ('debitCredit1','journalEntry1','DR','Cash',532.49),
  ('debitCredit2','journalEntry1','CR','Sales Revenue',479.99),
  ('debitCredit3','journalEntry1','CR','Tax Payable',32.50),
  ('debitCredit4','journalEntry1','DR','Commission Expense',20.0),
  ('debitCredit5','journalEntry1','CR','Commission Payable',20.0);
  



# Populate table commission ;
# ------------------------------------------------------------ ;


INSERT INTO `commission` (`id`, `transactionid`, `employeeid`, `amount`, `date`, `paid`)
VALUES
  ('commission1','transaction1','employee2',20.00,'2012-08-08 12:59:04',0);



# Populate table revenuesource ;
# ------------------------------------------------------------ ;


INSERT INTO `revenuesource` (`id`, `transactionid`, `chargeamount`, `type`)
VALUES
  ('sale1','transaction1',532.49,'Sale'),
  ('rental1','transaction1',25.5,'Rental'),
  ('rental2','transaction1',14.75,'Rental'),
  ('rental3','transaction1',28.5,'Rental'),
  ('fee1','transaction1',35,'Fee'),
  ('printorder1','transaction1',2.8,'PrintOrder');



# Populate table sale ;
# ------------------------------------------------------------ ;


INSERT INTO `sale` (`id`, `productid`, `quantity`)
VALUES
  ('sale1','forSale1',1);


# Populate table forrent ;
# ------------------------------------------------------------ ;

INSERT INTO `forrent` (`id`, `timesrented`, `currentrentalid`)
VALUES
  ('forRent1',5,NULL),
  ('forRent2',2,NULL),
  ('forRent3',0,NULL),
  ('forRent4',0,NULL);


# Populate table rental ;
# ------------------------------------------------------------ ;


INSERT INTO `rental` (`id`, `forrentid`, `dateout`, `datein`, `datedue`, `workordernumber`, `remindersent`)
VALUES
  ('rental1','forRent1','2013-03-10 12:53:23',NULL,'2013-03-19 20:00:00',NULL,0),
  ('rental2','forRent2','2013-03-03 10:45:02',NULL,'2013-03-12 20:00:00',NULL,0),
  ('rental3','forRent4','2013-02-01 08:30:42',NULL,'2013-02-08 20:00:00',NULL,0);


# Now that both rental and forrent tables are created, and since they reference each other, add FK to rental for forrentid. ;
# ------------------------------------------------------------ ;


UPDATE forrent SET currentrentalid = 'rental1' WHERE id = 'forRent1';
UPDATE forrent SET currentrentalid = 'rental2' WHERE id = 'forRent2';


# Populate table fee ;
# ------------------------------------------------------------ ;


INSERT INTO `fee` (`id`, `rentalid`, `amount`, `waived`)
VALUES
  ('fee1','rental2',35,0);


# Populate table generalledger ;
# ------------------------------------------------------------ ;


INSERT INTO `generalledger` (`id`, `account`, `balance`, `type`)
VALUES
  ('glAccount1','Cash',25000.00,'DR'),
  ('glAccount2','Commission Payable',3300.00,'CR'),
  ('glAccount3','Commission Expense',0.00,'DR'),
  ('glAccount4','Revenue',125000.00,'CR'),
  ('glAccount5','Tax Payable',15000.00,'CR');


# Populate table picture ;
# ------------------------------------------------------------ ;


INSERT INTO `picture` (`id`, `customerid`, `caption`, `picdata`)
VALUES
  ('picture1','customer1','Awesome Sunset','iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAADBklEQVR4Xm2TW2gcZRiGn/+fybqb1WxwY5O0ioraEw2CSFtCoaeLemGJYEQKFQVr1Zb1QNFeKO1NbS9KQCMqRBQPKYIYSrR6oaVUaiOeErKtiFVITYybTXZn9jA7O+dxMnsjwQde+H7ejwe+i5+VnN5P+qvTdw/mxx956/rl3Jczl3Lnpj57aHj81B0Dxx8nyQoE/+HzE6v39g8cOJld378JVYXQhQAIVbAsFq9+O3np3PtHB1/Tzq8U8M3Q+kO7Hnv5TZm5UdJcgMButV4AbggkINmLq5edr8+cevLBY/OjABLg01cyu3Y+/NQbsq0uqfwKdgV8C6NYwa4a8Yylw1KeNsVO7N57cOSjI6ktAHL5rq39e04qKUelNgeOBZ5Js1xh8uIfkaAGbhO8KIEN+nWSKVKb79tx4vh2VDH6Unrnvv0HLsh2H4QAKUEE5Cf+olo22Lb7dgQqRsWjXvPo7WqPz7LNwB/94sOtsifb1S9pgNXAXprHnp2E0i+o9Sk2rJlD6FMEhR+4cHachd+nwC6DZXADlrKqI7tFTUjRQ7MK3hLN4j8Yms6t93SzcV0GAqDpUvy7hlbS2NGXhsI0yCwkbiEtldWqZ0cb2gz4VW5KtFEwJNQdkAoEIQDtisKezWvoSCXBcsGZB8XAc5qeOHs0nRvo6x4WSQUUydxSiNOocNedneATJYCQFrYPTgBegB/NYzNLT8hCrTGhl20zLk2X2zISX8ny2zUDvWTSrNmYFYtywWBxwQCnJVmsu1rJdn+UxRJXrhSNMQyPWNIwWdvp09PZRaWRoqBJSlUIPUlHmxrvhFHyeuOTiyWuCYDXn2HTA73ZsXWrUmsJgTAEAQgFQgG+D14UP4y7vGZNn69WB4+8zZ+CFuKdw2zflr15eEM22adI4uU4AUAIQuBG76u6+fNlrf5cboTvARRacP9PzBY2Nr8rNVxfIrulFBkUKVwEdc8PZuvuzETJ+GCybr764gjT/D+t7zz0NPe+d0ju+zinvnDmsPr8u8/KR4cO0rfcsYJ/AQsIehGEorXGAAAAAElFTkSuQmCC'),
  ('picture2','customer1','My Cat','iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAQ5SURBVHjaYs62MMmLsRDdf//VD/63337uZgCCYDVtBnNJaQYTKWk7JQmhilsv3l6wlZT5HGhqwKDMyRegJS+edefV28OsLCy/AAIAQQC+/wTK5OkDW1YtCTzn8mTZ7PGGAAAA8Pr+/gD1+v0ELCERmV4rKyj+/fdCBgMKtv7/+wE7NBZjJCMaDMrh5Arf7O9kAojl7tMPG568ZW6REGHnBWpWBOLzHOyMDOxMjBZ6UqwqtnpKDEpebmq/BFXUfv38xCD46iID78NnDJaKIoFAAyoAAojJTkFenI/tH+urDz/+ATX/5WZjZfj7g5GBi5XRX06Xg+WHHhcDv/gvBr5/ZxlE2e8wcKnwM7AYcDJYaAsLMDEw2wEEAEEAvv8EOTocjw8QAQXH1u3ZIg0O0jIcEgAF+/4AICQPWCE2LgD0+PkA793FAAT/9gD9/wEAAwsWABgcGgA/RiwCK7nbagIAQQC+/wI4NhA2s8Tk/tTr85k5FhNXGAwIkAYI/K8pKQwbDAgDAOzv+QDo/A0AHTkyAA4nKQDd6/0A+/j8ACEcDAA2KRUxAgBBAL7/BOjk7vsaBPEFbEQ0d1ddVxUVICIC/vjuAAz5xAAA8+MAHQsEABYRAwD4uggACBH3ADAm9wAK+eAABAgAAAcJBQYCAEEAvv8Ek7HYsI5lJ1djTnEXCCZAAAD99QD94scA7s71AAEdLgALCgoAC//9APbrCwAGFQEADiEFAAAFBgDf0+wAxNDq6QIAQQC+/wLv6/Ss7vEH/wgO/wAA5LkAAOK3APPauwDjwb4A27a/APkCCgAADhcACkT4AAYwBgAAEhkA6uf+AHp/ufvS5emMAojZR1ne3cqY3UNSQYCBiQloCAhz8DAw8QsxvLt2keH16VMMjH9+MHAyMDK8f/yOYfuq8wy1Ves/bTx+vQUYaxMAAoiFgZmR4+t/ZoZ/7CwM376+Y/jy4S2DoJwKg6CZM8Mfxv8MDxfNY3j69hUDh589w7k9FxhqZr59duPTFz+g5rOgVAsQAEEAvv8CCAkFKAcRGADz5+wAmpKyAIR+rPzgzMwACgH1ABAdJQAECwIAFS0oAPj+BQAK/uUASTEHEQQBCvUDAQMABQIDAAIAQQC+/wM8JRlyREMpH52iyP+BocWxxt/ue9XN4iTo3eEXHCkzAh4sLAC/xuH/srbb/yQT9QE3I/0F6vEOTtjp+acFAwP1AojBVU2mpiTQ/IeOjOhtoIvYQM4SYAFTodPqsn/8///w/+xCz79sDIyzY0xFH8+LEf7vqSn0mYGB2YKFiYUBIIAYdMWFQIrtgdgc7CdGRgYJLk4Qk8HPVGvb1cNT/69vi/zPxcxYw8XKKSvJJ9DHwMDoD5RmAakBCCAGfQlhBnZGYKpmQAABdlYGXlYWBkE2Fml1Mb5VqhKCh5kZGYNEeTgZRLg5GZABQIABAHyzVcIgx9zFAAAAAElFTkSuQmCC'),
  ('picture3','customer1','Family Portrait','iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAsTAAALEwEAmpwYAAAABGdBTUEAALGOfPtRkwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAADiElEQVR42mJkgIKgoCB2RUXFJHFxcZfv37+/B2L+Dx8+nDx//vykkydP/kpMTJSVlJQ0+fTp05spU6YchukDCCBmGENUVJTb2Nh4k6amptGPHz+Mnj59ev/9+/flEhIS/Lm5uS1ACyZ7eXklqaqqxgDV/jt48CDYEIAAYgIRKioqWlxcXHl///5llJGRYVBXV2f4+fMn/4oVK74CFWvLysoWrFq1Smzu3LkMQJex6unpNaSlpTmB9AIEENgFcnJyRpmZmfMcHBw4+fj4GJ49ewZSyMLOzv4dKCcINMAX6DUGoIsYTpw4wcDMzMzEycnJu2/fvlUAAQR2wbt37869efPmHT8/P1gR0FYGCwsLKaCLRG/cuHHs5cuXX3l4eBji4uIYzM3NGYCaGa5fv34fpBcggFhABNC5/54/f/5/8+bNDLdu3QIb8ODBgzeXLl1q5+bm/q+srPzR1NSUG6gG5FqGixcvMgDD6TZIL0AAgb3w9evX70DnckpJSTkICAgwfASCM2fONAHD4zLQK/+BtBbQWwYg1wFdxHDt2jUGYWFhOWBYrAIIIBZQ9AEDMdTExMQL6CyG169fMwCdfuDIkSMT+/v712loaCgCLZDevXs3w927d0FyDKysrAx//vzRY2FhSQAIIBZnZ+fFMTExoaDAW7JkCQMw2hgEBQU9Xr16lbB///7dampqU65cucIE1ACWA9rMcPToUQZgmvj/7du3TwABxPj////LQF/oAGmG6dOnMwDTAtgVQKczrFmz5g1QgwDIq0AL/gLDggVkkIiICMM/IADKzwEIwCEZEwEAwyCQtHO2OsNtJERGbHQp1AD8PVjitYfuRlVhZpCZH1Whx616JESySSIijA+ttpTBJ4BYYDEBDHUGYDIFmcxgaWkJ5gNTHsOTJ08YgNHIAHQu44sXLxiAAQ02HJgWQOnlJ0AAwQ348uUL2GmgeD5+/DiDrq4u2Bu/f/8Gu+Dt27c/gdHI/uvXL1AsgcWAUb4VIICYgLaygBQpKCgwuLi4MPj5+YG9sGnTJgZgVDIAAxAUaGeArnDcunXrBmCaASckkEuB+WUrQAAxA1NhNdBGHpAgyGlXr14F2wwKTJCrQAqZmJj+AeO+B8h+DYz2CJDzgWIgw28DBBALMPe9BDqJ48CBAwznzp0DGxIREQGmgekDlrQFgIYVAuP9KRB/BLkYSINcbQEQYAADKMPmAjJAPQAAAABJRU5ErkJggg==');


# Populate table print ;
# ------------------------------------------------------------ ;

INSERT INTO `print` (`id`, `price`, `size`, `type`)
VALUES
  ('print1',0.5,'4in x 6in','Small Photo');


# Populate table printorder ;
# ------------------------------------------------------------ ;


INSERT INTO `printorder` (`id`, `pictureid`, `printid`, `quantity`)
VALUES
  ('printorder1','picture1','print1',1);


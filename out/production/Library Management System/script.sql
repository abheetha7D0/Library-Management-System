#mysql
-h localhost -p 1234 -u root

DROP DATABASE IF EXISTS `Library`;
CREATE DATABASE IF NOT EXISTS `Library`;
USE `Library`;
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User`
(   Id VARCHAR(6),
    UserName VARCHAR(10) NOT NULL,
    Password VARCHAR(300) NOT NULL,
    Role VARCHAR(10)NOT NULL,
    TpNo varchar (12),
    Email VARCHAR(30),
    Address varchar (50),
    CONSTRAINT PRIMARY KEY(Id));

insert into user values('U-001','Abhee',MD5('1234'),'Librarian','0763314450','abeetha.dilushan@gmail.com','Moragalla,Beruwala');
insert into user values('U-002','Dilu',MD5('1234'),'Staff','0776269119','abheetha.dilushan@gmail.com','Moragalla,Beruwala');


DROP TABLE IF EXISTS `Author`;
CREATE TABLE IF NOT EXISTS `Author`
(   AuthorId VARCHAR(6),
    Name VARCHAR(30) NOT NULL,
    TpNo VARCHAR(10),
    Email VARCHAR(30),
    CONSTRAINT PRIMARY KEY(AuthorId));


DROP TABLE IF EXISTS `Publisher`;
CREATE TABLE IF NOT EXISTS `Publisher`
(   PublisherId VARCHAR(6),
    Name VARCHAR(30) NOT NULL,
    TpNo VARCHAR(10),
    Email VARCHAR(30),
    Adress VARCHAR(50),
    CONSTRAINT PRIMARY KEY(PublisherId));


DROP TABLE IF EXISTS `Book`;
CREATE TABLE IF NOT EXISTS `Book`
(   BookId VARCHAR(6),
    Name VARCHAR(25) NOT NULL,
    AuthorId VARCHAR(6),
    PublisherId VARCHAR(6),
    UnitPrice DOUBLE(6,2) DEFAULT 0.00,
    Copies INT(5) DEFAULT 0,
    CONSTRAINT PRIMARY KEY(BookId),
    CONSTRAINT FOREIGN KEY(AuthorId) REFERENCES `Author`(AuthorId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(PublisherId) REFERENCES `Publisher`(PublisherId) ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS `Reader`;
CREATE TABLE IF NOT EXISTS `Reader`
(
    ReaderId VARCHAR(6),
    Name VARCHAR(30) NOT NULL,
    Age Int(3),
    TpNo VARCHAR(10),
    Address VARCHAR(50),
    CONSTRAINT PRIMARY KEY(ReaderId));

DROP TABLE IF EXISTS `Attendance`;
CREATE TABLE IF NOT EXISTS `Attendance`
(
    AttendanceId VARCHAR(10),
    ReaderId VARCHAR(6),
    Date VARCHAR(10) NOT NULL,
    InTime VARCHAR(15) NOT NULL,
    OutTime VARCHAR(15) NOT NULL,
    CONSTRAINT PRIMARY KEY(AttendanceId),
    CONSTRAINT FOREIGN KEY(ReaderId) REFERENCES `Reader`(ReaderId) ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS `Supplier`;
CREATE TABLE IF NOT EXISTS `Supplier`
(
    SupplierId VARCHAR(6),
    SupName VARCHAR(15) NOT NULL,
    supType VARCHAR(12),
    TpNo VARCHAR(12),
    Email VARCHAR(30),
    Address VARCHAR(50),
    CONSTRAINT PRIMARY KEY(SupplierId));

DROP TABLE IF EXISTS `Book supply`;
CREATE TABLE IF NOT EXISTS `Book supply`
(
    SupplyId VARCHAR(6),
    SupplyDate VARCHAR(10) ,
    SupplierId VARCHAR(6),
    SupplyCost double ,
    CONSTRAINT PRIMARY KEY(SupplyId),
    CONSTRAINT FOREIGN KEY(SupplierId) REFERENCES `supplier`(SupplierId) ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS `Book Supply detail`;
CREATE TABLE IF NOT EXISTS `Book Supply detail`
(
    SupplyId VARCHAR(6),
    BookId VARCHAR(15),
    SupplyQty INT(11),
    price DECIMAL(10,2),
    CONSTRAINT PRIMARY KEY(SupplyId,BookId),
    CONSTRAINT FOREIGN KEY(SupplyId) REFERENCES `Book supply`(SupplyId) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY(BookId) REFERENCES `Book`(BookId)ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS `Book Issue`;
CREATE TABLE IF NOT EXISTS `Book Issue`
(
    IssueId VARCHAR(10),
    ReaderId VARCHAR(15),
    IssueDate VARCHAR(15) ,
    DueDate VARCHAR(15),
    BookCount int(3),
    CONSTRAINT PRIMARY KEY(IssueId),
    CONSTRAINT FOREIGN KEY(ReaderId) REFERENCES `Reader`(ReaderId) ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS `Book Issue Detail`;
CREATE TABLE IF NOT EXISTS `Book Issue Detail`
(
    IssueId VARCHAR(10),
    BookId VARCHAR(15),
    copies int(1),
    CONSTRAINT PRIMARY KEY(IssueId,BookId),
    CONSTRAINT FOREIGN KEY(IssueId) REFERENCES `Book Issue`(IssueId) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY(BookId) REFERENCES `Book`(BookId)ON DELETE CASCADE ON UPDATE CASCADE
    );

DROP TABLE IF EXISTS `Book Return`;
CREATE TABLE IF NOT EXISTS `Book Return`
(
    ReturnId VARCHAR(10),
    ReaderId VARCHAR(15),
    ReturnDate VARCHAR(15) ,
    BookCount int(3),
    CONSTRAINT PRIMARY KEY(ReturnId),
    CONSTRAINT FOREIGN KEY(ReaderId) REFERENCES `Reader`(ReaderId) ON DELETE CASCADE ON UPDATE CASCADE
    );

DROP TABLE IF EXISTS `Book Return Detail`;
CREATE TABLE IF NOT EXISTS `Book Return Detail`
(
    ReturnId VARCHAR(10),
    BookId VARCHAR(15),
    copies int(1),
    Report VARCHAR(50),
    FinePrice DOUBLE (10,2),
    CONSTRAINT PRIMARY KEY(ReturnId,BookId),
    CONSTRAINT FOREIGN KEY(ReturnId) REFERENCES `Book Return`(ReturnId) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY(BookId) REFERENCES `Book`(BookId)ON DELETE CASCADE ON UPDATE CASCADE
    );

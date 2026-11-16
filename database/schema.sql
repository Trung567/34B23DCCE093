CREATE DATABASE IF NOT EXISTS online_department_store3 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE online_department_store3;

CREATE TABLE IF NOT EXISTS tblMember3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(255) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 birthDate DATE NOT NULL,
 email VARCHAR(255) NOT NULL,
 phone VARCHAR(255) NOT NULL,
 note VARCHAR(255) NULL
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblFullName3 (
 lastName VARCHAR(255) NOT NULL,
 firstName VARCHAR(255) NOT NULL,
 tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblMember3id) REFERENCES tblMember3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblAddress3 (
 houseNumber VARCHAR(255) NOT NULL,
 building VARCHAR(255) NULL,
 street VARCHAR(255) NOT NULL,
 ward VARCHAR(255) NOT NULL,
 district VARCHAR(255) NOT NULL,
 province VARCHAR(255) NOT NULL,
 tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblMember3id) REFERENCES tblMember3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblEmployee3 (
 employeeCode VARCHAR(255) NOT NULL UNIQUE,
 position VARCHAR(255) NOT NULL,
 tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblMember3id) REFERENCES tblMember3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblCustomer3 (
 customerCode VARCHAR(255) NOT NULL UNIQUE,
 tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblMember3id) REFERENCES tblMember3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblSalesStaff3 (
 tblEmployee3tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblEmployee3tblMember3id) REFERENCES tblEmployee3(tblMember3id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblDeliveryStaff3 (
 tblEmployee3tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblEmployee3tblMember3id) REFERENCES tblEmployee3(tblMember3id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblManagementStaff3 (
 tblEmployee3tblMember3id INT PRIMARY KEY,
 FOREIGN KEY (tblEmployee3tblMember3id) REFERENCES tblEmployee3(tblMember3id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblProductCategory3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(255) NOT NULL,
 description VARCHAR(255) NULL
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblProduct3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 productCode VARCHAR(255) NOT NULL UNIQUE,
 productName VARCHAR(255) NOT NULL,
 description VARCHAR(255) NULL,
 unitOfMeasure VARCHAR(255) NOT NULL,
 stockQuantity INT NOT NULL CHECK (stockQuantity >= 0),
 sellingPrice FLOAT NOT NULL CHECK (sellingPrice >= 0),
 tblProductCategory3id INT NOT NULL,
 FOREIGN KEY (tblProductCategory3id) REFERENCES tblProductCategory3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblOrder3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 orderNumber VARCHAR(255) NOT NULL UNIQUE,
 orderedAt DATETIME NOT NULL,
 deliveryAddress VARCHAR(255) NOT NULL,
 status VARCHAR(255) NOT NULL DEFAULT 'Chờ duyệt',
 approvedAt DATETIME NULL,
 totalAmount FLOAT NOT NULL CHECK (totalAmount >= 0),
 tblCustomer3tblMember3id INT NOT NULL,
 tblSalesStaff3tblEmployee3tblMember3id INT NULL,
 FOREIGN KEY (tblCustomer3tblMember3id) REFERENCES tblCustomer3(tblMember3id),
 FOREIGN KEY (tblSalesStaff3tblEmployee3tblMember3id) REFERENCES tblSalesStaff3(tblEmployee3tblMember3id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblOrderLine3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 quantity INT NOT NULL CHECK (quantity > 0),
 unitPrice FLOAT NOT NULL CHECK (unitPrice >= 0),
 lineTotal FLOAT NOT NULL CHECK (lineTotal >= 0),
 tblOrder3id INT NOT NULL,
 tblProduct3id INT NOT NULL,
 FOREIGN KEY (tblOrder3id) REFERENCES tblOrder3(id),
 FOREIGN KEY (tblProduct3id) REFERENCES tblProduct3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblInvoice3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 invoiceNumber VARCHAR(255) NOT NULL UNIQUE,
 issuedAt DATETIME NOT NULL,
 tblOrder3id INT NOT NULL UNIQUE,
 FOREIGN KEY (tblOrder3id) REFERENCES tblOrder3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblExportNote3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 exportNumber VARCHAR(255) NOT NULL UNIQUE,
 exportedAt DATETIME NOT NULL,
 note VARCHAR(255) NULL,
 tblInvoice3id INT NOT NULL UNIQUE,
 tblDeliveryStaff3tblEmployee3tblMember3id INT NOT NULL,
 tblSalesStaff3tblEmployee3tblMember3id INT NOT NULL,
 FOREIGN KEY (tblInvoice3id) REFERENCES tblInvoice3(id),
 FOREIGN KEY (tblDeliveryStaff3tblEmployee3tblMember3id) REFERENCES tblDeliveryStaff3(tblEmployee3tblMember3id),
 FOREIGN KEY (tblSalesStaff3tblEmployee3tblMember3id) REFERENCES tblSalesStaff3(tblEmployee3tblMember3id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblSupplier3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 supplierCode VARCHAR(255) NOT NULL UNIQUE,
 supplierName VARCHAR(255) NOT NULL,
 address VARCHAR(255) NOT NULL,
 phone VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL,
 taxCode VARCHAR(255) NOT NULL,
 description VARCHAR(255) NULL
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblGoodsReceipt3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 receiptNumber VARCHAR(255) NOT NULL UNIQUE,
 receivedAt DATETIME NOT NULL,
 note VARCHAR(255) NULL,
 totalAmount FLOAT NOT NULL CHECK (totalAmount >= 0),
 tblManagementStaff3tblEmployee3tblMember3id INT NOT NULL,
 tblSupplier3id INT NOT NULL,
 FOREIGN KEY (tblManagementStaff3tblEmployee3tblMember3id) REFERENCES tblManagementStaff3(tblEmployee3tblMember3id),
 FOREIGN KEY (tblSupplier3id) REFERENCES tblSupplier3(id)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS tblGoodsReceiptLine3 (
 id INT AUTO_INCREMENT PRIMARY KEY,
 quantity INT NOT NULL CHECK (quantity > 0),
 unitCost FLOAT NOT NULL CHECK (unitCost >= 0),
 lineTotal FLOAT NOT NULL CHECK (lineTotal >= 0),
 tblProduct3id INT NOT NULL,
 tblGoodsReceipt3id INT NOT NULL,
 FOREIGN KEY (tblProduct3id) REFERENCES tblProduct3(id),
 FOREIGN KEY (tblGoodsReceipt3id) REFERENCES tblGoodsReceipt3(id)
) ENGINE=InnoDB;
UPDATE tblOrder3
SET status='Chờ duyệt',approvedAt=NULL,tblSalesStaff3tblEmployee3tblMember3id=NULL
WHERE status='Đã duyệt';
ALTER TABLE tblOrder3 MODIFY COLUMN status VARCHAR(255) NOT NULL DEFAULT 'Chờ duyệt';
SET @order_status_constraint_sql = IF(
 EXISTS(SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
 WHERE CONSTRAINT_SCHEMA=DATABASE() AND TABLE_NAME='tblOrder3'
 AND CONSTRAINT_NAME='chkOrder3Status' AND CONSTRAINT_TYPE='CHECK'),
 'SELECT 1',
 'ALTER TABLE tblOrder3 ADD CONSTRAINT chkOrder3Status CHECK (status IN (''Chờ duyệt'',''Đã xuất''))'
);
PREPARE order_status_constraint FROM @order_status_constraint_sql;
EXECUTE order_status_constraint;
DEALLOCATE PREPARE order_status_constraint;
DROP TRIGGER IF EXISTS trgOrder3CreateInvoice;
DELIMITER $$
CREATE TRIGGER trgOrder3CreateInvoice
AFTER INSERT ON tblOrder3
FOR EACH ROW
BEGIN
 INSERT INTO tblInvoice3(invoiceNumber,issuedAt,tblOrder3id)
 VALUES(CONCAT('TMP-INVOICE-',UUID()),NEW.orderedAt,NEW.id);
 UPDATE tblInvoice3
 SET invoiceNumber=CONCAT('HD',LPAD(CAST(id AS CHAR),GREATEST(4,CHAR_LENGTH(CAST(id AS CHAR))),'0'))
 WHERE tblOrder3id=NEW.id;
END$$
DELIMITER ;
START TRANSACTION;
INSERT INTO tblInvoice3(invoiceNumber,issuedAt,tblOrder3id)
SELECT CONCAT('TMP-INVOICE-',UUID()),o.orderedAt,o.id
FROM tblOrder3 o
LEFT JOIN tblInvoice3 i ON i.tblOrder3id=o.id
WHERE i.id IS NULL
ORDER BY o.id;
UPDATE tblInvoice3
SET invoiceNumber=CONCAT('HD',LPAD(CAST(id AS CHAR),GREATEST(4,CHAR_LENGTH(CAST(id AS CHAR))),'0'))
WHERE LEFT(invoiceNumber,12)='TMP-INVOICE-';
COMMIT;

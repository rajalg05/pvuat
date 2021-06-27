CREATE TABLE Address (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    addressLine1 VARCHAR(100),
    streetAddress1 VARCHAR(100),
    streetAddress2 VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(100),
    postalCode VARCHAR(50),
    country VARCHAR(50),
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP
);

CREATE TABLE KYC (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstKycId VARCHAR(20),
    firstKycType VARCHAR(20),
    secondKycId VARCHAR(20),
    secondKycType VARCHAR(20),
    firstKycFrontImagePath VARCHAR(1000),
    firstKycBackImagePath VARCHAR(1000),
	secondKycFrontImagePath VARCHAR(1000),
    secondKycBackImagePath VARCHAR(1000),
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP
);

CREATE TABLE BasicContactDetail (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    whatsappCountryCode VARCHAR(6),
    whatsappMobileNumber INT,
    email VARCHAR(255),
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP
);

ALTER TABLE Resource MODIFY COLUMN bike VARCHAR(50);
ALTER TABLE Resource MODIFY COLUMN qualification VARCHAR(50);
ALTER TABLE Resource MODIFY COLUMN excelSkills VARCHAR(50);
ALTER TABLE Resource MODIFY COLUMN stockAuditExp VARCHAR(50);
ALTER TABLE Resource MODIFY COLUMN resourceType VARCHAR(50);
ALTER TABLE Resource ADD tlNonTl varchar(50);
ALTER TABLE Resource DROP COLUMN tlNonTl;
ALTER TABLE Job MODIFY COLUMN clientName VARCHAR(50);
ALTER TABLE audit DROP COLUMN job_id;
ALTER TABLE audit DROP COLUMN address_id;
ALTER TABLE audit DROP COLUMN job_jobName;


CREATE TABLE Resource (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	contactDetailId INT,
    kycId INT,
    addressId INT,
    dateOfBirth DATE,
    bike VARCHAR(10),
    qualification VARCHAR(50),
    excelSkills VARCHAR(10),
    stockAuditExp VARCHAR(10),
    resourceType VARCHAR(10),
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP,
    FOREIGN KEY (addressId) REFERENCES Address(id),
    FOREIGN KEY (kycId) REFERENCES KYC(id),
    FOREIGN KEY (contactDetailId) REFERENCES BasicContactDetail(id)
);


CREATE TABLE Associate(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	contactDetailId INT,
    addressId INT,
    kycId INT,
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP,
    FOREIGN KEY (addressId) REFERENCES Address(id),
    FOREIGN KEY (kycId) REFERENCES KYC(id),
    FOREIGN KEY (contactDetailId) REFERENCES BasicContactDetail(id)
);


CREATE TABLE Job(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	associateId INT,
    clientName INT,
    frequencyOfAudit VARCHAR(20),
    paymentType VARCHAR(20),
    totalPayment DECIMAL(20,2),
	resourcesNeeded INT,
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP,
    FOREIGN KEY (associateId) REFERENCES Associate(id)
    )
;



CREATE TABLE Audit(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	jobId INT,
	auditStatus VARCHAR(50),
    dateOfAudit DATE,
    auditLocationAddressId INT,
    statusUpdatedBy VARCHAR(100),
    paymentReceived DECIMAL,
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP,
    FOREIGN KEY (auditLocationAddressId) REFERENCES Address(id),
    FOREIGN KEY (jobId) REFERENCES Job(id)
    );


CREATE TABLE AuditResource(
	auditId INT,
    resourceId INT,
    costIncurred INT,
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP,
    FOREIGN KEY (auditId) REFERENCES Audit(id),
    FOREIGN KEY (resourceId) REFERENCES Resource(id)
);



CREATE TABLE AuditReport(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	auditId INT,
    systemInventoryReportPath VARCHAR(1000),
    physicalReportPath VARCHAR(1000),
    varianceReportPath VARCHAR(1000),
    uploadedByResourceId INT,
    createdTs TIMESTAMP,
    updatedTs TIMESTAMP,
	FOREIGN KEY (auditId) REFERENCES Audit(id),
	FOREIGN KEY (uploadedByResourceId) REFERENCES Resource(id)

);

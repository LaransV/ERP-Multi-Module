-- ============================================================
-- NexERP PostgreSQL schema
-- Auto-converted from MSSQL "Generate Scripts" backup (2026-07-20)
-- Run order: 01_schema -> 02_data -> 03_sequences -> 04_constraints
-- ============================================================

-- ============================================================
-- NexERP PostgreSQL schema (auto-converted from MSSQL backup)
-- ============================================================

CREATE TABLE "AttendanceLogs" (
    "AttendanceId" SERIAL NOT NULL,
    "EmpId" INTEGER NOT NULL,
    "AttendanceDate" DATE NOT NULL,
    "CheckIn" VARCHAR(30),
    "CheckOut" VARCHAR(30),
    "DurationMinutes" INTEGER,
    "Status" VARCHAR(20) NOT NULL,
    "Source" VARCHAR(20),
    "Notes" VARCHAR(500),
    "CreatedAt" TIMESTAMP,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("AttendanceId")
);

CREATE TABLE "Clients" (
    "ClientId" SERIAL NOT NULL,
    "ClientName" VARCHAR(255) NOT NULL,
    "Email" VARCHAR(255),
    "Phone" VARCHAR(20),
    "GstNumber" VARCHAR(20),
    "PanNumber" VARCHAR(12),
    "Address" VARCHAR(500),
    "City" VARCHAR(100),
    "State" VARCHAR(100),
    "Pincode" VARCHAR(10),
    "PaymentTerms" VARCHAR(50),
    "IsActive" BOOLEAN,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    "AliasName" VARCHAR(50),
    "VendorCode" VARCHAR(50),
    "Website" VARCHAR(200),
    "LandlinePhone" VARCHAR(20),
    "GstnType" VARCHAR(100),
    "AddressLine2" VARCHAR(500),
    "Country" VARCHAR(100),
    "Currency" VARCHAR(20),
    "CreatedBy" INTEGER,
    "ModifiedBy" INTEGER,
    PRIMARY KEY ("ClientId")
);

CREATE TABLE "Companies" (
    "CompanyId" SERIAL NOT NULL,
    "CompanyName" VARCHAR(200) NOT NULL,
    "CompanyType" VARCHAR(20) NOT NULL,
    "ParentCompanyId" INTEGER,
    "CorporateId" INTEGER,
    "Currency" VARCHAR(10) NOT NULL,
    "Gstin" VARCHAR(20),
    "Address" VARCHAR(500),
    "Phone" VARCHAR(20),
    "Email" VARCHAR(100),
    "LogoUrl" VARCHAR(500),
    "IsActive" BOOLEAN NOT NULL,
    "CreatedAt" TIMESTAMP NOT NULL,
    PRIMARY KEY ("CompanyId")
);

CREATE TABLE "CrmActivities" (
    "ActivityId" SERIAL NOT NULL,
    "LeadId" INTEGER,
    "ClientId" INTEGER,
    "EntityName" VARCHAR(255) NOT NULL,
    "ActivityType" VARCHAR(20) NOT NULL,
    "Title" VARCHAR(255) NOT NULL,
    "Description" TEXT,
    "ScheduledAt" TIMESTAMP,
    "CompletedAt" TIMESTAMP,
    "Status" VARCHAR(20),
    "CreatedBy" VARCHAR(200),
    "CreatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("ActivityId")
);

CREATE TABLE "Departments" (
    "DeptId" SERIAL NOT NULL,
    "DeptName" VARCHAR(255) NOT NULL,
    "HeadId" INTEGER,
    "IsActive" BOOLEAN,
    "IsDeleted" BOOLEAN,
    "CreatedAt" TIMESTAMP,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("DeptId")
);

CREATE TABLE "Designations" (
    "DesigId" SERIAL NOT NULL,
    "DesigName" VARCHAR(100) NOT NULL,
    "Level" INTEGER,
    "IsActive" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("DesigId")
);

CREATE TABLE "Employees" (
    "EmpId" SERIAL NOT NULL,
    "EmpCode" VARCHAR(50) NOT NULL,
    "FirstName" VARCHAR(100) NOT NULL,
    "LastName" VARCHAR(100) NOT NULL,
    "Email" VARCHAR(255) NOT NULL,
    "Phone" VARCHAR(20) NOT NULL,
    "DeptId" INTEGER,
    "DesigId" INTEGER,
    "DateOfJoining" DATE NOT NULL,
    "DateOfBirth" DATE,
    "Gender" VARCHAR(10),
    "EmploymentType" VARCHAR(20),
    "Status" VARCHAR(20),
    "BasicSalary" NUMERIC(15,2),
    "PfNumber" VARCHAR(30),
    "EsiNumber" VARCHAR(30),
    "PanNumber" VARCHAR(12),
    "ReportingManagerId" INTEGER,
    "Address" VARCHAR(500),
    "City" VARCHAR(100),
    "State" VARCHAR(100),
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("EmpId")
);

CREATE TABLE "Followups" (
    "FollowupId" SERIAL NOT NULL,
    "LeadId" INTEGER NOT NULL,
    "ScheduledAt" TIMESTAMP NOT NULL,
    "CompletedAt" TIMESTAMP,
    "Notes" TEXT NOT NULL,
    "FollowupType" VARCHAR(20),
    "Status" VARCHAR(20),
    "CreatedBy" VARCHAR(200),
    "CreatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("FollowupId")
);

CREATE TABLE "InvoiceItems" (
    "ItemId" SERIAL NOT NULL,
    "InvoiceId" INTEGER NOT NULL,
    "ProductId" INTEGER,
    "ProductName" VARCHAR(255) NOT NULL,
    "HsnCode" VARCHAR(10),
    "Quantity" NUMERIC(15,3) NOT NULL,
    "Unit" VARCHAR(20),
    "UnitPrice" NUMERIC(15,4) NOT NULL,
    "DiscountPct" NUMERIC(5,2),
    "DiscountAmount" NUMERIC(15,2),
    "TaxableAmount" NUMERIC(15,2),
    "CgstRate" NUMERIC(5,2),
    "CgstAmount" NUMERIC(15,2),
    "SgstRate" NUMERIC(5,2),
    "SgstAmount" NUMERIC(15,2),
    "IgstRate" NUMERIC(5,2),
    "IgstAmount" NUMERIC(15,2),
    "TotalAmount" NUMERIC(15,2),
    "SortOrder" INTEGER,
    PRIMARY KEY ("ItemId")
);

CREATE TABLE "Invoices" (
    "InvoiceId" SERIAL NOT NULL,
    "InvoiceNumber" VARCHAR(50) NOT NULL,
    "ClientId" INTEGER NOT NULL,
    "InvoiceDate" DATE NOT NULL,
    "DueDate" DATE NOT NULL,
    "Status" VARCHAR(20),
    "PaymentStatus" VARCHAR(20),
    "Subtotal" NUMERIC(15,2),
    "DiscountAmount" NUMERIC(15,2),
    "TaxableAmount" NUMERIC(15,2),
    "CgstTotal" NUMERIC(15,2),
    "SgstTotal" NUMERIC(15,2),
    "IgstTotal" NUMERIC(15,2),
    "TaxTotal" NUMERIC(15,2),
    "TdsPct" NUMERIC(5,2),
    "TdsAmount" NUMERIC(15,2),
    "RoundOff" NUMERIC(5,2),
    "GrandTotal" NUMERIC(15,2),
    "PaidAmount" NUMERIC(15,2),
    "BalanceAmount" NUMERIC(15,2),
    "IsInterstate" BOOLEAN,
    "Notes" TEXT,
    "Terms" TEXT,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("InvoiceId")
);

CREATE TABLE "InvoiceSequence" (
    "SeqYear" INTEGER NOT NULL,
    "NextNum" INTEGER NOT NULL,
    PRIMARY KEY ("SeqYear")
);

CREATE TABLE "Leads" (
    "LeadId" SERIAL NOT NULL,
    "LeadName" VARCHAR(255) NOT NULL,
    "Company" VARCHAR(255),
    "Email" VARCHAR(255),
    "Phone" VARCHAR(20) NOT NULL,
    "Source" VARCHAR(100),
    "Status" VARCHAR(30),
    "Priority" VARCHAR(10),
    "AssignedToId" INTEGER,
    "ExpectedValue" NUMERIC(15,2),
    "ExpectedCloseDate" DATE,
    "Notes" TEXT,
    "RejectedReason" VARCHAR(500),
    "NextFollowupDate" DATE,
    "LastActivityAt" TIMESTAMP,
    "ConvertedToClientId" INTEGER,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("LeadId")
);

CREATE TABLE "Modules" (
    "ModuleId" SERIAL NOT NULL,
    "ModuleCode" VARCHAR(30) NOT NULL,
    "ModuleName" VARCHAR(100) NOT NULL,
    "Icon" VARCHAR(50),
    "SortOrder" INTEGER,
    "IsActive" BOOLEAN,
    PRIMARY KEY ("ModuleId")
);

CREATE TABLE "PayrollRuns" (
    "PayrollId" SERIAL NOT NULL,
    "EmpId" INTEGER NOT NULL,
    "Month" INTEGER NOT NULL,
    "Year" INTEGER NOT NULL,
    "DaysWorked" INTEGER,
    "LopDays" NUMERIC(5,2),
    "GrossSalary" NUMERIC(15,2),
    "TotalDeductions" NUMERIC(15,2),
    "NetSalary" NUMERIC(15,2),
    "PfEmployee" NUMERIC(15,2),
    "PfEmployer" NUMERIC(15,2),
    "EsiEmployee" NUMERIC(15,2),
    "EsiEmployer" NUMERIC(15,2),
    "TdsAmount" NUMERIC(15,2),
    "ProfessionalTax" NUMERIC(15,2),
    "Status" VARCHAR(20),
    "ProcessedAt" TIMESTAMP,
    "PaidAt" TIMESTAMP,
    "CreatedAt" TIMESTAMP,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("PayrollId")
);

CREATE TABLE "PoSequence" (
    "SeqYear" INTEGER NOT NULL,
    "NextNum" INTEGER NOT NULL,
    PRIMARY KEY ("SeqYear")
);

CREATE TABLE "Products" (
    "ProductId" SERIAL NOT NULL,
    "ProductName" VARCHAR(255) NOT NULL,
    "ProductCode" VARCHAR(50),
    "HsnCode" VARCHAR(10),
    "ProductType" VARCHAR(20),
    "Unit" VARCHAR(20),
    "TaxRate" NUMERIC(5,2),
    "PurchasePrice" NUMERIC(15,2),
    "SalePrice" NUMERIC(15,2),
    "CategoryName" VARCHAR(100),
    "GroupName" VARCHAR(100),
    "IsActive" BOOLEAN,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("ProductId")
);

CREATE TABLE "PurchaseOrderItems" (
    "PoItemId" SERIAL NOT NULL,
    "PoId" INTEGER NOT NULL,
    "ProductId" INTEGER,
    "ProductName" VARCHAR(255) NOT NULL,
    "Quantity" NUMERIC(15,3) NOT NULL,
    "UnitPrice" NUMERIC(15,4) NOT NULL,
    "TotalAmount" NUMERIC(15,2) NOT NULL,
    PRIMARY KEY ("PoItemId")
);

CREATE TABLE "PurchaseOrders" (
    "PoId" SERIAL NOT NULL,
    "PoNumber" VARCHAR(50) NOT NULL,
    "VendorId" INTEGER NOT NULL,
    "PoDate" DATE NOT NULL,
    "ExpectedDate" DATE,
    "Status" VARCHAR(20),
    "TotalAmount" NUMERIC(15,2),
    "Notes" TEXT,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "IsDeleted" BOOLEAN,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("PoId")
);

CREATE TABLE "RoleEntitlements" (
    "EntitlementId" SERIAL NOT NULL,
    "RoleId" INTEGER NOT NULL,
    "ScreenId" INTEGER NOT NULL,
    "CanCreate" BOOLEAN,
    "CanRead" BOOLEAN,
    "CanUpdate" BOOLEAN,
    "CanDelete" BOOLEAN,
    PRIMARY KEY ("EntitlementId")
);

CREATE TABLE "Roles" (
    "RoleId" SERIAL NOT NULL,
    "RoleName" VARCHAR(100) NOT NULL,
    "RoleDescription" VARCHAR(255),
    "IsActive" BOOLEAN,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    PRIMARY KEY ("RoleId")
);

CREATE TABLE "Screens" (
    "ScreenId" SERIAL NOT NULL,
    "ScreenCode" VARCHAR(50) NOT NULL,
    "ScreenName" VARCHAR(100) NOT NULL,
    "ModuleId" INTEGER NOT NULL,
    "Route" VARCHAR(200),
    "SortOrder" INTEGER,
    PRIMARY KEY ("ScreenId")
);

CREATE TABLE "StockLevels" (
    "StockId" SERIAL NOT NULL,
    "ProductId" INTEGER NOT NULL,
    "WarehouseName" VARCHAR(100),
    "OpeningStock" NUMERIC(15,3),
    "CurrentStock" NUMERIC(15,3),
    "ReservedStock" NUMERIC(15,3),
    "ReorderLevel" NUMERIC(15,3),
    "LastUpdated" TIMESTAMP,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("StockId")
);

CREATE TABLE "StockMovements" (
    "MovementId" SERIAL NOT NULL,
    "ProductId" INTEGER NOT NULL,
    "MovementType" VARCHAR(20) NOT NULL,
    "Quantity" NUMERIC(15,3) NOT NULL,
    "ReferenceType" VARCHAR(50),
    "ReferenceNumber" VARCHAR(100),
    "WarehouseFrom" VARCHAR(100),
    "WarehouseTo" VARCHAR(100),
    "Notes" VARCHAR(500),
    "CreatedBy" VARCHAR(200),
    "CreatedAt" TIMESTAMP,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("MovementId")
);

CREATE TABLE "UserEntitlements" (
    "UserEntitlementId" SERIAL NOT NULL,
    "UserId" INTEGER NOT NULL,
    "ScreenId" INTEGER NOT NULL,
    "CanCreate" BOOLEAN NOT NULL,
    "CanRead" BOOLEAN NOT NULL,
    "CanUpdate" BOOLEAN NOT NULL,
    "CanDelete" BOOLEAN NOT NULL,
    "CreatedAt" TIMESTAMP NOT NULL,
    PRIMARY KEY ("UserEntitlementId")
);

CREATE TABLE "Users" (
    "UserId" SERIAL NOT NULL,
    "Username" VARCHAR(100) NOT NULL,
    "Email" VARCHAR(255) NOT NULL,
    "FullName" VARCHAR(200) NOT NULL,
    "Phone" VARCHAR(20),
    "PasswordHash" VARCHAR(255) NOT NULL,
    "RoleId" INTEGER NOT NULL,
    "IsActive" BOOLEAN,
    "CreatedAt" TIMESTAMP,
    "UpdatedAt" TIMESTAMP,
    "LastLogin" TIMESTAMP,
    "CorporateId" INTEGER,
    "CompanyId" INTEGER,
    PRIMARY KEY ("UserId")
);

CREATE TABLE "Vendors" (
    "VendorId" SERIAL NOT NULL,
    "VendorName" VARCHAR(255) NOT NULL,
    "Email" VARCHAR(255),
    "Phone" VARCHAR(20),
    "GstNumber" VARCHAR(20),
    "Address" VARCHAR(500),
    "City" VARCHAR(100),
    "State" VARCHAR(100),
    "IsActive" BOOLEAN,
    "CreatedAt" TIMESTAMP,
    "CompanyId" INTEGER NOT NULL,
    PRIMARY KEY ("VendorId")
);

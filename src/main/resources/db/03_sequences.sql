-- ============================================================
-- Reset SERIAL sequences so new rows continue after imported IDs
-- ============================================================

-- Reset SERIAL sequences after explicit-id inserts
SELECT setval(pg_get_serial_sequence('"AttendanceLogs"', 'AttendanceId'), COALESCE((SELECT MAX("AttendanceId") FROM "AttendanceLogs"), 1), true);
SELECT setval(pg_get_serial_sequence('"Clients"', 'ClientId'), COALESCE((SELECT MAX("ClientId") FROM "Clients"), 1), true);
SELECT setval(pg_get_serial_sequence('"Companies"', 'CompanyId'), COALESCE((SELECT MAX("CompanyId") FROM "Companies"), 1), true);
SELECT setval(pg_get_serial_sequence('"CrmActivities"', 'ActivityId'), COALESCE((SELECT MAX("ActivityId") FROM "CrmActivities"), 1), true);
SELECT setval(pg_get_serial_sequence('"Departments"', 'DeptId'), COALESCE((SELECT MAX("DeptId") FROM "Departments"), 1), true);
SELECT setval(pg_get_serial_sequence('"Designations"', 'DesigId'), COALESCE((SELECT MAX("DesigId") FROM "Designations"), 1), true);
SELECT setval(pg_get_serial_sequence('"Employees"', 'EmpId'), COALESCE((SELECT MAX("EmpId") FROM "Employees"), 1), true);
SELECT setval(pg_get_serial_sequence('"Followups"', 'FollowupId'), COALESCE((SELECT MAX("FollowupId") FROM "Followups"), 1), true);
SELECT setval(pg_get_serial_sequence('"InvoiceItems"', 'ItemId'), COALESCE((SELECT MAX("ItemId") FROM "InvoiceItems"), 1), true);
SELECT setval(pg_get_serial_sequence('"Invoices"', 'InvoiceId'), COALESCE((SELECT MAX("InvoiceId") FROM "Invoices"), 1), true);
SELECT setval(pg_get_serial_sequence('"Leads"', 'LeadId'), COALESCE((SELECT MAX("LeadId") FROM "Leads"), 1), true);
SELECT setval(pg_get_serial_sequence('"Modules"', 'ModuleId'), COALESCE((SELECT MAX("ModuleId") FROM "Modules"), 1), true);
SELECT setval(pg_get_serial_sequence('"PayrollRuns"', 'PayrollId'), COALESCE((SELECT MAX("PayrollId") FROM "PayrollRuns"), 1), true);
SELECT setval(pg_get_serial_sequence('"Products"', 'ProductId'), COALESCE((SELECT MAX("ProductId") FROM "Products"), 1), true);
SELECT setval(pg_get_serial_sequence('"PurchaseOrderItems"', 'PoItemId'), COALESCE((SELECT MAX("PoItemId") FROM "PurchaseOrderItems"), 1), true);
SELECT setval(pg_get_serial_sequence('"PurchaseOrders"', 'PoId'), COALESCE((SELECT MAX("PoId") FROM "PurchaseOrders"), 1), true);
SELECT setval(pg_get_serial_sequence('"RoleEntitlements"', 'EntitlementId'), COALESCE((SELECT MAX("EntitlementId") FROM "RoleEntitlements"), 1), true);
SELECT setval(pg_get_serial_sequence('"Roles"', 'RoleId'), COALESCE((SELECT MAX("RoleId") FROM "Roles"), 1), true);
SELECT setval(pg_get_serial_sequence('"Screens"', 'ScreenId'), COALESCE((SELECT MAX("ScreenId") FROM "Screens"), 1), true);
SELECT setval(pg_get_serial_sequence('"StockLevels"', 'StockId'), COALESCE((SELECT MAX("StockId") FROM "StockLevels"), 1), true);
SELECT setval(pg_get_serial_sequence('"StockMovements"', 'MovementId'), COALESCE((SELECT MAX("MovementId") FROM "StockMovements"), 1), true);
SELECT setval(pg_get_serial_sequence('"UserEntitlements"', 'UserEntitlementId'), COALESCE((SELECT MAX("UserEntitlementId") FROM "UserEntitlements"), 1), true);
SELECT setval(pg_get_serial_sequence('"Users"', 'UserId'), COALESCE((SELECT MAX("UserId") FROM "Users"), 1), true);
SELECT setval(pg_get_serial_sequence('"Vendors"', 'VendorId'), COALESCE((SELECT MAX("VendorId") FROM "Vendors"), 1), true);

# NexERP — MSSQL → PostgreSQL migration: COMPLETE
(based on BackEnd.zip + NexERP_full_backup.sql, both dated 2026-07-20)

All 6 modules converted. Every real stored procedure was read from your
actual database backup and verified against it — not guessed, not copied
from an older/stale version.

## What changed, module by module

| Module | Procs converted | Repositories rewritten | SQL classes |
|---|---|---|---|
| Finance | 23 | 4 | ClientSql, ProductSql, InvoiceSql, DashboardSql |
| Admin | 25 | 7 | CompanySql, UserSql, RoleSql, ScreenSql, EntitlementSql, UserEntitlementSql |
| Auth | 5 | 3 | AuthSql |
| CRM | 21 | 4 | LeadSql, FollowupSql, ActivitySql, CRMDashboardSql |
| HR | 20 | 6 | EmployeeSql, DeptSql, DesigSql, AttendanceSql, PayrollSql, HRMSDashboardSql |
| Inventory | 14 | 4 | StockSql, MovementSql, PurchaseOrderSql |
| **Total** | **108** | **28** | **24 files** |

`config/SpExecutor.java` (the old MSSQL stored-proc caller) is deleted —
nothing references it anymore. Every repository now uses
`config/JdbcExecutor.java` instead.

## Infrastructure changes
- `pom.xml`: `mssql-jdbc` → `postgresql`
- `application.yml`: datasource URL/driver/Hibernate dialect → PostgreSQL,
  pointing at the `nexerp` database (matches the restore package)
- `@Transactional(rollbackFor = Exception.class)` added to every service
  method that does more than one write, so a failure partway through rolls
  back everything instead of leaving inconsistent data

## Real bugs found in your MSSQL procs (fixed, not silently — flagging here)

1. **`usp_Finance_InsertInvoice`** never set `Invoices.CompanyId`, even
   though the column is `NOT NULL`. Untested in your live system because no
   Invoices exist yet in the seed data. Fixed using the `CompanyId` your
   `InvoiceService` already provides.
2. **`usp_HR_InsertEmployee`** — same pattern: never set `Employees.CompanyId`
   (`NOT NULL`). Fixed the same way.

## Things preserved exactly, even though they look unusual (not "fixed" — flagging instead)

- `usp_HR_GetEmployees` / `GetEmployeeById` / `GetPayrollById` don't filter
  by `CompanyId` in your real procs (unlike nearly every other list/get proc
  in the system). Left as-is — this is a scoping question, not a hard
  failure, and changing it would be a functional change I haven't confirmed
  with you.
- `usp_HR_GetDepartments` has no `@CompanyId` parameter at all — returns
  departments company-wide. Left as-is for the same reason.
- The `PoSequence` table exists in your schema but no stored procedure
  actually reads or writes it — `usp_Inv_InsertPO` generates PO numbers with
  `NEWID()` instead. Reproduced the same random-based numbering in Java
  (`PurchaseOrderRepository`) rather than "fixing" it to use the apparently
  unused sequence table.

## Multi-step operations now wrapped in one transaction

These used to be single stored-proc calls (atomic by virtue of running
inside SQL Server); now they're multiple SQL statements from Java, so each
is wrapped in `@Transactional` so a mid-way failure rolls back cleanly:

- `InvoiceService.createInvoice/updateInvoice/deleteInvoice`
- `RoleService.deleteRole` (removes RoleEntitlements, then the Role)
- `EntitlementService.saveEntitlements`, `UserEntitlementService.saveUserEntitlements`
- `LeadService.convertLead` (reads Lead → inserts Client → marks Lead WON)
- `PurchaseOrderService.createPO/deletePO`
- `StockService.adjustStock` (upserts StockLevels, then logs StockMovements)

## MSSQL → Postgres constructs translated

- T-SQL `MERGE` (upsert) → `INSERT ... ON CONFLICT DO UPDATE`
  (RoleEntitlements, UserEntitlements, StockLevels)
- `IF EXISTS(...)/ELSE` returning two different result shapes
  (`usp_Auth_GetUserPermissions`) → existence check + two branch queries,
  picked in Java
- `NEWID()` → Java `UUID.randomUUID()` (avoids needing a Postgres UUID
  extension)
- String parameters bound into `DATE`/`TIMESTAMP` columns (e.g.
  `ExpectedCloseDate`, `ScheduledAt`) → `NULLIF(:Param, '')::date` /
  `::timestamp` (Postgres won't auto-cast a bound VARCHAR parameter into a
  date/timestamp column the way MSSQL implicitly would)
- `OFFSET/FETCH` pagination — kept as-is (ANSI standard, works in Postgres
  unchanged), just `[dbo].[Table]` → `"Table"` and `@Param` → `:Param`

## Database
Already delivered separately: `NexERP-postgres-restore.zip` — all 26 tables
+ all 167 data rows, statically verified (no dangling FKs, no missing
unique columns, no NOT NULL violations). Run the 4 files in order per
`RESTORE_INSTRUCTIONS.md` inside it.

## Recommended next steps (not part of this conversion, just practical advice)
1. Restore the Postgres database using the other zip.
2. Point `application.yml` at it (already done in this zip) and start the
   backend — watch startup logs for any Spring bean wiring errors.
3. Smoke-test each module's endpoints in order: Auth (login) → Admin →
   Finance → CRM → HR → Inventory.
4. Pay closest attention to Finance Invoices, HR Employees/Payroll, and
   Inventory Purchase Orders first — these had the most complex multi-step
   conversions.

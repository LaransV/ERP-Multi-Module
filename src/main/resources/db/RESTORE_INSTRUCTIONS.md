# NexERP — PostgreSQL restore instructions

This gives you a brand-new PostgreSQL database with the **exact same tables
and the exact same data** as your MSSQL `NexERP` database (converted from
`NexERP_full_backup.sql`, generated 2026-07-20).

Verified automatically before packaging:
- Every foreign key's table/column exists ✅
- Every unique constraint's column exists ✅
- Every `INSERT` targets a table that exists in the schema ✅
- No row violates a `NOT NULL` column ✅

## What's in this folder

Run these **in this exact order** — each depends on the one before it:

| File | What it does |
|---|---|
| `01_schema.sql` | Creates all 26 tables (empty) |
| `02_data.sql` | Inserts all 167 rows of your real data |
| `03_sequences.sql` | Fixes auto-increment counters so new rows after this get correct IDs (e.g. next Client after ID 7 will be ID 8, not restart at 1) |
| `04_constraints.sql` | Adds unique constraints, foreign keys, and column defaults — done **last** on purpose, so historical data can't fail these checks during load |

## Step 1 — Install PostgreSQL (skip if you already have it)

Windows: download from https://www.postgresql.org/download/windows/ and run
the installer (remember the password you set for the `postgres` superuser).

## Step 2 — Create the database and user

Open **pgAdmin** (installed with PostgreSQL) or `psql`, connect as the
`postgres` superuser, and run:

```sql
CREATE ROLE nexerp_user WITH LOGIN PASSWORD 'NexErp@123';
CREATE DATABASE nexerp OWNER nexerp_user;
```

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO nexerp_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO nexerp_user;
GRANT USAGE ON SCHEMA public TO nexerp_user;

## Step 3 — Run the 4 files, in order

**Using `psql` (Command Prompt / terminal):**

```bash
psql -U nexerp_user -d nexerp -h localhost -f 01_schema.sql
psql -U nexerp_user -d nexerp -h localhost -f 02_data.sql
psql -U nexerp_user -d nexerp -h localhost -f 03_sequences.sql
psql -U nexerp_user -d nexerp -h localhost -f 04_constraints.sql
```

It'll ask for the password (`NexErp@123`) each time.

**Using pgAdmin (GUI, if you prefer clicking):**
1. Connect to your server → right-click the `nexerp` database → **Query Tool**
2. Open `01_schema.sql` in the Query Tool → click **Execute (▶)**
3. Repeat for `02_data.sql`, `03_sequences.sql`, `04_constraints.sql` — **same order**

## Step 4 — Verify

```sql
SELECT COUNT(*) FROM "Clients";      -- should return 7
SELECT COUNT(*) FROM "Users";
SELECT COUNT(*) FROM "Products";
```

If all 4 scripts ran without red error text, your Postgres `nexerp` database
now has the exact same tables + data as your MSSQL `NexERP` database.

## Step 5 — Point the backend at it

In `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/nexerp
    username: nexerp_user
    password: NexErp@123
    driver-class-name: org.postgresql.Driver
```

## ⚠️ Important — stored procedures NOT included here

This restore package covers **tables + data only**. Your 108 stored
procedures (`usp_Admin_*`, `usp_Auth_*`, `usp_CRM_*`, `usp_Finance_*`,
`usp_HR_*`, `usp_Inv_*`) do **not** exist in PostgreSQL yet — those aren't
SQL you run once, they get converted into Java repository code (see the
main chat response for current progress: Finance module done, other 5
modules in progress).

Until a module's Java code is converted, that module's API endpoints will
error out against this new Postgres database — the DB itself is ready and
correct, but the backend code that talks to it isn't finished for every
module yet.

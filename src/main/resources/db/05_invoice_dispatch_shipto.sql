-- ============================================================
-- Migration: Invoice Basic Info / Dispatch From / Ship To
-- Run this ONCE against your existing "nexerp" database.
-- Safe to re-run (uses IF NOT EXISTS guards where possible).
-- ============================================================

-- ── 1. Reusable Dispatch-From addresses (master data) ──────
CREATE TABLE IF NOT EXISTS "DispatchAddresses" (
    "DispatchAddressId" SERIAL NOT NULL,
    "Name"          VARCHAR(200) NOT NULL,
    "AddressLine1"  VARCHAR(500),
    "AddressLine2"  VARCHAR(500),
    "DispatchState" VARCHAR(100),
    "Pincode"       VARCHAR(10),
    "IsActive"      BOOLEAN DEFAULT TRUE,
    "CreatedAt"     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "CompanyId"     INTEGER NOT NULL,
    PRIMARY KEY ("DispatchAddressId")
);

-- ── 2. Reusable Ship-To addresses (master data) ────────────
CREATE TABLE IF NOT EXISTS "ShipToAddresses" (
    "ShipToAddressId" SERIAL NOT NULL,
    "Name"           VARCHAR(200) NOT NULL,
    "AddressLine1"   VARCHAR(500),
    "AddressLine2"   VARCHAR(500),
    "ShippingState"  VARCHAR(100),
    "Pincode"        VARCHAR(10),
    "Gstin"          VARCHAR(20),
    "IsActive"       BOOLEAN DEFAULT TRUE,
    "CreatedAt"      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "CompanyId"      INTEGER NOT NULL,
    PRIMARY KEY ("ShipToAddressId")
);

-- ── 3. New columns on Invoices for Basic Info tab + address links ──
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "SupplierRefNo"     VARCHAR(100);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "EWayBillNo"        VARCHAR(50);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "GenerateEWayBill"  BOOLEAN DEFAULT FALSE;
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "DcNo"              VARCHAR(50);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "DcDate"            DATE;
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "SelectDc"          VARCHAR(20) DEFAULT 'MANUAL';
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "VehicleNo"         VARCHAR(30);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "LrNo"              VARCHAR(50);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "Distance"          NUMERIC(10,2);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "TransporterId"     VARCHAR(50);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "DelThrough"        VARCHAR(100);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "DelDestn"          VARCHAR(100);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "OrderNo"           VARCHAR(50);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "OrderDate"         DATE;
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "SoNo"              VARCHAR(50);
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "Currency"          VARCHAR(10) DEFAULT 'INR';
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "DispatchAddressId" INTEGER;
ALTER TABLE "Invoices" ADD COLUMN IF NOT EXISTS "ShipToAddressId"   INTEGER;

-- ── 4. Foreign keys (guarded so re-running doesn't error) ──
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'FK_Invoices_DispatchAddressId') THEN
        ALTER TABLE "Invoices" ADD CONSTRAINT "FK_Invoices_DispatchAddressId"
            FOREIGN KEY ("DispatchAddressId") REFERENCES "DispatchAddresses"("DispatchAddressId");
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'FK_Invoices_ShipToAddressId') THEN
        ALTER TABLE "Invoices" ADD CONSTRAINT "FK_Invoices_ShipToAddressId"
            FOREIGN KEY ("ShipToAddressId") REFERENCES "ShipToAddresses"("ShipToAddressId");
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'FK_DispatchAddresses_CompanyId') THEN
        ALTER TABLE "DispatchAddresses" ADD CONSTRAINT "FK_DispatchAddresses_CompanyId"
            FOREIGN KEY ("CompanyId") REFERENCES "Companies"("CompanyId");
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'FK_ShipToAddresses_CompanyId') THEN
        ALTER TABLE "ShipToAddresses" ADD CONSTRAINT "FK_ShipToAddresses_CompanyId"
            FOREIGN KEY ("CompanyId") REFERENCES "Companies"("CompanyId");
    END IF;
END $$;

-- ── 5. Grant access to the app user (same as the rest of your schema) ──
GRANT ALL PRIVILEGES ON "DispatchAddresses" TO nexerp_user;
GRANT ALL PRIVILEGES ON "ShipToAddresses" TO nexerp_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO nexerp_user;

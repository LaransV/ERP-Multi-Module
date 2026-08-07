package com.nexerp.modules.finance.sql;

/**
 * PostgreSQL equivalents of the old MSSQL usp_Finance_*Invoice* stored procedures.
 *
 * NOTE on invoice numbering: the original usp_Finance_InsertInvoice did three
 * things inside one proc (ensure a sequence row exists for the year, atomically
 * grab-and-increment the next number, then insert the invoice). PostgreSQL has
 * no equivalent "single call" construct for this, so it's split into three SQL
 * statements here (ENSURE_SEQUENCE_YEAR, NEXT_INVOICE_NUMBER, INSERT_INVOICE)
 * run together from InvoiceRepository.insertInvoice(). Because this method is
 * only ever called from InvoiceService, which is @Transactional, all three
 * statements commit or roll back together.
 */
public final class InvoiceSql {

    private InvoiceSql() {}

    // ── Invoice numbering ──────────────────────────
    public static final String ENSURE_SEQUENCE_YEAR = """
        INSERT INTO invoice_sequence (seq_year, next_num) VALUES (:Year, 1)
        ON CONFLICT (seq_year) DO NOTHING
        """;

    /** Atomically grabs the next number for the year and advances the counter. */
    public static final String NEXT_INVOICE_NUMBER = """
        UPDATE invoice_sequence
        SET next_num = next_num + 1
        WHERE seq_year = :Year
        RETURNING next_num - 1
        """;

    // ── Invoices ────────────────────────────────────
    public static final String GET_INVOICES = """
        SELECT i.invoice_id, i.invoice_number, i.client_id, c.client_name,
               i.invoice_date, i.due_date, i.status, i.payment_status,
               i.grand_total, i.paid_amount, i.balance_amount, i.created_at,
               COUNT(*) OVER() AS "TotalCount"
        FROM invoices i
        INNER JOIN clients c ON c.client_id = i.client_id
        WHERE i.company_id = :CompanyId
          AND (:Status = '' OR i.status = :Status)
          AND (:ClientId = 0 OR i.client_id = :ClientId)
        ORDER BY i.created_at DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_INVOICE_BY_ID = """
        SELECT i.*, c.client_name,
               da.name AS "DispatchName", da.address_line1 AS "DispatchAddressLine1",
               da.address_line2 AS "DispatchAddressLine2", da.dispatch_state AS "DispatchStateName",
               da.pincode AS "DispatchPincode",
               sa.name AS "ShipToName", sa.address_line1 AS "ShipToAddressLine1",
               sa.address_line2 AS "ShipToAddressLine2", sa.shipping_state AS "ShipToStateName",
               sa.pincode AS "ShipToPincode", sa.gstin AS "ShipToGstin"
        FROM invoices i
        INNER JOIN clients c ON c.client_id = i.client_id
        LEFT JOIN dispatch_addresses da ON da.dispatch_address_id = i.dispatch_address_id
        LEFT JOIN ship_to_addresses sa ON sa.ship_to_address_id = i.ship_to_address_id
        WHERE i.invoice_id = :InvoiceId AND i.company_id = :CompanyId
        """;

    public static final String GET_INVOICE_ITEMS = """
        SELECT item_id, invoice_id, product_id, product_name, hsn_code,
               quantity, unit, unit_price,
               discount_pct, discount_amount, taxable_amount,
               cgst_rate, cgst_amount, sgst_rate, sgst_amount,
               igst_rate, igst_amount, total_amount, sort_order
        FROM invoice_items
        WHERE invoice_id = :InvoiceId
        ORDER BY sort_order
        """;

    public static final String INSERT_INVOICE = """
        INSERT INTO invoices
            (invoice_number, client_id, invoice_date, due_date,
             status, payment_status,
             subtotal, discount_amount, taxable_amount,
             cgst_total, sgst_total, igst_total, tax_total,
             tds_pct, tds_amount, round_off, grand_total, balance_amount,
             is_interstate, notes, terms, company_id,
             supplier_ref_no, e_way_bill_no, generate_e_way_bill,
             dc_no, dc_date, select_dc,
             vehicle_no, lr_no, distance, transporter_id,
             del_through, del_destn, order_no, order_date, so_no, currency,
             dispatch_address_id, ship_to_address_id)
        VALUES
            (:InvoiceNumber, :ClientId, :InvoiceDate::date, :DueDate::date,
             'DRAFT', 'UNPAID',
             :Subtotal, :DiscountAmount, :TaxableAmount,
             :CgstTotal, :SgstTotal, :IgstTotal, :TaxTotal,
             :TdsPct, :TdsAmount, :RoundOff, :GrandTotal, :GrandTotal,
             :IsInterstate, NULLIF(:Notes, ''), NULLIF(:Terms, ''), :CompanyId,
             NULLIF(:SupplierRefNo, ''), NULLIF(:EWayBillNo, ''), :GenerateEWayBill,
             NULLIF(:DcNo, ''), NULLIF(:DcDate, '')::date, COALESCE(NULLIF(:SelectDc, ''), 'MANUAL'),
             NULLIF(:VehicleNo, ''), NULLIF(:LrNo, ''), :Distance, NULLIF(:TransporterId, ''),
             NULLIF(:DelThrough, ''), NULLIF(:DelDestn, ''), NULLIF(:OrderNo, ''),
             NULLIF(:OrderDate, '')::date, NULLIF(:SoNo, ''), COALESCE(NULLIF(:Currency, ''), 'INR'),
             NULLIF(:DispatchAddressId, 0), NULLIF(:ShipToAddressId, 0))
        RETURNING invoice_id
        """;

    public static final String INSERT_INVOICE_ITEM = """
        INSERT INTO invoice_items
            (invoice_id, product_id, product_name, hsn_code,
             quantity, unit, unit_price,
             discount_pct, discount_amount, taxable_amount,
             cgst_rate, cgst_amount, sgst_rate, sgst_amount,
             igst_rate, igst_amount, total_amount, sort_order)
        VALUES
            (:InvoiceId, NULLIF(:ProductId, 0), :ProductName, NULLIF(:HsnCode, ''),
             :Quantity, NULLIF(:Unit, ''), :UnitPrice,
             :DiscountPct, :DiscountAmount, :TaxableAmount,
             :CgstRate, :CgstAmount, :SgstRate, :SgstAmount,
             :IgstRate, :IgstAmount, :TotalAmount, :SortOrder)
        """;

    public static final String UPDATE_INVOICE = """
        UPDATE invoices
        SET client_id = :ClientId, invoice_date = :InvoiceDate::date, due_date = :DueDate::date,
            subtotal = :Subtotal, discount_amount = :DiscountAmount, taxable_amount = :TaxableAmount,
            cgst_total = :CgstTotal, sgst_total = :SgstTotal, igst_total = :IgstTotal, tax_total = :TaxTotal,
            tds_pct = :TdsPct, tds_amount = :TdsAmount, round_off = :RoundOff, grand_total = :GrandTotal,
            balance_amount = :GrandTotal - paid_amount, is_interstate = :IsInterstate,
            notes = :Notes, terms = :Terms,
            supplier_ref_no = NULLIF(:SupplierRefNo, ''), e_way_bill_no = NULLIF(:EWayBillNo, ''),
            generate_e_way_bill = :GenerateEWayBill,
            dc_no = NULLIF(:DcNo, ''), dc_date = NULLIF(:DcDate, '')::date,
            select_dc = COALESCE(NULLIF(:SelectDc, ''), 'MANUAL'),
            vehicle_no = NULLIF(:VehicleNo, ''), lr_no = NULLIF(:LrNo, ''),
            distance = :Distance, transporter_id = NULLIF(:TransporterId, ''),
            del_through = NULLIF(:DelThrough, ''), del_destn = NULLIF(:DelDestn, ''),
            order_no = NULLIF(:OrderNo, ''), order_date = NULLIF(:OrderDate, '')::date,
            so_no = NULLIF(:SoNo, ''), currency = COALESCE(NULLIF(:Currency, ''), 'INR'),
            dispatch_address_id = NULLIF(:DispatchAddressId, 0), ship_to_address_id = NULLIF(:ShipToAddressId, 0)
        WHERE invoice_id = :InvoiceId AND company_id = :CompanyId
        """;

    public static final String DELETE_INVOICE_ITEMS = """
        DELETE FROM invoice_items WHERE invoice_id = :InvoiceId
        """;

    public static final String UPDATE_INVOICE_STATUS = """
        UPDATE invoices SET status = :Status
        WHERE invoice_id = :InvoiceId AND company_id = :CompanyId
        """;

    public static final String DELETE_INVOICE = """
        DELETE FROM invoices WHERE invoice_id = :InvoiceId AND company_id = :CompanyId
        """;
}

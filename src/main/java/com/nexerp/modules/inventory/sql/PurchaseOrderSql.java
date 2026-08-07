package com.nexerp.modules.inventory.sql;

/**
 * PostgreSQL equivalents of the usp_Inv_*PO* (Purchase Order) stored procedures.
 *
 * NOTE: the PO number is generated using NEWID() in the original proc, not
 * from the PoSequence table (that table exists in the schema but no proc
 * actually reads/writes it - it appears to be a dead/unused leftover).
 * Reproduced the same random-based numbering, generated in Java
 * (PurchaseOrderRepository) instead of relying on a Postgres UUID extension.
 */
public final class PurchaseOrderSql {

    private PurchaseOrderSql() {}

    public static final String GET_PURCHASE_ORDERS = """
        SELECT po.*, v.vendor_name, COUNT(*) OVER() AS "TotalCount"
        FROM purchase_orders po
        INNER JOIN vendors v ON v.vendor_id = po.vendor_id
        WHERE po.company_id = :CompanyId
          AND (:Status = '' OR po.status = :Status)
        ORDER BY po.created_at DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_PO_BY_ID = """
        SELECT po.*, v.vendor_name
        FROM purchase_orders po
        INNER JOIN vendors v ON v.vendor_id = po.vendor_id
        WHERE po.po_id = :PoId AND po.company_id = :CompanyId
        """;

    public static final String GET_PO_ITEMS = """
        SELECT po_item_id, po_id, product_id, product_name, quantity, unit_price, total_amount
        FROM purchase_order_items
        WHERE po_id = :PoId
        ORDER BY po_item_id
        """;

    public static final String INSERT_PO = """
        INSERT INTO purchase_orders (po_number, vendor_id, po_date, expected_date, status, total_amount, notes, company_id)
        VALUES (:PoNumber, :VendorId, :PoDate::date, NULLIF(:ExpectedDate, '')::date, 'DRAFT', :TotalAmount, :Notes, :CompanyId)
        RETURNING po_id
        """;

    public static final String INSERT_PO_ITEM = """
        INSERT INTO purchase_order_items (po_id, product_id, product_name, quantity, unit_price, total_amount)
        VALUES (:PoId, NULLIF(:ProductId, 0), :ProductName, :Quantity, :UnitPrice, :TotalAmount)
        """;

    public static final String APPROVE_PO = """
        UPDATE purchase_orders SET status = 'APPROVED' WHERE po_id = :PoId AND company_id = :CompanyId
        """;

    public static final String DELETE_PO_ITEMS = """
        DELETE FROM purchase_order_items WHERE po_id = :PoId
        """;

    public static final String DELETE_PO = """
        DELETE FROM purchase_orders WHERE po_id = :PoId AND company_id = :CompanyId
        """;
}

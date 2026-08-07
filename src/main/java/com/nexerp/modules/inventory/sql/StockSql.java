package com.nexerp.modules.inventory.sql;

/**
 * PostgreSQL equivalents of the usp_Inv_*Stock* stored procedures.
 * usp_Inv_AdjustStock used IF EXISTS/UPDATE/ELSE/INSERT (upsert) plus a
 * movement-log insert; the upsert is now INSERT ... ON CONFLICT DO UPDATE
 * against the existing UQ_StockLevels_ProductId constraint, and the
 * NEWID()-based reference number is generated in Java (StockRepository)
 * instead of relying on a Postgres UUID extension.
 */
public final class StockSql {

    private StockSql() {}

    public static final String GET_STOCK = """
        SELECT sl.*, p.product_name, p.product_code, p.hsn_code, p.unit, p.category_name, p.purchase_price,
               (sl.current_stock * p.purchase_price) AS "StockValue",
               COUNT(*) OVER() AS "TotalCount"
        FROM stock_levels sl
        INNER JOIN products p ON p.product_id = sl.product_id
        WHERE sl.company_id = :CompanyId AND p.is_active = true
          AND (:Filter = '' OR
               (:Filter = 'LOW' AND sl.current_stock <= sl.reorder_level AND sl.current_stock > 0) OR
               (:Filter = 'OUT' AND sl.current_stock = 0))
        ORDER BY p.product_name
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_STOCK_BY_ID = """
        SELECT sl.*, p.product_name, p.product_code, p.hsn_code, p.unit, p.category_name, p.purchase_price,
               (sl.current_stock * p.purchase_price) AS "StockValue"
        FROM stock_levels sl
        INNER JOIN products p ON p.product_id = sl.product_id
        WHERE sl.stock_id = :StockId AND sl.company_id = :CompanyId
        """;

    public static final String UPSERT_STOCK_LEVEL = """
        INSERT INTO stock_levels (product_id, opening_stock, current_stock, reserved_stock, reorder_level, company_id, last_updated)
        VALUES (:ProductId, :Quantity, :Quantity, 0, 0, :CompanyId, now())
        ON CONFLICT (product_id) DO UPDATE
        SET current_stock = CASE :Type
                WHEN 'IN'         THEN stock_levels.current_stock + :Quantity
                WHEN 'OUT'        THEN stock_levels.current_stock - :Quantity
                WHEN 'ADJUSTMENT' THEN :Quantity
                ELSE stock_levels.current_stock END,
            last_updated = now()
        """;

    public static final String INSERT_STOCK_MOVEMENT = """
        INSERT INTO stock_movements (product_id, movement_type, quantity, reference_type, reference_number, notes, company_id)
        VALUES (:ProductId, :Type, :Quantity, 'MANUAL', :RefNum, :Notes, :CompanyId)
        """;

    public static final String GET_LOW_STOCK_ITEMS = """
        SELECT sl.*, p.product_name, p.product_code, p.hsn_code, p.unit, p.category_name, p.purchase_price,
               (sl.current_stock * p.purchase_price) AS "StockValue"
        FROM stock_levels sl
        INNER JOIN products p ON p.product_id = sl.product_id
        WHERE sl.company_id = :CompanyId AND p.is_active = true
          AND sl.current_stock <= sl.reorder_level
        ORDER BY sl.current_stock ASC
        LIMIT :Limit
        """;
}

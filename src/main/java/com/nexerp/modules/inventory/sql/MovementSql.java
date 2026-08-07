package com.nexerp.modules.inventory.sql;

/** PostgreSQL equivalents of the usp_Inv_*Movement* / usp_Inv_GetDashboardSummary procs. */
public final class MovementSql {

    private MovementSql() {}

    public static final String GET_MOVEMENTS = """
        SELECT sm.*, p.product_name, COUNT(*) OVER() AS "TotalCount"
        FROM stock_movements sm
        INNER JOIN products p ON p.product_id = sm.product_id
        WHERE sm.company_id = :CompanyId
          AND (:ProductId = 0 OR sm.product_id = :ProductId)
        ORDER BY sm.created_at DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_RECENT_MOVEMENTS = """
        SELECT sm.*, p.product_name
        FROM stock_movements sm
        INNER JOIN products p ON p.product_id = sm.product_id
        WHERE sm.company_id = :CompanyId
        ORDER BY sm.created_at DESC
        LIMIT :Limit
        """;

    public static final String GET_DASHBOARD_SUMMARY = """
        SELECT
            COUNT(DISTINCT sl.product_id) AS "TotalItems",
            COALESCE(SUM(sl.current_stock * p.purchase_price), 0) AS "TotalStockValue",
            COUNT(CASE WHEN sl.current_stock <= sl.reorder_level AND sl.current_stock > 0 THEN 1 END) AS "LowStockCount",
            COUNT(CASE WHEN sl.current_stock = 0 THEN 1 END) AS "OutOfStockCount"
        FROM stock_levels sl
        INNER JOIN products p ON p.product_id = sl.product_id
        WHERE sl.company_id = :CompanyId AND p.is_active = true
        """;
}

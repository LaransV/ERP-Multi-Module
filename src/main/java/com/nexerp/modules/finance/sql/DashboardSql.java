package com.nexerp.modules.finance.sql;

/**
 * PostgreSQL equivalents of the old MSSQL usp_Finance_GetDashboardSummary /
 * usp_Finance_GetMonthlyRevenue stored procedures.
 */
public final class DashboardSql {

    private DashboardSql() {}

    public static final String GET_DASHBOARD_SUMMARY = """
        SELECT
            COUNT(*) AS "TotalInvoices",
            COALESCE(SUM(CASE WHEN payment_status = 'PAID' THEN grand_total ELSE 0 END), 0) AS "TotalRevenue",
            COUNT(CASE WHEN status = 'SENT' AND due_date >= CURRENT_DATE THEN 1 END) AS "DueCount",
            COALESCE(SUM(CASE WHEN status = 'SENT' AND due_date >= CURRENT_DATE THEN balance_amount ELSE 0 END), 0) AS "DueAmount",
            COUNT(CASE WHEN status = 'OVERDUE' THEN 1 END) AS "OverdueCount",
            COALESCE(SUM(CASE WHEN status = 'OVERDUE' THEN balance_amount ELSE 0 END), 0) AS "OverdueAmount",
            COUNT(CASE WHEN payment_status = 'PAID' THEN 1 END) AS "PaidCount",
            COALESCE(SUM(CASE WHEN payment_status = 'PAID' THEN grand_total ELSE 0 END), 0) AS paid_amount
        FROM invoices
        WHERE company_id = :CompanyId
        """;

    public static final String GET_MONTHLY_REVENUE = """
        SELECT
            TO_CHAR(invoice_date, 'YYYY-MM') AS month,
            SUM(grand_total) AS "Invoiced",
            SUM(paid_amount) AS "Collected"
        FROM invoices
        WHERE company_id = :CompanyId
          AND invoice_date >= (date_trunc('month', CURRENT_DATE) - INTERVAL '11 months')
        GROUP BY TO_CHAR(invoice_date, 'YYYY-MM')
        ORDER BY month
        """;
}

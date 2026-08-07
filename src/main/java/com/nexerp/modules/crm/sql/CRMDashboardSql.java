package com.nexerp.modules.crm.sql;

/** PostgreSQL equivalents of usp_CRM_GetDashboardSummary / usp_CRM_GetStatusWise. */
public final class CRMDashboardSql {

    private CRMDashboardSql() {}

    public static final String GET_DASHBOARD_SUMMARY = """
        SELECT
            COUNT(*) AS "TotalLeads",
            COUNT(CASE WHEN status = 'NEW' THEN 1 END) AS "NewLeads",
            COUNT(CASE WHEN status = 'WON' THEN 1 END) AS "WonLeads",
            COUNT(CASE WHEN status = 'LOST' THEN 1 END) AS "LostLeads",
            CASE WHEN COUNT(*) > 0
                 THEN CAST(COUNT(CASE WHEN status = 'WON' THEN 1 END) * 100.0 / COUNT(*) AS NUMERIC(5,2))
                 ELSE 0 END AS "ConversionRate",
            COALESCE(SUM(CASE WHEN status NOT IN ('WON','LOST') THEN expected_value ELSE 0 END), 0) AS "TotalPipelineValue"
        FROM leads WHERE company_id = :CompanyId
        """;

    public static final String GET_STATUS_WISE = """
        SELECT status, COUNT(*) AS "Count", COALESCE(SUM(expected_value), 0) AS "Value"
        FROM leads WHERE company_id = :CompanyId
        GROUP BY status
        """;
}

package com.nexerp.modules.hr.sql;

/** PostgreSQL equivalents of usp_HR_GetDashboardSummary / usp_HR_GetDeptWiseCount. */
public final class HRMSDashboardSql {

    private HRMSDashboardSql() {}

    public static final String GET_DASHBOARD_SUMMARY = """
        SELECT
            COUNT(*) AS "TotalEmployees",
            COUNT(CASE WHEN status = 'ACTIVE' THEN 1 END) AS "ActiveEmployees",
            COUNT(CASE WHEN status = 'ON_NOTICE' THEN 1 END) AS "OnNotice",
            0 AS "TodayPresent", 0 AS "TodayAbsent",
            COUNT(CASE WHEN EXTRACT(MONTH FROM date_of_joining) = EXTRACT(MONTH FROM CURRENT_DATE)
                        AND EXTRACT(YEAR FROM date_of_joining) = EXTRACT(YEAR FROM CURRENT_DATE)
                       THEN 1 END) AS "NewJoiningThisMonth",
            0 AS "SeparationsThisMonth"
        FROM employees WHERE company_id = :CompanyId
        """;

    public static final String GET_DEPT_WISE_COUNT = """
        SELECT d.dept_name, COUNT(e.emp_id) AS "Count"
        FROM departments d
        LEFT JOIN employees e ON e.dept_id = d.dept_id AND e.status = 'ACTIVE' AND e.company_id = :CompanyId
        WHERE d.company_id = :CompanyId
        GROUP BY d.dept_name
        ORDER BY "Count" DESC
        """;
}

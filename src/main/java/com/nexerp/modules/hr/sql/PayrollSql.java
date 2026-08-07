package com.nexerp.modules.hr.sql;

/** PostgreSQL equivalents of the usp_HR_*Payroll* stored procedures. */
public final class PayrollSql {

    private PayrollSql() {}

    private static final String PAYROLL_COLUMNS = """
            p.payroll_id, p.emp_id,
            e.first_name || ' ' || e.last_name AS "EmpName",
            e.emp_code,
            p.month, p.year, p.days_worked, p.lop_days,
            p.gross_salary, p.total_deductions, p.net_salary,
            p.pf_employee, p.pf_employer,
            p.esi_employee, p.esi_employer,
            p.tds_amount, p.professional_tax,
            p.status, p.processed_at, p.paid_at
        """;

    public static final String GET_PAYROLL = """
        SELECT """ + PAYROLL_COLUMNS + """
            , COUNT(*) OVER() AS "TotalCount"
        FROM payroll_runs p
        JOIN employees e ON e.emp_id = p.emp_id
        WHERE (:Month = 0 OR p.month = :Month)
          AND (:Year = 0 OR p.year = :Year)
        ORDER BY p.year DESC, p.month DESC, e.first_name
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_PAYROLL_BY_ID = """
        SELECT """ + PAYROLL_COLUMNS + """
        FROM payroll_runs p
        JOIN employees e ON e.emp_id = p.emp_id
        WHERE p.payroll_id = :PayrollId
        """;

    /** Inserts payroll drafts for active employees not yet processed for
     * this month/year - single INSERT...SELECT, exactly as the original proc. */
    public static final String PROCESS_PAYROLL = """
        INSERT INTO payroll_runs
            (emp_id, month, year, days_worked, lop_days, gross_salary,
             total_deductions, net_salary, pf_employee, pf_employer, esi_employee, esi_employer,
             tds_amount, professional_tax, status, company_id)
        SELECT
            e.emp_id, :Month, :Year,
            26 AS days_worked, 0 AS lop_days,
            e.basic_salary AS gross_salary,
            ROUND(e.basic_salary * 0.12, 2) AS total_deductions,
            ROUND(e.basic_salary * 0.88, 2) AS net_salary,
            ROUND(e.basic_salary * 0.12, 2) AS pf_employee,
            ROUND(e.basic_salary * 0.12, 2) AS pf_employer,
            0, 0, 0, 0,
            'PROCESSED', :CompanyId
        FROM employees e
        WHERE e.company_id = :CompanyId AND e.status = 'ACTIVE'
          AND NOT EXISTS (
              SELECT 1 FROM payroll_runs p2
              WHERE p2.emp_id = e.emp_id AND p2.month = :Month AND p2.year = :Year AND p2.company_id = :CompanyId
          )
        """;

    public static final String MARK_PAYROLL_PAID = """
        UPDATE payroll_runs SET status = 'PAID', paid_at = now()
        WHERE payroll_id = :PayrollId AND company_id = :CompanyId
        """;
}

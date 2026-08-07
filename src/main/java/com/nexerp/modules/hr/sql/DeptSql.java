package com.nexerp.modules.hr.sql;

/** PostgreSQL equivalents of the usp_HR_*Department* stored procedures. */
public final class DeptSql {

    private DeptSql() {}

    public static final String GET_DEPARTMENTS = """
        SELECT d.dept_id,
               d.dept_name,
               COALESCE(e.first_name || ' ' || e.last_name, '') AS "HeadName",
               (SELECT COUNT(*) FROM employees
                WHERE dept_id = d.dept_id AND is_deleted = false) AS "EmployeeCount"
        FROM departments d
        LEFT JOIN employees e ON e.emp_id = d.head_id
        WHERE d.is_deleted = false AND d.is_active = true
        ORDER BY d.dept_name
        """;

    public static final String INSERT_DEPARTMENT = """
        INSERT INTO departments (dept_name, head_id, company_id)
        VALUES (:DeptName, NULLIF(:HeadId, 0), :CompanyId)
        RETURNING dept_id
        """;

    public static final String DELETE_DEPARTMENT = """
        DELETE FROM departments WHERE dept_id = :DeptId AND company_id = :CompanyId
        """;
}

package com.nexerp.modules.hr.sql;

/**
 * PostgreSQL equivalents of the usp_HR_*Employee* stored procedures.
 *
 * NOTE (bug found + fixed): the original usp_HR_InsertEmployee never set
 * Employees.CompanyId even though the column is NOT NULL - same latent bug
 * pattern found earlier in Finance's InsertInvoice. Fixed here using the
 * CompanyId EmployeeService already puts in params for insert/update/delete.
 *
 * NOTE (kept as-is, not a hard failure): usp_HR_GetEmployees / GetEmployeeById
 * do NOT filter by CompanyId in the original proc (unlike almost every other
 * list/get proc in this system) - preserved exactly as written since this is
 * a scoping question, not a bug that breaks anything, and changing it would
 * be a functional change I haven't confirmed with you.
 */
public final class EmployeeSql {

    private EmployeeSql() {}

    private static final String EMP_COLUMNS = """
            e.emp_id, e.emp_code,
            e.first_name, e.last_name,
            e.first_name || ' ' || e.last_name AS full_name,
            e.email, e.phone,
            e.dept_id, d.dept_name,
            e.desig_id, des.desig_name,
            e.date_of_joining, e.date_of_birth,
            e.gender, e.employment_type, e.status,
            e.basic_salary, e.pf_number, e.esi_number, e.pan_number,
            e.reporting_manager_id,
            COALESCE(rm.first_name || ' ' || rm.last_name, '') AS "ReportingManagerName",
            e.address, e.city, e.state, e.created_at
        """;

    public static final String GET_EMPLOYEES = """
        SELECT """ + EMP_COLUMNS + """
            , COUNT(*) OVER() AS "TotalCount"
        FROM employees e
        LEFT JOIN departments d ON d.dept_id = e.dept_id
        LEFT JOIN designations des ON des.desig_id = e.desig_id
        LEFT JOIN employees rm ON rm.emp_id = e.reporting_manager_id
        WHERE e.is_deleted = false
          AND (:Status = '' OR e.status = :Status)
          AND (:Search = ''
               OR e.first_name || ' ' || e.last_name ILIKE '%' || :Search || '%'
               OR e.email ILIKE '%' || :Search || '%'
               OR e.emp_code ILIKE '%' || :Search || '%')
        ORDER BY e.first_name
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_EMPLOYEE_BY_ID = """
        SELECT """ + EMP_COLUMNS + """
        FROM employees e
        LEFT JOIN departments d ON d.dept_id = e.dept_id
        LEFT JOIN designations des ON des.desig_id = e.desig_id
        LEFT JOIN employees rm ON rm.emp_id = e.reporting_manager_id
        WHERE e.emp_id = :EmpId AND e.is_deleted = false
        """;

    public static final String INSERT_EMPLOYEE = """
        INSERT INTO employees
            (emp_code, first_name, last_name, email, phone,
             dept_id, desig_id, date_of_joining, date_of_birth,
             gender, employment_type, basic_salary,
             pf_number, esi_number, pan_number,
             reporting_manager_id, address, city, state, status, company_id)
        VALUES
            (:EmpCode, :FirstName, :LastName, :Email, :Phone,
             NULLIF(:DeptId, 0), NULLIF(:DesigId, 0),
             :DateOfJoining::date,
             NULLIF(:DateOfBirth, '')::date,
             NULLIF(:Gender, ''), :EmploymentType, :BasicSalary,
             NULLIF(:PfNumber, ''), NULLIF(:EsiNumber, ''), NULLIF(:PanNumber, ''),
             NULLIF(:ReportingManagerId, 0),
             NULLIF(:Address, ''), NULLIF(:City, ''), NULLIF(:State, ''),
             COALESCE(NULLIF(:Status, ''), 'ACTIVE'), :CompanyId)
        RETURNING emp_id
        """;

    public static final String UPDATE_EMPLOYEE = """
        UPDATE employees
        SET first_name = :FirstName,
            last_name = :LastName,
            email = :Email,
            phone = :Phone,
            dept_id = NULLIF(:DeptId, 0),
            desig_id = NULLIF(:DesigId, 0),
            date_of_joining = :DateOfJoining::date,
            date_of_birth = NULLIF(:DateOfBirth, '')::date,
            gender = NULLIF(:Gender, ''),
            employment_type = :EmploymentType,
            basic_salary = :BasicSalary,
            pf_number = NULLIF(:PfNumber, ''),
            esi_number = NULLIF(:EsiNumber, ''),
            pan_number = NULLIF(:PanNumber, ''),
            reporting_manager_id = NULLIF(:ReportingManagerId, 0),
            address = NULLIF(:Address, ''),
            city = NULLIF(:City, ''),
            state = NULLIF(:State, ''),
            status = COALESCE(NULLIF(:Status, ''), status),
            updated_at = now()
        WHERE emp_id = :EmpId
        """;

    public static final String DELETE_EMPLOYEE = """
        UPDATE employees SET status = 'INACTIVE'
        WHERE emp_id = :EmpId AND company_id = :CompanyId
        """;
}

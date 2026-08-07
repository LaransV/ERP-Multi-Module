package com.nexerp.modules.hr.sql;

/**
 * PostgreSQL equivalents of the usp_HR_*Attendance* stored procedures.
 * FromDate/ToDate arrive as Strings (possibly empty) but AttendanceDate is
 * DATE - NULLIF(...,'')::date handles both the empty check and the explicit
 * cast Postgres requires for a bound VARCHAR parameter.
 */
public final class AttendanceSql {

    private AttendanceSql() {}

    public static final String GET_ATTENDANCE = """
        SELECT a.attendance_id, a.emp_id,
               e.first_name || ' ' || e.last_name AS "EmpName",
               e.emp_code,
               a.attendance_date, a.check_in, a.check_out,
               a.duration_minutes, a.status, a.source, a.notes,
               COUNT(*) OVER() AS "TotalCount"
        FROM attendance_logs a
        JOIN employees e ON e.emp_id = a.emp_id
        WHERE (:EmpId = 0 OR a.emp_id = :EmpId)
          AND (:FromDate = '' OR a.attendance_date >= NULLIF(:FromDate, '')::date)
          AND (:ToDate = '' OR a.attendance_date <= NULLIF(:ToDate, '')::date)
        ORDER BY a.attendance_date DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_ATTENDANCE_BY_ID = """
        SELECT a.attendance_id, a.emp_id,
               e.first_name || ' ' || e.last_name AS "EmpName",
               e.emp_code,
               a.attendance_date, a.check_in, a.check_out,
               a.duration_minutes, a.status, a.source, a.notes
        FROM attendance_logs a
        JOIN employees e ON e.emp_id = a.emp_id
        WHERE a.attendance_id = :AttendanceId
        """;

    public static final String INSERT_ATTENDANCE = """
        INSERT INTO attendance_logs (emp_id, attendance_date, check_in, check_out, status, source, notes, company_id)
        VALUES (:EmpId, :AttendanceDate, NULLIF(:CheckIn, ''), NULLIF(:CheckOut, ''), :Status, :Source, :Notes, :CompanyId)
        RETURNING attendance_id
        """;

    public static final String UPDATE_ATTENDANCE = """
        UPDATE attendance_logs
        SET emp_id = :EmpId, attendance_date = :AttendanceDate,
            check_in = NULLIF(:CheckIn, ''), check_out = NULLIF(:CheckOut, ''),
            status = :Status, source = :Source, notes = :Notes
        WHERE attendance_id = :AttendanceId AND company_id = :CompanyId
        """;

    public static final String DELETE_ATTENDANCE = """
        DELETE FROM attendance_logs WHERE attendance_id = :AttendanceId AND company_id = :CompanyId
        """;
}

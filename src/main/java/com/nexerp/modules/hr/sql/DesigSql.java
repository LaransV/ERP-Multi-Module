package com.nexerp.modules.hr.sql;

/** PostgreSQL equivalent of usp_HR_GetDesignations. */
public final class DesigSql {

    private DesigSql() {}

    public static final String GET_DESIGNATIONS = """
        SELECT * FROM designations WHERE company_id = :CompanyId ORDER BY level, desig_name
        """;
}

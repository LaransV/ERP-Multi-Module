package com.nexerp.modules.admin.sql;

/** PostgreSQL equivalents of the usp_Admin_*Company* stored procedures. */
public final class CompanySql {

    private CompanySql() {}

    public static final String GET_COMPANIES = """
        SELECT company_id, company_name, currency, gstin, address,
               phone, email, is_active, created_at
        FROM companies
        ORDER BY company_name
        """;

    public static final String GET_COMPANY_BY_ID = """
        SELECT company_id, company_name, currency, gstin, address,
               phone, email, is_active, created_at
        FROM companies
        WHERE company_id = :CompanyId
        """;

    public static final String INSERT_COMPANY = """
        INSERT INTO companies
            (company_name, company_type, currency, gstin, address, phone, email, is_active, created_at)
        VALUES
            (:CompanyName, 'COMPANY', :Currency, :Gstin, :Address, :Phone, :Email, :IsActive, now())
        RETURNING company_id
        """;

    public static final String UPDATE_COMPANY = """
        UPDATE companies
        SET company_name = :CompanyName,
            currency = :Currency,
            gstin = :Gstin,
            address = :Address,
            phone = :Phone,
            email = :Email,
            is_active = :IsActive
        WHERE company_id = :CompanyId
        """;

    // Soft-delete: just deactivate (matches original proc's comment/behavior)
    public static final String DELETE_COMPANY = """
        UPDATE companies SET is_active = false WHERE company_id = :CompanyId
        """;
}

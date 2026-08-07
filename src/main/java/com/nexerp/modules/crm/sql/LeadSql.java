package com.nexerp.modules.crm.sql;

/**
 * PostgreSQL equivalents of the usp_CRM_*Lead* stored procedures.
 * NOTE: ExpectedCloseDate is sent from the Java layer as a String (possibly
 * empty), but the column is DATE - NULLIF(...,'')::date handles both the
 * empty-string-to-NULL conversion and the explicit text->date cast Postgres
 * requires (it won't auto-cast a bound VARCHAR parameter into a DATE column).
 */
public final class LeadSql {

    private LeadSql() {}

    public static final String GET_LEADS = """
        SELECT l.*, u.full_name AS "AssignedToName",
               COUNT(*) OVER() AS "TotalCount"
        FROM leads l
        LEFT JOIN users u ON u.user_id = l.assigned_to_id
        WHERE l.company_id = :CompanyId
          AND (:Status = '' OR l.status = :Status)
          AND (:Search = '' OR l.lead_name ILIKE '%' || :Search || '%' OR l.company ILIKE '%' || :Search || '%')
        ORDER BY l.created_at DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_LEAD_BY_ID = """
        SELECT l.*, u.full_name AS "AssignedToName"
        FROM leads l
        LEFT JOIN users u ON u.user_id = l.assigned_to_id
        WHERE l.lead_id = :LeadId AND l.company_id = :CompanyId
        """;

    public static final String INSERT_LEAD = """
        INSERT INTO leads
            (lead_name, company, email, phone, source, status, priority,
             assigned_to_id, expected_value, expected_close_date, notes, rejected_reason, company_id)
        VALUES
            (:LeadName, :Company, :Email, :Phone, :Source, :Status, :Priority,
             NULLIF(:AssignedToId, 0), :ExpectedValue, NULLIF(:ExpectedCloseDate, '')::date,
             :Notes, :RejectedReason, :CompanyId)
        RETURNING lead_id
        """;

    public static final String UPDATE_LEAD = """
        UPDATE leads
        SET lead_name = :LeadName, company = :Company, email = :Email, phone = :Phone,
            source = :Source, status = :Status, priority = :Priority,
            assigned_to_id = NULLIF(:AssignedToId, 0),
            expected_value = :ExpectedValue, expected_close_date = NULLIF(:ExpectedCloseDate, '')::date,
            notes = :Notes, rejected_reason = :RejectedReason
        WHERE lead_id = :LeadId AND company_id = :CompanyId
        """;

    public static final String UPDATE_LEAD_STATUS = """
        UPDATE leads SET status = :Status WHERE lead_id = :LeadId AND company_id = :CompanyId
        """;

    public static final String DELETE_LEAD = """
        DELETE FROM leads WHERE lead_id = :LeadId AND company_id = :CompanyId
        """;

    // ── ConvertLeadToClient (3-step, run inside a @Transactional service method) ──
    public static final String GET_LEAD_CORE_FIELDS = """
        SELECT lead_name, email, phone FROM leads WHERE lead_id = :LeadId AND company_id = :CompanyId
        """;

    public static final String INSERT_CLIENT_FROM_LEAD = """
        INSERT INTO clients (client_name, email, phone, is_active, company_id)
        VALUES (:LeadName, COALESCE(:Email, ''), COALESCE(:Phone, ''), true, :CompanyId)
        RETURNING client_id
        """;

    public static final String MARK_LEAD_WON = """
        UPDATE leads SET status = 'WON', converted_to_client_id = :ClientId
        WHERE lead_id = :LeadId AND company_id = :CompanyId
        """;
}

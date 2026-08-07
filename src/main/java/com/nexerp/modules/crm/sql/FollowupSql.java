package com.nexerp.modules.crm.sql;

/** PostgreSQL equivalents of the usp_CRM_*Followup* stored procedures. */
public final class FollowupSql {

    private FollowupSql() {}

    public static final String GET_FOLLOWUPS = """
        SELECT f.*, l.lead_name, COUNT(*) OVER() AS "TotalCount"
        FROM followups f
        INNER JOIN leads l ON l.lead_id = f.lead_id
        WHERE f.company_id = :CompanyId
          AND (:LeadId = 0 OR f.lead_id = :LeadId)
          AND (:Status = '' OR f.status = :Status)
        ORDER BY f.scheduled_at DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_FOLLOWUP_BY_ID = """
        SELECT f.*, l.lead_name FROM followups f
        INNER JOIN leads l ON l.lead_id = f.lead_id
        WHERE f.followup_id = :FollowupId
        """;

    public static final String INSERT_FOLLOWUP = """
        INSERT INTO followups (lead_id, scheduled_at, notes, followup_type, status, company_id)
        VALUES (:LeadId, :ScheduledAt, :Notes, :FollowupType, :Status, :CompanyId)
        RETURNING followup_id
        """;

    public static final String UPDATE_FOLLOWUP = """
        UPDATE followups
        SET scheduled_at = :ScheduledAt, notes = :Notes, followup_type = :FollowupType, status = :Status
        WHERE followup_id = :FollowupId AND company_id = :CompanyId
        """;

    public static final String COMPLETE_FOLLOWUP = """
        UPDATE followups
        SET status = 'COMPLETED', completed_at = now(), notes = :Notes
        WHERE followup_id = :FollowupId AND company_id = :CompanyId
        """;

    public static final String DELETE_FOLLOWUP = """
        DELETE FROM followups WHERE followup_id = :FollowupId AND company_id = :CompanyId
        """;

    public static final String GET_RECENT_FOLLOWUPS = """
        SELECT f.*, l.lead_name
        FROM followups f
        INNER JOIN leads l ON l.lead_id = f.lead_id
        WHERE f.company_id = :CompanyId AND f.status = 'PENDING'
        ORDER BY f.scheduled_at
        LIMIT :Limit
        """;
}

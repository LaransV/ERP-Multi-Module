package com.nexerp.modules.crm.sql;

/**
 * PostgreSQL equivalents of the usp_CRM_*Activity* stored procedures.
 * ScheduledAt is sent as a String (possibly empty) but the column is
 * TIMESTAMP - NULLIF(...,'')::timestamp handles the empty-to-NULL
 * conversion and the explicit cast Postgres requires.
 */
public final class ActivitySql {

    private ActivitySql() {}

    public static final String GET_ACTIVITIES = """
        SELECT a.*, COUNT(*) OVER() AS "TotalCount"
        FROM crm_activities a
        WHERE a.company_id = :CompanyId
          AND (:LeadId = 0 OR a.lead_id = :LeadId)
        ORDER BY a.created_at DESC
        OFFSET :Offset ROWS FETCH NEXT :Size ROWS ONLY
        """;

    public static final String GET_ACTIVITY_BY_ID = """
        SELECT * FROM crm_activities WHERE activity_id = :ActivityId
        """;

    public static final String INSERT_ACTIVITY = """
        INSERT INTO crm_activities
            (lead_id, client_id, entity_name, activity_type, title, description, scheduled_at, status, company_id)
        VALUES
            (NULLIF(:LeadId, 0), NULLIF(:ClientId, 0), :EntityName, :ActivityType, :Title, :Description,
             NULLIF(:ScheduledAt, '')::timestamp, :Status, :CompanyId)
        RETURNING activity_id
        """;

    public static final String UPDATE_ACTIVITY = """
        UPDATE crm_activities
        SET title = :Title, description = :Description,
            scheduled_at = NULLIF(:ScheduledAt, '')::timestamp, status = :Status
        WHERE activity_id = :ActivityId AND company_id = :CompanyId
        """;

    public static final String DELETE_ACTIVITY = """
        DELETE FROM crm_activities WHERE activity_id = :ActivityId AND company_id = :CompanyId
        """;
}

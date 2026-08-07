package com.nexerp.modules.admin.sql;

/**
 * PostgreSQL equivalents of the usp_Admin_*Entitlement* stored procedures.
 * The original usp_Admin_InsertEntitlement used T-SQL MERGE (upsert);
 * Postgres equivalent is INSERT ... ON CONFLICT DO UPDATE, relying on the
 * existing UQ_RoleEntitlements_RoleId_ScreenId unique constraint.
 */
public final class EntitlementSql {

    private EntitlementSql() {}

    public static final String GET_ENTITLEMENTS = """
        SELECT e.entitlement_id, e.role_id, e.screen_id,
               s.screen_code, s.screen_name,
               m.module_code, m.module_name,
               e.can_create, e.can_read, e.can_update, e.can_delete
        FROM role_entitlements e
        JOIN screens s ON s.screen_id = e.screen_id
        JOIN modules m ON m.module_id = s.module_id
        WHERE e.role_id = :RoleId
        ORDER BY m.sort_order, s.sort_order
        """;

    public static final String UPSERT_ENTITLEMENT = """
        INSERT INTO role_entitlements (role_id, screen_id, can_create, can_read, can_update, can_delete)
        VALUES (:RoleId, :ScreenId, :CanCreate, :CanRead, :CanUpdate, :CanDelete)
        ON CONFLICT (role_id, screen_id) DO UPDATE
        SET can_create = :CanCreate,
            can_read = :CanRead,
            can_update = :CanUpdate,
            can_delete = :CanDelete
        """;

    public static final String DELETE_ENTITLEMENTS_BY_ROLE = """
        DELETE FROM role_entitlements WHERE role_id = :RoleId
        """;
}

package com.nexerp.modules.admin.sql;

/**
 * PostgreSQL equivalents of the usp_Admin_*UserEntitlement* stored procedures.
 * The original usp_Admin_SaveUserEntitlements used IF EXISTS/UPDATE/INSERT;
 * Postgres equivalent is INSERT ... ON CONFLICT DO UPDATE, relying on the
 * existing UQ_UserEntitlements_UserScreen unique constraint.
 */
public final class UserEntitlementSql {

    private UserEntitlementSql() {}

    /** Effective permissions: user-level override if it exists, else role-level,
     * else false. Mirrors the original COALESCE(ue.X, re.X, 0) exactly. */
    public static final String GET_USER_ENTITLEMENTS = """
        SELECT
            m.module_id,
            m.module_code,
            m.module_name,
            m.sort_order AS "ModuleSortOrder",
            s.screen_id,
            s.screen_code,
            s.screen_name,
            s.sort_order AS "ScreenSortOrder",
            ue.user_entitlement_id,
            CASE WHEN ue.user_id IS NOT NULL THEN true ELSE false END AS "IsUserOverride",
            COALESCE(ue.can_create, re.can_create, false) AS can_create,
            COALESCE(ue.can_read,   re.can_read,   false) AS can_read,
            COALESCE(ue.can_update, re.can_update, false) AS can_update,
            COALESCE(ue.can_delete, re.can_delete, false) AS can_delete
        FROM screens s
        JOIN modules m ON m.module_id = s.module_id
        LEFT JOIN role_entitlements re ON re.role_id = (SELECT role_id FROM users WHERE user_id = :UserId)
                                        AND re.screen_id = s.screen_id
        LEFT JOIN user_entitlements ue ON ue.user_id = :UserId
                                        AND ue.screen_id = s.screen_id
        WHERE m.is_active = true
        ORDER BY m.sort_order, s.sort_order
        """;

    public static final String SAVE_USER_ENTITLEMENT = """
        INSERT INTO user_entitlements (user_id, screen_id, can_create, can_read, can_update, can_delete)
        VALUES (:UserId, :ScreenId, :CanCreate, :CanRead, :CanUpdate, :CanDelete)
        ON CONFLICT (user_id, screen_id) DO UPDATE
        SET can_create = :CanCreate,
            can_read = :CanRead,
            can_update = :CanUpdate,
            can_delete = :CanDelete
        """;

    public static final String DELETE_SINGLE_USER_ENTITLEMENT = """
        DELETE FROM user_entitlements WHERE user_id = :UserId AND screen_id = :ScreenId
        """;

    public static final String DELETE_USER_ENTITLEMENTS_BY_USER = """
        DELETE FROM user_entitlements WHERE user_id = :UserId
        """;
}

package com.nexerp.modules.admin.sql;

/** PostgreSQL equivalents of the usp_Admin_GetScreens / usp_Admin_GetModules procs. */
public final class ScreenSql {

    private ScreenSql() {}

    public static final String GET_SCREENS = """
        SELECT s.screen_id, s.screen_code, s.screen_name,
               s.module_id, m.module_name, s.route, s.sort_order
        FROM screens s
        JOIN modules m ON m.module_id = s.module_id
        WHERE (:ModuleId = 0 OR s.module_id = :ModuleId)
        ORDER BY m.sort_order, s.sort_order
        """;

    public static final String GET_MODULES = """
        SELECT module_id, module_code, module_name, icon, sort_order
        FROM modules
        WHERE is_active = true
        ORDER BY sort_order
        """;
}

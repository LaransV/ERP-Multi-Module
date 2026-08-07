package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.UserEntitlementRow;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserEntitlementMapper {

    public UserEntitlementRow toUserEntitlementRow(Map<String, Object> r) {
        UserEntitlementRow row = new UserEntitlementRow();
        row.setModuleId(toInt(r.get("module_id")));
        row.setModuleCode((String) r.get("module_code"));
        row.setModuleName((String) r.get("module_name"));
        row.setModuleSortOrder(toInt(r.get("ModuleSortOrder")));
        row.setScreenId(toInt(r.get("screen_id")));
        row.setScreenCode((String) r.get("screen_code"));
        row.setScreenName((String) r.get("screen_name"));
        row.setScreenSortOrder(toInt(r.get("ScreenSortOrder")));
        Object ueid = r.get("user_entitlement_id");
        if (ueid != null) row.setUserEntitlementId(toInt(ueid));
        row.setUserOverride(toBool(r.get("IsUserOverride")));
        row.setCanCreate(toBool(r.get("can_create")));
        row.setCanRead(toBool(r.get("can_read")));
        row.setCanUpdate(toBool(r.get("can_update")));
        row.setCanDelete(toBool(r.get("can_delete")));
        return row;
    }

    /**
     * Safe Boolean converter — SQL Server JDBC can return BIT as Boolean OR Integer.
     * Boolean.TRUE.equals(Integer(1)) = false → always use this helper instead.
     */
    public boolean toBool(Object val) {
        if (val == null)            return false;
        if (val instanceof Boolean) return (Boolean) val;
        if (val instanceof Number)  return ((Number) val).intValue() != 0;
        return false;
    }

    public int  toInt(Object o)  { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long toLong(Object o) { if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
}

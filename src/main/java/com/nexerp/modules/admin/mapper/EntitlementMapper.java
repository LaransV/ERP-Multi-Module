package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.EntitlementResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EntitlementMapper {

    public EntitlementResponse toEntitlement(Map<String, Object> r) {
        EntitlementResponse e = new EntitlementResponse();
        e.setEntitlementId(r.get("entitlement_id") != null ? toInt(r.get("entitlement_id")) : null);
        e.setRoleId(toInt(r.get("role_id")));
        e.setScreenId(toInt(r.get("screen_id")));
        e.setScreenCode((String) r.get("screen_code"));
        e.setScreenName((String) r.get("screen_name"));
        e.setModuleCode((String) r.get("module_code"));
        e.setModuleName((String) r.get("module_name"));
        e.setCanCreate(Boolean.TRUE.equals(r.get("can_create")));
        e.setCanRead(Boolean.TRUE.equals(r.get("can_read")));
        e.setCanUpdate(Boolean.TRUE.equals(r.get("can_update")));
        e.setCanDelete(Boolean.TRUE.equals(r.get("can_delete")));
        return e;
    }

    public boolean toBool(Object val) {
        if (val == null)            return false;
        if (val instanceof Boolean) return (Boolean) val;
        if (val instanceof Number)  return ((Number) val).intValue() != 0;
        return false;
    }

    public int  toInt(Object o)  { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long toLong(Object o) { if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
}

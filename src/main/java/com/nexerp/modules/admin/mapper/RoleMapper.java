package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.RoleResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoleMapper {

    public RoleResponse toRole(Map<String, Object> r) {
        RoleResponse role = new RoleResponse();
        role.setRoleId(toInt(r.get("role_id")));
        role.setRoleName((String) r.get("role_name"));
        role.setRoleDescription((String) r.get("role_description"));
        role.setActive(Boolean.TRUE.equals(r.get("is_active")));
        role.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        return role;
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

package com.nexerp.modules.admin.mapper;

import com.nexerp.modules.admin.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserMapper {

    public UserResponse toUser(Map<String, Object> r) {
        UserResponse u = new UserResponse();
        u.setUserId(toInt(r.get("user_id")));
        u.setUsername((String) r.get("username"));
        u.setEmail((String) r.get("email"));
        u.setFullName((String) r.get("full_name"));
        u.setPhone((String) r.get("phone"));
        u.setRoleId(r.get("role_id") != null ? toInt(r.get("role_id")) : null);
        u.setRoleName((String) r.get("role_name"));
        u.setCompanyId(r.get("company_id") != null ? toInt(r.get("company_id")) : null);
        u.setCompanyName((String) r.get("company_name"));
        u.setActive(Boolean.TRUE.equals(r.get("is_active")));
        u.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        u.setLastLogin(r.get("last_login") != null ? r.get("last_login").toString() : null);
        return u;
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

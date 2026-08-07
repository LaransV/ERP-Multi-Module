package com.nexerp.modules.auth.mapper;

import com.nexerp.modules.auth.dto.response.PermissionDto;
import com.nexerp.modules.auth.dto.response.UserInfo;
import com.nexerp.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SessionMapper {

    public List<PermissionDto> toPermissions(List<Map<String, Object>> rows) {
        return rows.stream().map(r -> {
            PermissionDto p = new PermissionDto();
            p.setModuleCode((String) r.get("module_code"));
            p.setModuleName((String) r.get("module_name"));
            p.setScreenCode((String) r.get("screen_code"));
            p.setScreenName((String) r.get("screen_name"));
            p.setCanCreate(Boolean.TRUE.equals(r.get("can_create")));
            p.setCanRead(Boolean.TRUE.equals(r.get("can_read")));
            p.setCanUpdate(Boolean.TRUE.equals(r.get("can_update")));
            p.setCanDelete(Boolean.TRUE.equals(r.get("can_delete")));
            return p;
        }).collect(Collectors.toList());
    }

    public UserInfo toUserInfo(UserPrincipal p, List<PermissionDto> perms) {
        UserInfo info = new UserInfo();
        info.setUserId(p.getUserId());
        info.setUsername(p.getUsername());
        info.setFullName(p.getFullName());
        info.setEmail(p.getEmail());
        info.setRoleId(p.getRoleId());
        info.setRoleName(p.getRoleName());
        info.setActive(p.isActive());
        info.setCompanyId(p.getCompanyId());
        info.setPermissions(perms);
        return info;
    }
}

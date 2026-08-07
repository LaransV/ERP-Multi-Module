package com.nexerp.modules.auth.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserInfo {

        private Integer            userId;
        private String             username;
        private String             email;
        private String             fullName;
        private Integer            roleId;
        private String             roleName;
        private boolean            isActive;
        private Integer            companyId;     // active company
        private String             companyName;
        private List<PermissionDto> permissions;
    
}

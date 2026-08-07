package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class RoleResponse {

        private Integer roleId;
        private String  roleName;
        private String  roleDescription;
        private boolean isActive;
        private String  createdAt;
    
}

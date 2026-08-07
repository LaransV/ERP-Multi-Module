package com.nexerp.modules.admin.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class RoleRequest {

        @NotBlank(message = "Role name is required")
        private String  roleName;
        private String  roleDescription;
        private Boolean isActive = true;
    
}

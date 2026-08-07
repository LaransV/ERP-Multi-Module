package com.nexerp.modules.admin.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserResponse {

        private Integer userId;
        private String  username;
        private String  email;
        private String  fullName;
        private String  phone;
        private Integer roleId;
        private String  roleName;
        private Integer companyId;
        private String  companyName;
        private boolean isActive;
        private String  createdAt;
        private String  lastLogin;
    
}

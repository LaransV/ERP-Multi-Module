package com.nexerp.modules.admin.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserRequest {

        @NotBlank(message = "Username is required")
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Full name is required")
        private String fullName;

        private String  phone;

        @NotNull(message = "Role is required")
        private Integer roleId;

        @NotNull(message = "Company is required")
        private Integer companyId;

        private Boolean isActive = true;

        // Only required on create
        private String password;
    
}

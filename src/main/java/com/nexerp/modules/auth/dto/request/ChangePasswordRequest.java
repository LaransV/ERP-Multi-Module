package com.nexerp.modules.auth.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class ChangePasswordRequest {

        @NotBlank(message = "Current password is required")
        private String currentPassword;

        @NotBlank(message = "New password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String newPassword;
    
}

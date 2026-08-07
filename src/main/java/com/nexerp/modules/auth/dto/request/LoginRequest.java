package com.nexerp.modules.auth.dto.request;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class LoginRequest {

        @NotBlank(message = "Username is required")
        private String username;
        @NotBlank(message = "Password is required")
        private String password;
    
}

package com.nexerp.modules.auth.dto.response;

import javax.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class LoginResponse {

        private String   accessToken;
        private String   tokenType = "Bearer";
        private UserInfo user;
    
}

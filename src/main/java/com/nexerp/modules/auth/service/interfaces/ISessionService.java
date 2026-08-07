package com.nexerp.modules.auth.service.interfaces;

import com.nexerp.modules.auth.dto.request.LoginRequest;
import com.nexerp.modules.auth.dto.response.LoginResponse;

public interface ISessionService {
    LoginResponse login(LoginRequest req);
}

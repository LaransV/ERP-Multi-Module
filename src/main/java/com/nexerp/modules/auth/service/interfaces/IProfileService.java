package com.nexerp.modules.auth.service.interfaces;

import com.nexerp.modules.auth.dto.request.ChangePasswordRequest;
import com.nexerp.modules.auth.dto.response.UserInfo;

public interface IProfileService {
    UserInfo me();
    void changePassword(ChangePasswordRequest req);
}

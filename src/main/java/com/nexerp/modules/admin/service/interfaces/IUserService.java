package com.nexerp.modules.admin.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.admin.dto.request.UserRequest;
import com.nexerp.modules.admin.dto.response.UserResponse;

public interface IUserService {
    PagedResponse<UserResponse> listUsers(int page, int size, String search);
    UserResponse getUser(Integer id);
    UserResponse createUser(UserRequest req);
    UserResponse updateUser(Integer id, UserRequest req);
    void toggleUser(Integer id);
    void deleteUser(Integer id);
}

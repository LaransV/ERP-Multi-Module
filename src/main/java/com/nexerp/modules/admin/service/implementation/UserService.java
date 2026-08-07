package com.nexerp.modules.admin.service.implementation;

import com.nexerp.common.BusinessException;
import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.admin.dto.request.UserRequest;
import com.nexerp.modules.admin.dto.response.UserResponse;
import com.nexerp.modules.admin.mapper.UserMapper;
import com.nexerp.modules.admin.repository.interfaces.IUserRepository;
import com.nexerp.modules.admin.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository repository;
    private final UserMapper      mapper;
    private final PasswordEncoder encoder;

    @Override
    public PagedResponse<UserResponse> listUsers(int page, int size, String search) {
        log.info("listUsers START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page);
            params.put("Size", size);
            params.put("Search", search != null ? search : "");
            List<Map<String, Object>> rows = repository.getUsers(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            List<UserResponse> content = rows.stream().map(mapper::toUser).collect(Collectors.toList());
            log.info("listUsers END | total={}", total);
            return PagedResponse.of(content, page, size, total);
        } catch (Exception e) {
            log.error("listUsers | Exception occurred", e);
            throw e;
        }
    }

    @Override
    public UserResponse getUser(Integer id) {
        log.info("getUser START | userId={}", id);
        try {
            Map<String, Object> row = repository.getUserById(id);
            if (row == null) throw new ResourceNotFoundException("User", id);
            UserResponse result = mapper.toUser(row);
            log.info("getUser END | userId={}", id);
            return result;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("getUser | Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserRequest req) {
        log.info("createUser START | username={}", req.getUsername());
        try {
            if (req.getPassword() == null || req.getPassword().trim().isEmpty())
                throw new BusinessException("Password required for new user");
            Map<String, Object> params = new HashMap<>();
            params.put("Username",     req.getUsername());
            params.put("Email",        req.getEmail());
            params.put("FullName",     req.getFullName());
            params.put("Phone",        req.getPhone() != null ? req.getPhone() : "");
            params.put("RoleId",       req.getRoleId());
            params.put("CompanyId",    req.getCompanyId());
            params.put("PasswordHash", encoder.encode(req.getPassword()));
            params.put("IsActive",     req.getIsActive());
            Integer newId = repository.insertUser(params);
            UserResponse result = getUser(newId);
            log.info("createUser END | newUserId={}", newId);
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("createUser | Exception occurred | username={}", req.getUsername(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse updateUser(Integer id, UserRequest req) {
        log.info("updateUser START | userId={}", id);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("UserId",    id);
            params.put("Email",     req.getEmail());
            params.put("FullName",  req.getFullName());
            params.put("Phone",     req.getPhone() != null ? req.getPhone() : "");
            params.put("RoleId",    req.getRoleId());
            params.put("CompanyId", req.getCompanyId());
            params.put("IsActive",  req.getIsActive());
            repository.updateUser(params);
            UserResponse result = getUser(id);
            log.info("updateUser END | userId={}", id);
            return result;
        } catch (Exception e) {
            log.error("updateUser | Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleUser(Integer id) {
        log.info("toggleUser START | userId={}", id);
        try {
            repository.toggleUser(id);
            log.info("toggleUser END | userId={}", id);
        } catch (Exception e) {
            log.error("toggleUser | Exception occurred | userId={}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        log.info("deleteUser START | userId={}", id);
        try {
            repository.deleteUser(id);
            log.info("deleteUser END | userId={}", id);
        } catch (Exception e) {
            log.error("deleteUser | Exception occurred | userId={}", id, e);
            throw e;
        }
    }
}

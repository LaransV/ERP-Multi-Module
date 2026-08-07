package com.nexerp.modules.hr.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.hr.dto.request.EmployeeRequest;
import com.nexerp.modules.hr.dto.response.EmployeeResponse;
import com.nexerp.modules.hr.mapper.EmployeeMapper;
import com.nexerp.modules.hr.repository.interfaces.IEmployeeRepository;
import com.nexerp.modules.hr.service.interfaces.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository repository;
    private final EmployeeMapper      mapper;
    private final AuthService         auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<EmployeeResponse> listEmployees(int page, int size, String status, String search) {
        log.info("listEmployees START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      page);
            params.put("Size",      size);
            params.put("Status",    status != null ? status : "");
            params.put("Search",    search != null ? search : "");
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getEmployees(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toEmployee).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listEmployees | Exception occurred", e); throw e; }
    }

    @Override
    public EmployeeResponse getEmployee(Integer id) {
        log.info("getEmployee START | empId={}", id);
        try {
            Map<String, Object> r = repository.getEmployeeById(id, cid());
            if (r == null) throw new ResourceNotFoundException("Employee", id);
            return mapper.toEmployee(r);
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getEmployee | Exception occurred | empId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeResponse createEmployee(EmployeeRequest req) {
        log.info("createEmployee START");
        try {
            Map<String, Object> p = mapper.toEmployeeParams(req);
            p.put("CompanyId", cid());
            Integer id = repository.insertEmployee(p);
            return getEmployee(id);
        } catch (Exception e) { log.error("createEmployee | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest req) {
        log.info("updateEmployee START | empId={}", id);
        try {
            Map<String, Object> p = mapper.toEmployeeParams(req);
            p.put("EmpId",     id);
            p.put("CompanyId", cid());
            repository.updateEmployee(p);
            return getEmployee(id);
        } catch (Exception e) { log.error("updateEmployee | Exception occurred | empId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEmployee(Integer id) {
        log.info("deleteEmployee START | empId={}", id);
        try {
            repository.deleteEmployee(id, cid());
            log.info("deleteEmployee END | empId={}", id);
        } catch (Exception e) { log.error("deleteEmployee | Exception occurred | empId={}", id, e); throw e; }
    }
}

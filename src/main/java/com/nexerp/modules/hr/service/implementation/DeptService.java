package com.nexerp.modules.hr.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.hr.dto.request.DeptRequest;
import com.nexerp.modules.hr.dto.response.DeptResponse;
import com.nexerp.modules.hr.mapper.DeptMapper;
import com.nexerp.modules.hr.repository.interfaces.IDeptRepository;
import com.nexerp.modules.hr.service.interfaces.IDeptService;
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
public class DeptService implements IDeptService {

    private final IDeptRepository repository;
    private final DeptMapper      mapper;
    private final AuthService     auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public List<DeptResponse> listDepts() {
        log.info("listDepts START");
        try {
            return repository.getDepartments(cid()).stream().map(r -> {
                DeptResponse d = new DeptResponse();
                d.setDeptId(mapper.toInt(r.get("dept_id")));
                d.setDeptName((String) r.get("dept_name"));
                d.setHeadName((String) r.get("HeadName"));
                d.setEmployeeCount(r.get("EmployeeCount") != null ? mapper.toInt(r.get("EmployeeCount")) : 0);
                return d;
            }).collect(Collectors.toList());
        } catch (Exception e) { log.error("listDepts | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptResponse createDept(DeptRequest req) {
        log.info("createDept START | deptName={}", req.getDeptName());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("DeptName",  req.getDeptName());
            params.put("HeadId",    req.getHeadId() != null ? req.getHeadId() : 0);
            params.put("CompanyId", cid());
            final Integer id = repository.insertDepartment(params);
            return listDepts().stream().filter(d -> d.getDeptId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Dept not found: " + id));
        } catch (Exception e) { log.error("createDept | Exception occurred | deptName={}", req.getDeptName(), e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Integer id) {
        log.info("deleteDept START | deptId={}", id);
        try {
            repository.deleteDepartment(id, cid());
            log.info("deleteDept END | deptId={}", id);
        } catch (Exception e) { log.error("deleteDept | Exception occurred | deptId={}", id, e); throw e; }
    }
}

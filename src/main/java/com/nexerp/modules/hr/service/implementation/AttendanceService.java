package com.nexerp.modules.hr.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.hr.dto.request.AttendanceRequest;
import com.nexerp.modules.hr.dto.response.AttendanceResponse;
import com.nexerp.modules.hr.mapper.AttendanceMapper;
import com.nexerp.modules.hr.repository.interfaces.IAttendanceRepository;
import com.nexerp.modules.hr.service.interfaces.IAttendanceService;
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
public class AttendanceService implements IAttendanceService {

    private final IAttendanceRepository repository;
    private final AttendanceMapper      mapper;
    private final AuthService           auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<AttendanceResponse> listAttendance(int page, int size, Integer empId, String fromDate, String toDate) {
        log.info("listAttendance START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      page);
            params.put("Size",      size);
            params.put("EmpId",     empId != null ? empId : 0);
            params.put("FromDate",  fromDate != null ? fromDate : "");
            params.put("ToDate",    toDate != null ? toDate : "");
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getAttendance(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toAttendance).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listAttendance | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttendanceResponse createAttendance(AttendanceRequest req) {
        log.info("createAttendance START | empId={}", req.getEmpId());
        try {
            Map<String, Object> p = mapper.toAttendanceParams(req);
            p.put("CompanyId", cid());
            Integer id = repository.insertAttendance(p);
            return mapper.toAttendance(repository.getAttendanceById(id));
        } catch (Exception e) { log.error("createAttendance | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttendanceResponse updateAttendance(Integer id, AttendanceRequest req) {
        log.info("updateAttendance START | attendanceId={}", id);
        try {
            Map<String, Object> p = mapper.toAttendanceParams(req);
            p.put("AttendanceId", id);
            p.put("CompanyId",    cid());
            repository.updateAttendance(p);
            return mapper.toAttendance(repository.getAttendanceById(id));
        } catch (Exception e) { log.error("updateAttendance | Exception occurred | attendanceId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAttendance(Integer id) {
        log.info("deleteAttendance START | attendanceId={}", id);
        try {
            repository.deleteAttendance(id, cid());
            log.info("deleteAttendance END | attendanceId={}", id);
        } catch (Exception e) { log.error("deleteAttendance | Exception occurred | attendanceId={}", id, e); throw e; }
    }
}

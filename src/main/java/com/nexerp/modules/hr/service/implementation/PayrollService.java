package com.nexerp.modules.hr.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.hr.dto.response.PayrollResponse;
import com.nexerp.modules.hr.mapper.PayrollMapper;
import com.nexerp.modules.hr.repository.interfaces.IPayrollRepository;
import com.nexerp.modules.hr.service.interfaces.IPayrollService;
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
public class PayrollService implements IPayrollService {

    private final IPayrollRepository repository;
    private final PayrollMapper      mapper;
    private final AuthService        auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<PayrollResponse> listPayroll(int page, int size, Integer month, Integer year) {
        log.info("listPayroll START | month={}, year={}", month, year);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      page);
            params.put("Size",      size);
            params.put("Month",     month != null ? month : 0);
            params.put("Year",      year  != null ? year  : 0);
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getPayroll(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toPayroll).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listPayroll | Exception occurred", e); throw e; }
    }

    @Override
    public PayrollResponse getPayroll(Integer id) {
        log.info("getPayroll START | payrollId={}", id);
        try {
            Map<String, Object> r = repository.getPayrollById(id, cid());
            if (r == null) throw new ResourceNotFoundException("Payroll", id);
            return mapper.toPayroll(r);
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getPayroll | Exception occurred | payrollId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processPayroll(Integer month, Integer year) {
        log.info("processPayroll START | month={}, year={}", month, year);
        try {
            repository.processPayroll(month, year, cid());
            log.info("processPayroll END");
        } catch (Exception e) { log.error("processPayroll | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markPayrollPaid(Integer id) {
        log.info("markPayrollPaid START | payrollId={}", id);
        try {
            repository.markPayrollPaid(id, cid());
            log.info("markPayrollPaid END | payrollId={}", id);
        } catch (Exception e) { log.error("markPayrollPaid | Exception occurred | payrollId={}", id, e); throw e; }
    }
}

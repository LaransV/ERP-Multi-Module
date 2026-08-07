package com.nexerp.modules.hr.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.hr.dto.response.HRMSDashboardResponse;
import com.nexerp.modules.hr.mapper.HRMSDashboardMapper;
import com.nexerp.modules.hr.repository.interfaces.IHRMSDashboardRepository;
import com.nexerp.modules.hr.service.interfaces.IHRMSDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HRMSDashboardService implements IHRMSDashboardService {

    private final IHRMSDashboardRepository repository;
    private final HRMSDashboardMapper mapper;
    private final AuthService          auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public HRMSDashboardResponse getDashboard() {
        log.info("getDashboard START");
        try {
            Map<String, Object> stats = repository.getDashboardSummary(cid());
            HRMSDashboardResponse result = new HRMSDashboardResponse();
            if (stats != null) {
                result.setTotalEmployees(mapper.toLong(stats.get("TotalEmployees")));
                result.setActiveEmployees(mapper.toLong(stats.get("ActiveEmployees")));
                result.setOnNotice(mapper.toLong(stats.get("OnNotice")));
                result.setTodayPresent(mapper.toLong(stats.get("TodayPresent")));
                result.setTodayAbsent(mapper.toLong(stats.get("TodayAbsent")));
                result.setNewJoiningThisMonth(mapper.toLong(stats.get("NewJoiningThisMonth")));
                result.setSeparationsThisMonth(mapper.toLong(stats.get("SeparationsThisMonth")));
            }
            result.setDepartmentWise(repository.getDeptWiseCount(cid()).stream()
                    .map(mapper::toDeptCount)
                    .collect(Collectors.toList()));
            log.info("getDashboard END");
            return result;
        } catch (Exception e) { log.error("getDashboard | Exception occurred", e); throw e; }
    }
}

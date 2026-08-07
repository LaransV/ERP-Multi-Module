package com.nexerp.modules.finance.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.finance.dto.response.FinanceDashboardResponseDto;
import com.nexerp.modules.finance.mapper.FinanceDashboardMapper;
import com.nexerp.modules.finance.repository.interfaces.IFinanceDashboardRepository;
import com.nexerp.modules.finance.service.interfaces.IFinanceDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceDashboardService implements IFinanceDashboardService {

    private final IFinanceDashboardRepository repository;
    private final FinanceDashboardMapper dashboardMapper;
    private final AuthService       auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public FinanceDashboardResponseDto getDashboard() {
        log.info("getDashboard START");
        try {
            Integer companyId = cid();
            Map<String, Object> stats  = repository.getDashboardSummary(companyId);
            List<Map<String, Object>> monthly = repository.getMonthlyRevenue(companyId);
            return dashboardMapper.toDashboard(stats, monthly);
        } catch (Exception e) { log.error("getDashboard | Exception occurred", e); throw e; }
    }
}

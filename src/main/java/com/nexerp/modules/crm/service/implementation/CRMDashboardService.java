package com.nexerp.modules.crm.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.crm.dto.response.CRMDashboardResponse;
import com.nexerp.modules.crm.mapper.CRMDashboardMapper;
import com.nexerp.modules.crm.mapper.FollowupMapper;
import com.nexerp.modules.crm.repository.interfaces.ICRMDashboardRepository;
import com.nexerp.modules.crm.repository.interfaces.IFollowupRepository;
import com.nexerp.modules.crm.service.interfaces.ICRMDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CRMDashboardService implements ICRMDashboardService {

    private final ICRMDashboardRepository repository;
    private final IFollowupRepository  followupRepository;
    private final CRMDashboardMapper mapper;
    private final FollowupMapper       followupMapper;
    private final AuthService          auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public CRMDashboardResponse getDashboard() {
        log.info("getDashboard START");
        try {
            Map<String, Object> summary = repository.getDashboardSummary(cid());
            List<Map<String, Object>> statusRows = repository.getStatusWise(cid());
            List<Map<String, Object>> followupRows = followupRepository.getRecentFollowups(5, cid());
            CRMDashboardResponse d = new CRMDashboardResponse();
            if (summary != null) {
                d.setTotalLeads(mapper.toLong(summary.get("TotalLeads"))); d.setNewLeads(mapper.toLong(summary.get("NewLeads")));
                d.setWonLeads(mapper.toLong(summary.get("WonLeads"))); d.setLostLeads(mapper.toLong(summary.get("LostLeads")));
                Object cr = summary.get("ConversionRate");
                d.setConversionRate(cr instanceof Number ? ((Number) cr).doubleValue() : 0.0);
                d.setTotalPipelineValue(mapper.toBD(summary.get("TotalPipelineValue")));
            }
            d.setStatusWise(statusRows.stream().map(mapper::toStatusWise).collect(Collectors.toList()));
            d.setRecentFollowups(followupRows.stream().map(followupMapper::toFollowup).collect(Collectors.toList()));
            log.info("getDashboard END");
            return d;
        } catch (Exception e) { log.error("getDashboard | Exception occurred", e); throw e; }
    }
}

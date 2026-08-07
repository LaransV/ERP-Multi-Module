package com.nexerp.modules.crm.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.crm.dto.request.FollowupRequest;
import com.nexerp.modules.crm.dto.response.FollowupResponse;
import com.nexerp.modules.crm.mapper.FollowupMapper;
import com.nexerp.modules.crm.repository.interfaces.IFollowupRepository;
import com.nexerp.modules.crm.service.interfaces.IFollowupService;
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
public class FollowupService implements IFollowupService {

    private final IFollowupRepository repository;
    private final FollowupMapper      mapper;
    private final AuthService         auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<FollowupResponse> listFollowups(int page, int size, Integer leadId, String status) {
        log.info("listFollowups START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page); params.put("Size", size);
            params.put("LeadId", leadId != null ? leadId : 0); params.put("Status", mapper.nvl(status));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getFollowups(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toFollowup).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listFollowups | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FollowupResponse createFollowup(FollowupRequest req) {
        log.info("createFollowup START | leadId={}", req.getLeadId());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("LeadId", req.getLeadId()); params.put("ScheduledAt", req.getScheduledAt());
            params.put("Notes", mapper.nvl(req.getNotes())); params.put("FollowupType", req.getFollowupType() != null ? req.getFollowupType() : "CALL");
            params.put("Status", mapper.nvl(req.getStatus())); params.put("CompanyId", cid());
            Integer id = repository.insertFollowup(params);
            Map<String, Object> r = repository.getFollowupById(id);
            return mapper.toFollowup(r != null ? r : new HashMap<>());
        } catch (Exception e) { log.error("createFollowup | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FollowupResponse updateFollowup(Integer id, FollowupRequest req) {
        log.info("updateFollowup START | followupId={}", id);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("FollowupId", id); params.put("ScheduledAt", req.getScheduledAt());
            params.put("Notes", mapper.nvl(req.getNotes())); params.put("FollowupType", req.getFollowupType() != null ? req.getFollowupType() : "CALL");
            params.put("Status", mapper.nvl(req.getStatus())); params.put("CompanyId", cid());
            repository.updateFollowup(params);
            Map<String, Object> r = repository.getFollowupById(id);
            return mapper.toFollowup(r != null ? r : new HashMap<>());
        } catch (Exception e) { log.error("updateFollowup | Exception occurred | followupId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeFollowup(Integer id, String notes) {
        log.info("completeFollowup START | followupId={}", id);
        try {
            repository.completeFollowup(id, notes, cid());
            log.info("completeFollowup END | followupId={}", id);
        } catch (Exception e) { log.error("completeFollowup | Exception occurred | followupId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFollowup(Integer id) {
        log.info("deleteFollowup START | followupId={}", id);
        try {
            repository.deleteFollowup(id, cid());
            log.info("deleteFollowup END | followupId={}", id);
        } catch (Exception e) { log.error("deleteFollowup | Exception occurred | followupId={}", id, e); throw e; }
    }
}

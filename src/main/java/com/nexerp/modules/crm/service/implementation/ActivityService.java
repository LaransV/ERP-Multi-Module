package com.nexerp.modules.crm.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.crm.dto.request.ActivityRequest;
import com.nexerp.modules.crm.dto.response.ActivityResponse;
import com.nexerp.modules.crm.mapper.ActivityMapper;
import com.nexerp.modules.crm.repository.interfaces.IActivityRepository;
import com.nexerp.modules.crm.service.interfaces.IActivityService;
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
public class ActivityService implements IActivityService {

    private final IActivityRepository repository;
    private final ActivityMapper      mapper;
    private final AuthService         auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<ActivityResponse> listActivities(int page, int size, Integer leadId) {
        log.info("listActivities START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page); params.put("Size", size);
            params.put("LeadId", leadId != null ? leadId : 0); params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getActivities(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toActivity).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listActivities | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityResponse createActivity(ActivityRequest req) {
        log.info("createActivity START | leadId={}", req.getLeadId());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("LeadId", req.getLeadId() != null ? req.getLeadId() : 0);
            params.put("ClientId", req.getClientId() != null ? req.getClientId() : 0);
            params.put("EntityName", req.getEntityName()); params.put("ActivityType", req.getActivityType());
            params.put("Title", req.getTitle()); params.put("Description", mapper.nvl(req.getDescription()));
            params.put("ScheduledAt", mapper.nvl(req.getScheduledAt())); params.put("Status", req.getStatus() != null ? req.getStatus() : "OPEN");
            params.put("CompanyId", cid());
            Integer id = repository.insertActivity(params);
            Map<String, Object> r = repository.getActivityById(id);
            return mapper.toActivity(r != null ? r : new HashMap<>());
        } catch (Exception e) { log.error("createActivity | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityResponse updateActivity(Integer id, ActivityRequest req) {
        log.info("updateActivity START | activityId={}", id);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ActivityId", id); params.put("Title", req.getTitle());
            params.put("Description", mapper.nvl(req.getDescription())); params.put("ScheduledAt", mapper.nvl(req.getScheduledAt()));
            params.put("Status", req.getStatus() != null ? req.getStatus() : "OPEN"); params.put("CompanyId", cid());
            repository.updateActivity(params);
            Map<String, Object> r = repository.getActivityById(id);
            return mapper.toActivity(r != null ? r : new HashMap<>());
        } catch (Exception e) { log.error("updateActivity | Exception occurred | activityId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteActivity(Integer id) {
        log.info("deleteActivity START | activityId={}", id);
        try {
            repository.deleteActivity(id, cid());
            log.info("deleteActivity END | activityId={}", id);
        } catch (Exception e) { log.error("deleteActivity | Exception occurred | activityId={}", id, e); throw e; }
    }
}

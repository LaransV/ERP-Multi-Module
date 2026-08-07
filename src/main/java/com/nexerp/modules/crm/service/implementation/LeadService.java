package com.nexerp.modules.crm.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.crm.dto.request.LeadRequest;
import com.nexerp.modules.crm.dto.response.LeadListItem;
import com.nexerp.modules.crm.dto.response.LeadResponse;
import com.nexerp.modules.crm.mapper.LeadMapper;
import com.nexerp.modules.crm.repository.interfaces.ILeadRepository;
import com.nexerp.modules.crm.service.interfaces.ILeadService;
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
public class LeadService implements ILeadService {

    private final ILeadRepository repository;
    private final LeadMapper      mapper;
    private final AuthService     auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<LeadListItem> listLeads(int page, int size, String status, String search) {
        log.info("listLeads START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page); params.put("Size", size);
            params.put("Status", mapper.nvl(status)); params.put("Search", mapper.nvl(search));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getLeads(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toLeadListItem).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listLeads | Exception occurred", e); throw e; }
    }

    @Override
    public LeadResponse getLead(Integer id) {
        log.info("getLead START | leadId={}", id);
        try {
            Map<String, Object> r = repository.getLeadById(id, cid());
            if (r == null) throw new ResourceNotFoundException("Lead", id);
            return mapper.toLead(r);
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getLead | Exception occurred | leadId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LeadResponse createLead(LeadRequest req) {
        log.info("createLead START | leadName={}", req.getLeadName());
        try {
            Map<String, Object> p = mapper.toLeadParams(req);
            p.put("CompanyId", cid());
            Integer id = repository.insertLead(p);
            return getLead(id);
        } catch (Exception e) { log.error("createLead | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LeadResponse updateLead(Integer id, LeadRequest req) {
        log.info("updateLead START | leadId={}", id);
        try {
            Map<String, Object> p = mapper.toLeadParams(req);
            p.put("LeadId", id); p.put("CompanyId", cid());
            repository.updateLead(p);
            return getLead(id);
        } catch (Exception e) { log.error("updateLead | Exception occurred | leadId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLeadStatus(Integer id, String status) {
        log.info("updateLeadStatus START | leadId={}, status={}", id, status);
        try {
            repository.updateLeadStatus(id, status, cid());
            log.info("updateLeadStatus END | leadId={}", id);
        } catch (Exception e) { log.error("updateLeadStatus | Exception occurred | leadId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLead(Integer id) {
        log.info("deleteLead START | leadId={}", id);
        try {
            repository.deleteLead(id, cid());
            log.info("deleteLead END | leadId={}", id);
        } catch (Exception e) { log.error("deleteLead | Exception occurred | leadId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Integer> convertLead(Integer id) {
        log.info("convertLead START | leadId={}", id);
        try {
            Map<String, Object> out = repository.convertLeadToClient(id, cid());
            Object clientIdObj = out.get("ClientId");
            Integer clientId = (clientIdObj instanceof Number) ? ((Number) clientIdObj).intValue() : null;
            Map<String, Integer> result = new HashMap<>();
            if (clientId != null) result.put("clientId", clientId);
            log.info("convertLead END | leadId={}, clientId={}", id, clientId);
            return result;
        } catch (Exception e) { log.error("convertLead | Exception occurred | leadId={}", id, e); throw e; }
    }
}

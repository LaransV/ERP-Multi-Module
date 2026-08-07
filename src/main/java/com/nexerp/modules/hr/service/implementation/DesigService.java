package com.nexerp.modules.hr.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.hr.dto.response.DesigResponse;
import com.nexerp.modules.hr.mapper.DesigMapper;
import com.nexerp.modules.hr.repository.interfaces.IDesigRepository;
import com.nexerp.modules.hr.service.interfaces.IDesigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesigService implements IDesigService {

    private final IDesigRepository repository;
    private final DesigMapper      mapper;
    private final AuthService      auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public List<DesigResponse> listDesigs() {
        log.info("listDesigs START");
        try {
            return repository.getDesignations(cid()).stream().map(r -> {
                DesigResponse d = new DesigResponse();
                d.setDesigId(mapper.toInt(r.get("desig_id")));
                d.setDesigName((String) r.get("desig_name"));
                d.setLevel(r.get("level") != null ? mapper.toInt(r.get("level")) : 0);
                return d;
            }).collect(Collectors.toList());
        } catch (Exception e) { log.error("listDesigs | Exception occurred", e); throw e; }
    }
}

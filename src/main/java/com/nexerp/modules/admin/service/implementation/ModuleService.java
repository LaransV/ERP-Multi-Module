package com.nexerp.modules.admin.service.implementation;

import com.nexerp.modules.admin.dto.response.ModuleResponse;
import com.nexerp.modules.admin.mapper.ModuleMapper;
import com.nexerp.modules.admin.repository.interfaces.IModuleRepository;
import com.nexerp.modules.admin.service.interfaces.IModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleService implements IModuleService {

    private final IModuleRepository repository;
    private final ModuleMapper      mapper;

    @Override
    public List<ModuleResponse> listModules() {
        log.info("listModules START");
        try {
            List<ModuleResponse> result = repository.getModules()
                .stream().map(mapper::toModule).collect(Collectors.toList());
            log.info("listModules END | count={}", result.size());
            return result;
        } catch (Exception e) {
            log.error("listModules | Exception occurred", e);
            throw e;
        }
    }
}

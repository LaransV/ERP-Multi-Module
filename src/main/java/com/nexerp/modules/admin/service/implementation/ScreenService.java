package com.nexerp.modules.admin.service.implementation;

import com.nexerp.modules.admin.dto.response.ScreenResponse;
import com.nexerp.modules.admin.mapper.ScreenMapper;
import com.nexerp.modules.admin.repository.interfaces.IScreenRepository;
import com.nexerp.modules.admin.service.interfaces.IScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScreenService implements IScreenService {

    private final IScreenRepository repository;
    private final ScreenMapper      mapper;

    @Override
    public List<ScreenResponse> listScreens(Integer moduleId) {
        log.info("listScreens START | moduleId={}", moduleId);
        try {
            List<ScreenResponse> result = repository.getScreens(moduleId)
                .stream().map(mapper::toScreen).collect(Collectors.toList());
            log.info("listScreens END | count={}", result.size());
            return result;
        } catch (Exception e) {
            log.error("listScreens | Exception occurred | moduleId={}", moduleId, e);
            throw e;
        }
    }
}

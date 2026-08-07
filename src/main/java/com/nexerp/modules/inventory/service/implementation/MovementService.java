package com.nexerp.modules.inventory.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.inventory.dto.response.MovementResponse;
import com.nexerp.modules.inventory.mapper.MovementMapper;
import com.nexerp.modules.inventory.repository.interfaces.IMovementRepository;
import com.nexerp.modules.inventory.service.interfaces.IMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementService implements IMovementService {

    private final IMovementRepository repository;
    private final MovementMapper      mapper;
    private final AuthService         auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<MovementResponse> listMovements(int page, int size, Integer productId) {
        log.info("listMovements START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page); params.put("Size", size); params.put("ProductId", productId != null ? productId : 0);
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getMovements(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toMovement).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listMovements | Exception occurred", e); throw e; }
    }
}

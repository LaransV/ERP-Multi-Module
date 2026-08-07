package com.nexerp.modules.inventory.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.inventory.dto.request.AdjustRequest;
import com.nexerp.modules.inventory.dto.response.StockItemResponse;
import com.nexerp.modules.inventory.mapper.StockMapper;
import com.nexerp.modules.inventory.repository.interfaces.IStockRepository;
import com.nexerp.modules.inventory.service.interfaces.IStockService;
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
public class StockService implements IStockService {

    private final IStockRepository repository;
    private final StockMapper      mapper;
    private final AuthService      auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<StockItemResponse> listStock(int page, int size, String filter) {
        log.info("listStock START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page); params.put("Size", size); params.put("Filter", mapper.nvl(filter));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getStock(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toStock).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listStock | Exception occurred", e); throw e; }
    }

    @Override
    public StockItemResponse getStock(Integer id) {
        log.info("getStock START | stockId={}", id);
        try {
            Map<String, Object> r = repository.getStockById(id, cid());
            if (r == null) throw new ResourceNotFoundException("Stock item", id);
            return mapper.toStock(r);
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getStock | Exception occurred | stockId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adjustStock(AdjustRequest req) {
        log.info("adjustStock START | productId={}", req.getProductId());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ProductId", req.getProductId()); params.put("Quantity", req.getQuantity()); params.put("Type", req.getType()); params.put("Notes", mapper.nvl(req.getNotes()));
            params.put("CompanyId", cid());
            repository.adjustStock(params);
            log.info("adjustStock END | productId={}", req.getProductId());
        } catch (Exception e) { log.error("adjustStock | Exception occurred | productId={}", req.getProductId(), e); throw e; }
    }
}

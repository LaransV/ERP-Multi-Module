package com.nexerp.modules.inventory.service.implementation;

import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.inventory.dto.response.InventoryDashboardResponse;
import com.nexerp.modules.inventory.mapper.InventoryDashboardMapper;
import com.nexerp.modules.inventory.mapper.MovementMapper;
import com.nexerp.modules.inventory.mapper.StockMapper;
import com.nexerp.modules.inventory.repository.interfaces.IInventoryDashboardRepository;
import com.nexerp.modules.inventory.repository.interfaces.IMovementRepository;
import com.nexerp.modules.inventory.service.interfaces.IInventoryDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryDashboardService implements IInventoryDashboardService {

    private final IInventoryDashboardRepository repository;
    private final IMovementRepository  movementRepository;
    private final InventoryDashboardMapper mapper;
    private final MovementMapper       movementMapper;
    private final StockMapper          stockMapper;
    private final AuthService          auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public InventoryDashboardResponse getDashboard() {
        log.info("getDashboard START");
        try {
            Integer companyId = cid();
            Map<String, Object> summary = repository.getDashboardSummary(companyId);
            List<Map<String, Object>> recentMovements = movementRepository.getRecentMovements(5, companyId);
            List<Map<String, Object>> lowStock = repository.getLowStockItems(10, companyId);
            InventoryDashboardResponse d = new InventoryDashboardResponse();
            if (summary != null) {
                d.setTotalItems(mapper.toLong(summary.get("TotalItems"))); d.setTotalStockValue(mapper.toBD(summary.get("TotalStockValue")));
                d.setLowStockCount(mapper.toLong(summary.get("LowStockCount"))); d.setOutOfStockCount(mapper.toLong(summary.get("OutOfStockCount")));
            }
            d.setRecentMovements(recentMovements.stream().map(movementMapper::toMovement).collect(Collectors.toList()));
            d.setLowStockItems(lowStock.stream().map(stockMapper::toStock).collect(Collectors.toList()));
            log.info("getDashboard END");
            return d;
        } catch (Exception e) { log.error("getDashboard | Exception occurred", e); throw e; }
    }
}

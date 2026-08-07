package com.nexerp.modules.inventory.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.inventory.dto.request.PoItemRequest;
import com.nexerp.modules.inventory.dto.request.PoRequest;
import com.nexerp.modules.inventory.dto.response.PoListItem;
import com.nexerp.modules.inventory.dto.response.PoResponse;
import com.nexerp.modules.inventory.mapper.PurchaseOrderMapper;
import com.nexerp.modules.inventory.repository.interfaces.IPurchaseOrderRepository;
import com.nexerp.modules.inventory.service.interfaces.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderRepository repository;
    private final PurchaseOrderMapper      mapper;
    private final AuthService              auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<PoListItem> listPOs(int page, int size, String status) {
        log.info("listPOs START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page", page); params.put("Size", size); params.put("Status", mapper.nvl(status));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getPurchaseOrders(params);
            long total = rows.isEmpty() ? 0 : mapper.toLong(rows.get(0).get("TotalCount") != null ? rows.get(0).get("TotalCount") : rows.size());
            return PagedResponse.of(rows.stream().map(mapper::toPoListItem).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listPOs | Exception occurred", e); throw e; }
    }

    @Override
    public PoResponse getPO(Integer id) {
        log.info("getPO START | poId={}", id);
        try {
            Map<String, Object> h = repository.getPOById(id, cid());
            if (h == null) throw new ResourceNotFoundException("Purchase Order", id);
            return mapper.toPO(h, repository.getPOItems(id));
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getPO | Exception occurred | poId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PoResponse createPO(PoRequest req) {
        log.info("createPO START | vendorId={}", req.getVendorId());
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("VendorId", req.getVendorId()); params.put("PoDate", req.getPoDate()); params.put("ExpectedDate", mapper.nvl(req.getExpectedDate()));
            params.put("TotalAmount", req.getTotalAmount() != null ? req.getTotalAmount() : BigDecimal.ZERO); params.put("Notes", mapper.nvl(req.getNotes()));
            params.put("CompanyId", cid());
            Integer id = repository.insertPO(params);
            if (req.getItems() != null) {
                for (PoItemRequest item : req.getItems()) {
                    Map<String, Object> ip = new HashMap<>();
                    ip.put("PoId", id); ip.put("ProductId", item.getProductId()); ip.put("ProductName", item.getProductName());
                    ip.put("Quantity", item.getQuantity()); ip.put("UnitPrice", item.getUnitPrice()); ip.put("TotalAmount", item.getTotalAmount() != null ? item.getTotalAmount() : BigDecimal.ZERO);
                    repository.insertPOItem(ip);
                }
            }
            return getPO(id);
        } catch (Exception e) { log.error("createPO | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approvePO(Integer id) {
        log.info("approvePO START | poId={}", id);
        try {
            repository.approvePO(id, cid());
            log.info("approvePO END | poId={}", id);
        } catch (Exception e) { log.error("approvePO | Exception occurred | poId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePO(Integer id) {
        log.info("deletePO START | poId={}", id);
        try {
            repository.deletePO(id, cid());
            log.info("deletePO END | poId={}", id);
        } catch (Exception e) { log.error("deletePO | Exception occurred | poId={}", id, e); throw e; }
    }
}

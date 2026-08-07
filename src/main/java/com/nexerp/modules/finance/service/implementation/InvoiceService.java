package com.nexerp.modules.finance.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.finance.dto.request.InvoiceItemRequestDto;
import com.nexerp.modules.finance.dto.request.InvoiceRequestDto;
import com.nexerp.modules.finance.dto.response.GetAllInvoiceDto;
import com.nexerp.modules.finance.dto.response.InvoiceResponseDto;
import com.nexerp.modules.finance.mapper.InvoicesMapper;
import com.nexerp.modules.finance.repository.interfaces.IInvoiceRepository;
import com.nexerp.modules.finance.service.interfaces.IInvoiceService;
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
public class InvoiceService implements IInvoiceService {

    private final IInvoiceRepository repository;
    private final InvoicesMapper     invoiceMapper;
    private final AuthService       auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<GetAllInvoiceDto> listInvoices(int page, int size, String status, Integer clientId) {
        log.info("listInvoices START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      page);
            params.put("Size",      size);
            params.put("Status",    invoiceMapper.nvl(status));
            params.put("ClientId",  clientId != null ? clientId : 0);
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getInvoices(params);
            long total = invoiceMapper.firstLong(rows, "TotalCount", rows.size());
            return PagedResponse.of(rows.stream().map(invoiceMapper::toInvoiceListItem).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listInvoices | Exception occurred", e); throw e; }
    }

    @Override
    public InvoiceResponseDto getInvoice(Integer id) {
        log.info("getInvoice START | invoiceId={}", id);
        try {
            Map<String, Object> header = repository.getInvoiceById(id, cid());
            if (header == null) throw new ResourceNotFoundException("Invoice", id);
            List<Map<String, Object>> items = repository.getInvoiceItems(id);
            return invoiceMapper.toInvoice(header, items);
        } catch (ResourceNotFoundException e) { throw e; }
        catch (Exception e) { log.error("getInvoice | Exception occurred | invoiceId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseDto createInvoice(InvoiceRequestDto req) {
        log.info("createInvoice START | clientId={}", req.getClientId());
        try {
            Map<String, Object> p = invoiceMapper.toInvoiceHeaderParams(req);
            p.put("CompanyId", cid());
            Integer invoiceId = repository.insertInvoice(p);
            if (req.getItems() != null) {
                int sortOrder = 1;
                for (InvoiceItemRequestDto item : req.getItems()) {
                    repository.insertInvoiceItem(invoiceMapper.toInvoiceItemParams(invoiceId, sortOrder++, item));
                }
            }
            return getInvoice(invoiceId);
        } catch (Exception e) { log.error("createInvoice | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceResponseDto updateInvoice(Integer id, InvoiceRequestDto req) {
        log.info("updateInvoice START | invoiceId={}", id);
        try {
            Map<String, Object> p = invoiceMapper.toInvoiceHeaderParams(req);
            p.put("InvoiceId", id);
            p.put("CompanyId", cid());
            repository.updateInvoice(p);
            repository.deleteInvoiceItems(id);
            if (req.getItems() != null) {
                int sortOrder = 1;
                for (InvoiceItemRequestDto item : req.getItems()) {
                    repository.insertInvoiceItem(invoiceMapper.toInvoiceItemParams(id, sortOrder++, item));
                }
            }
            return getInvoice(id);
        } catch (Exception e) { log.error("updateInvoice | Exception occurred | invoiceId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInvoiceStatus(Integer id, String status) {
        log.info("updateInvoiceStatus START | invoiceId={}, status={}", id, status);
        try {
            repository.updateInvoiceStatus(id, status, cid());
            log.info("updateInvoiceStatus END | invoiceId={}", id);
        } catch (Exception e) { log.error("updateInvoiceStatus | Exception occurred | invoiceId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInvoice(Integer id) {
        log.info("deleteInvoice START | invoiceId={}", id);
        try {
            repository.deleteInvoice(id, cid());
            log.info("deleteInvoice END | invoiceId={}", id);
        } catch (Exception e) { log.error("deleteInvoice | Exception occurred | invoiceId={}", id, e); throw e; }
    }
}

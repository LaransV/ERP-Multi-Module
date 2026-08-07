package com.nexerp.modules.finance.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.export.IExportService;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.finance.dto.request.ClientRequestDto;
import com.nexerp.modules.finance.dto.response.ClientResponseDto;
import com.nexerp.modules.finance.mapper.ClientMapper;
import com.nexerp.modules.finance.repository.interfaces.IClientRepository;
import com.nexerp.modules.finance.service.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final IClientRepository repository;
    private final ClientMapper     clientMapper;
    private final AuthService       auth;
    private final IExportService exportService;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }
    private Integer uid() { return auth.currentPrincipal().getUserId(); }

    @Override
    public PagedResponse<ClientResponseDto> listClients(int page, int size, String search, String stateName) {
        log.info("listClients START | page={}, size={}, stateName={}", page, size, stateName);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      page + 1);
            params.put("Size",      size);
            params.put("Search",    clientMapper.nvl(search));
            params.put("StateName", clientMapper.nvl(stateName));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getClients(params);
            long total = clientMapper.firstLong(rows, "TotalCount", rows.size());
            List<ClientResponseDto> content = rows.stream().map(clientMapper::toClient).collect(Collectors.toList());
            log.info("listClients END | total={}", total);
            return PagedResponse.of(content, page, size, total);
        } catch (Exception e) { log.error("listClients | Exception occurred", e); throw e; }
    }

    @Override
    public List<ClientResponseDto> listAllClientsForExport(String stateName) {
        log.info("listAllClientsForExport START | stateName={}", stateName);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      1);
            params.put("Size",      Integer.MAX_VALUE);
            params.put("Search",    "");
            params.put("StateName", clientMapper.nvl(stateName));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getClients(params);
            log.info("listAllClientsForExport END | total={}", rows.size());
            return rows.stream().map(clientMapper::toClient).collect(Collectors.toList());
        } catch (Exception e) { log.error("listAllClientsForExport | Exception occurred", e); throw e; }
    }

    @Override
    public List<ClientResponseDto> searchClients(String q) {
        log.info("searchClients START | q={}", q);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Query",     clientMapper.nvl(q));
            params.put("CompanyId", cid());
            List<ClientResponseDto> result = repository.searchClients(params)
                .stream().map(clientMapper::toClient).collect(Collectors.toList());
            log.info("searchClients END | found={}", result.size());
            return result;
        } catch (Exception e) { log.error("searchClients | Exception occurred", e); throw e; }
    }

    @Override
    public ClientResponseDto getClient(Integer id) {
        log.info("getClient START | clientId={}", id);
        try {
            Map<String, Object> r = repository.getClientById(id, cid());
            if (r == null) throw new ResourceNotFoundException("Client", id);
            return clientMapper.toClient(r);
        } catch (ResourceNotFoundException e) { throw e; }
          catch (Exception e) { log.error("getClient | Exception occurred | clientId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto createClient(ClientRequestDto req) {
        log.info("createClient START | clientName={}", req.getClientName());
        try {
            Map<String, Object> p = clientMapper.toClientParams(req);
            p.put("CompanyId", cid());
            p.put("UserId", uid());
            Integer id = repository.insertClient(p);
            return getClient(id);
        } catch (Exception e) { log.error("createClient | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto updateClient(Integer id, ClientRequestDto req) {
        log.info("updateClient START | clientId={}", id);
        try {
            Map<String, Object> p = clientMapper.toClientParams(req);
            p.put("ClientId",  id);
            p.put("CompanyId", cid());
            p.put("UserId", uid());
            repository.updateClient(p);
            return getClient(id);
        } catch (Exception e) { log.error("updateClient | Exception occurred | clientId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClient(Integer id) {
        log.info("deleteClient START | clientId={}", id);
        try {
            repository.deleteClient(id, cid());
            log.info("deleteClient END | clientId={}", id);
        } catch (Exception e) { log.error("deleteClient | Exception occurred | clientId={}", id, e); throw e; }
    }

    // ── Export Excel ──────────────────────────────────────────
    @Override
    public byte[] exportClientsExcel(String stateName) {
        log.info("exportClientsExcel START | stateName={}", stateName);
        try {
            List<ClientResponseDto> clients = listAllClientsForExport(stateName);
            // Header row
            List<String> headers = Arrays.asList("#", "Client Name", "Mobile", "Email", "GST No", "PAN No", "State", "City", "Address");
            List<List<String>> rows = new java.util.ArrayList<>();
            // Data rows
            int idx = 1;
            for (ClientResponseDto c : clients) {
                rows.add(Arrays.asList(String.valueOf(idx++), nvl(c.getClientName()), nvl(c.getPhone()), nvl(c.getEmail()),
                        nvl(c.getGstNumber()), nvl(c.getPanNumber()), nvl(c.getState()), nvl(c.getCity()), nvl(c.getAddress())));
            }

            return exportService.exportExcel("Clients", headers, rows);
        } catch (Exception e) {
            log.error("exportClientsExcel failed", e);
            throw new RuntimeException("Failed to generate Excel", e);
        }
    }

    // ── Export PDF ────────────────────────────────────────────
    @Override
    public byte[] exportClientsPdf(String stateName) {
        log.info("exportClientsPdf START | stateName={}", stateName);
        List<ClientResponseDto> clients = listAllClientsForExport(stateName);
        try {

            String title = "Client List" + (stateName != null && !stateName.isEmpty() ? " — " + stateName : "");
            List<String> headers = Arrays.asList("#", "Client Name", "Mobile", "Email", "GST No", "PAN No", "State", "City");
            float[] widths = {1.5f, 5f, 3f, 4f, 3.5f, 3f, 3.5f, 3f};
            List<List<String>> rows = new java.util.ArrayList<>();
            int idx = 1;
            for (ClientResponseDto c : clients) {
                rows.add(Arrays.asList(String.valueOf(idx++), nvl(c.getClientName()), nvl(c.getPhone()), nvl(c.getEmail()),
                        nvl(c.getGstNumber()), nvl(c.getPanNumber()), nvl(c.getState()), nvl(c.getCity())));
            }

            return exportService.exportPdf(title, headers, rows, widths);

        } catch (Exception e) {
            log.error("exportClientsPdf failed", e);
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private String nvl(String s) { return s != null ? s : ""; }
}

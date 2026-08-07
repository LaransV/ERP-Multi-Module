package com.nexerp.modules.finance.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.finance.dto.request.ClientRequestDto;
import com.nexerp.modules.finance.dto.response.ClientResponseDto;

import java.util.List;

public interface IClientService {
    PagedResponse<ClientResponseDto> listClients(int page, int size, String search, String stateName);
    List<ClientResponseDto> listAllClientsForExport(String stateName);
    List<ClientResponseDto> searchClients(String q);
    ClientResponseDto getClient(Integer id);
    ClientResponseDto createClient(ClientRequestDto req);
    ClientResponseDto updateClient(Integer id, ClientRequestDto req);
    void deleteClient(Integer id);
    byte[] exportClientsExcel(String stateName);
    byte[] exportClientsPdf(String stateName);
}

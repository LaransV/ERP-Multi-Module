package com.nexerp.modules.finance.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.finance.dto.request.InvoiceRequestDto;
import com.nexerp.modules.finance.dto.response.GetAllInvoiceDto;
import com.nexerp.modules.finance.dto.response.InvoiceResponseDto;

public interface IInvoiceService {
    PagedResponse<GetAllInvoiceDto> listInvoices(int page, int size, String status, Integer clientId);
    InvoiceResponseDto getInvoice(Integer id);
    InvoiceResponseDto createInvoice(InvoiceRequestDto req);
    InvoiceResponseDto updateInvoice(Integer id, InvoiceRequestDto req);
    void updateInvoiceStatus(Integer id, String status);
    void deleteInvoice(Integer id);
}

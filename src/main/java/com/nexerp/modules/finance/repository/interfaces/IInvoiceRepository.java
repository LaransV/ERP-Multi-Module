package com.nexerp.modules.finance.repository.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IInvoiceRepository {

    // ── Invoices ─────────────────────────────
    List<Map<String, Object>> getInvoices(Map<String, Object> params);
    Map<String, Object> getInvoiceById(Integer id, Integer companyId);
    List<Map<String, Object>> getInvoiceItems(Integer invoiceId);
    Integer insertInvoice(Map<String, Object> params);
    void insertInvoiceItem(Map<String, Object> params);
    void updateInvoice(Map<String, Object> params);
    void deleteInvoiceItems(Integer invoiceId);
    void updateInvoiceStatus(Integer id, String status, Integer companyId);
    void deleteInvoice(Integer id, Integer companyId);

}

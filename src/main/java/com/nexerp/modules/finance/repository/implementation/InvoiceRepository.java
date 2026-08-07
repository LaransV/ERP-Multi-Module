package com.nexerp.modules.finance.repository.implementation;

import com.nexerp.config.JdbcExecutor;
import com.nexerp.modules.finance.repository.interfaces.IInvoiceRepository;
import com.nexerp.modules.finance.sql.InvoiceSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InvoiceRepository implements IInvoiceRepository {

    private final JdbcExecutor jdbc;

    @Override
    public List<Map<String, Object>> getInvoices(Map<String, Object> params) {
        int page = (Integer) params.get("Page");
        int size = (Integer) params.get("Size");
        params.put("Offset", page * size);
        return jdbc.queryList(InvoiceSql.GET_INVOICES, params);
    }

    @Override
    public Map<String, Object> getInvoiceById(Integer id, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("InvoiceId", id);
        params.put("CompanyId", companyId);
        return jdbc.queryOne(InvoiceSql.GET_INVOICE_BY_ID, params);
    }

    @Override
    public List<Map<String, Object>> getInvoiceItems(Integer invoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("InvoiceId", invoiceId);
        return jdbc.queryList(InvoiceSql.GET_INVOICE_ITEMS, params);
    }

    /**
     * usp_Finance_InsertInvoice did the year-sequence claim + insert in one
     * proc call; Postgres needs it as separate statements. Stays atomic
     * because this only ever runs inside InvoiceService's @Transactional
     * methods.
     *
     * NOTE: the original proc never set Invoices.CompanyId even though the
     * column is NOT NULL (a pre-existing bug - untested because no Invoices
     * exist in the seed data). Fixed here using the CompanyId InvoiceService
     * already puts in params.
     */
    @Override
    public Integer insertInvoice(Map<String, Object> params) {
        int year = Year.now().getValue();
        Map<String, Object> yearParam = new HashMap<>();
        yearParam.put("Year", year);

        jdbc.execute(InvoiceSql.ENSURE_SEQUENCE_YEAR, yearParam);
        Integer num = jdbc.executeAndGetId(InvoiceSql.NEXT_INVOICE_NUMBER, yearParam);
        String invoiceNumber = String.format("INV/%d/%05d", year, num);

        params.put("InvoiceNumber", invoiceNumber);
        return jdbc.executeAndGetId(InvoiceSql.INSERT_INVOICE, params);
    }

    @Override
    public void insertInvoiceItem(Map<String, Object> params) {
        jdbc.execute(InvoiceSql.INSERT_INVOICE_ITEM, params);
    }

    @Override
    public void updateInvoice(Map<String, Object> params) {
        jdbc.execute(InvoiceSql.UPDATE_INVOICE, params);
    }

    @Override
    public void deleteInvoiceItems(Integer invoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("InvoiceId", invoiceId);
        jdbc.execute(InvoiceSql.DELETE_INVOICE_ITEMS, params);
    }

    @Override
    public void updateInvoiceStatus(Integer id, String status, Integer companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("InvoiceId", id);
        params.put("Status", status);
        params.put("CompanyId", companyId);
        jdbc.execute(InvoiceSql.UPDATE_INVOICE_STATUS, params);
    }

    @Override
    public void deleteInvoice(Integer id, Integer companyId) {
        Map<String, Object> itemParams = new HashMap<>();
        itemParams.put("InvoiceId", id);
        jdbc.execute(InvoiceSql.DELETE_INVOICE_ITEMS, itemParams);

        Map<String, Object> params = new HashMap<>();
        params.put("InvoiceId", id);
        params.put("CompanyId", companyId);
        jdbc.execute(InvoiceSql.DELETE_INVOICE, params);
    }
}

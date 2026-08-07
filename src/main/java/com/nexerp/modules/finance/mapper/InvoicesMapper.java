package com.nexerp.modules.finance.mapper;

import com.nexerp.modules.finance.dto.request.InvoiceItemRequestDto;
import com.nexerp.modules.finance.dto.request.InvoiceRequestDto;
import com.nexerp.modules.finance.dto.response.GetAllInvoiceDto;
import com.nexerp.modules.finance.dto.response.InvoiceItemResponseDto;
import com.nexerp.modules.finance.dto.response.InvoiceResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InvoicesMapper {

    // ── Invoices ─────────────────────────────
    public Map<String, Object> toInvoiceHeaderParams(InvoiceRequestDto r) {
        Map<String, Object> p = new HashMap<>();
        p.put("ClientId",       r.getClientId());
        p.put("InvoiceDate",    r.getInvoiceDate());
        p.put("DueDate",        r.getDueDate());
        p.put("Subtotal",       r.getSubtotal()       != null ? r.getSubtotal()       : BigDecimal.ZERO);
        p.put("DiscountAmount", r.getDiscountAmount() != null ? r.getDiscountAmount() : BigDecimal.ZERO);
        p.put("TaxableAmount",  r.getTaxableAmount()  != null ? r.getTaxableAmount()  : BigDecimal.ZERO);
        p.put("CgstTotal",      r.getCgstTotal()      != null ? r.getCgstTotal()      : BigDecimal.ZERO);
        p.put("SgstTotal",      r.getSgstTotal()      != null ? r.getSgstTotal()      : BigDecimal.ZERO);
        p.put("IgstTotal",      r.getIgstTotal()      != null ? r.getIgstTotal()      : BigDecimal.ZERO);
        p.put("TaxTotal",       r.getTaxTotal()       != null ? r.getTaxTotal()       : BigDecimal.ZERO);
        p.put("TdsPct",         r.getTdsPct()         != null ? r.getTdsPct()         : BigDecimal.ZERO);
        p.put("TdsAmount",      r.getTdsAmount()      != null ? r.getTdsAmount()      : BigDecimal.ZERO);
        p.put("RoundOff",       r.getRoundOff()       != null ? r.getRoundOff()       : BigDecimal.ZERO);
        p.put("GrandTotal",     r.getGrandTotal()     != null ? r.getGrandTotal()     : BigDecimal.ZERO);
        p.put("IsInterstate",   r.isInterstate());
        p.put("Notes",          nvl(r.getNotes()));
        p.put("Terms",          nvl(r.getTerms()));
        p.put("SupplierRefNo",  nvl(r.getSupplierRefNo()));
        p.put("EWayBillNo",     nvl(r.getEWayBillNo()));
        p.put("GenerateEWayBill", r.isGenerateEWayBill());
        p.put("DcNo",           nvl(r.getDcNo()));
        p.put("DcDate",         nvl(r.getDcDate()));
        p.put("SelectDc",       nvl(r.getSelectDc()));
        p.put("VehicleNo",      nvl(r.getVehicleNo()));
        p.put("LrNo",           nvl(r.getLrNo()));
        p.put("Distance",       r.getDistance()        != null ? r.getDistance()        : BigDecimal.ZERO);
        p.put("TransporterId",  nvl(r.getTransporterId()));
        p.put("DelThrough",     nvl(r.getDelThrough()));
        p.put("DelDestn",       nvl(r.getDelDestn()));
        p.put("OrderNo",        nvl(r.getOrderNo()));
        p.put("OrderDate",      nvl(r.getOrderDate()));
        p.put("SoNo",           nvl(r.getSoNo()));
        p.put("Currency",       nvl(r.getCurrency()));
        p.put("DispatchAddressId", r.getDispatchAddressId() != null ? r.getDispatchAddressId() : 0);
        p.put("ShipToAddressId",   r.getShipToAddressId()   != null ? r.getShipToAddressId()   : 0);
        return p;
    }

    public Map<String, Object> toInvoiceItemParams(Integer invoiceId, int sortOrder, InvoiceItemRequestDto i) {
        Map<String, Object> p = new HashMap<>();
        p.put("InvoiceId",      invoiceId);
        p.put("ProductId",      i.getProductId());
        p.put("ProductName",    i.getProductName());
        p.put("HsnCode",        nvl(i.getHsnCode()));
        p.put("Quantity",       i.getQuantity());
        p.put("Unit",           nvl(i.getUnit()));
        p.put("UnitPrice",      i.getUnitPrice());
        p.put("DiscountPct",    i.getDiscountPct()    != null ? i.getDiscountPct()    : BigDecimal.ZERO);
        p.put("DiscountAmount", i.getDiscountAmount() != null ? i.getDiscountAmount() : BigDecimal.ZERO);
        p.put("TaxableAmount",  i.getTaxableAmount()  != null ? i.getTaxableAmount()  : BigDecimal.ZERO);
        p.put("CgstRate",       i.getCgstRate()       != null ? i.getCgstRate()       : BigDecimal.ZERO);
        p.put("CgstAmount",     i.getCgstAmount()     != null ? i.getCgstAmount()     : BigDecimal.ZERO);
        p.put("SgstRate",       i.getSgstRate()       != null ? i.getSgstRate()       : BigDecimal.ZERO);
        p.put("SgstAmount",     i.getSgstAmount()     != null ? i.getSgstAmount()     : BigDecimal.ZERO);
        p.put("IgstRate",       i.getIgstRate()       != null ? i.getIgstRate()       : BigDecimal.ZERO);
        p.put("IgstAmount",     i.getIgstAmount()     != null ? i.getIgstAmount()     : BigDecimal.ZERO);
        p.put("TotalAmount",    i.getTotalAmount()    != null ? i.getTotalAmount()    : BigDecimal.ZERO);
        p.put("SortOrder",      sortOrder);
        return p;
    }

    public GetAllInvoiceDto toInvoiceListItem(Map<String, Object> r) {
        GetAllInvoiceDto i = new GetAllInvoiceDto();
        i.setInvoiceId(toInt(r.get("invoice_id")));
        i.setInvoiceNumber((String) r.get("invoice_number"));
        i.setClientId(toInt(r.get("client_id")));
        i.setClientName((String) r.get("client_name"));
        i.setInvoiceDate(str(r.get("invoice_date")));
        i.setDueDate(str(r.get("due_date")));
        i.setStatus((String) r.get("status"));
        i.setPaymentStatus((String) r.get("payment_status"));
        i.setGrandTotal(toBD(r.get("grand_total")));
        i.setPaidAmount(toBD(r.get("paid_amount")));
        i.setBalanceAmount(toBD(r.get("balance_amount")));
        i.setCreatedAt(str(r.get("created_at")));
        return i;
    }

    public InvoiceResponseDto toInvoice(Map<String, Object> h, List<Map<String, Object>> itemRows) {
        InvoiceResponseDto inv = new InvoiceResponseDto();
        inv.setInvoiceId(toInt(h.get("invoice_id")));
        inv.setInvoiceNumber((String) h.get("invoice_number"));
        inv.setClientId(toInt(h.get("client_id")));
        inv.setClientName((String) h.get("client_name"));
        inv.setInvoiceDate(str(h.get("invoice_date")));
        inv.setDueDate(str(h.get("due_date")));
        inv.setStatus((String) h.get("status"));
        inv.setPaymentStatus((String) h.get("payment_status"));
        inv.setSubtotal(toBD(h.get("subtotal")));
        inv.setDiscountAmount(toBD(h.get("discount_amount")));
        inv.setTaxableAmount(toBD(h.get("taxable_amount")));
        inv.setCgstTotal(toBD(h.get("cgst_total")));
        inv.setSgstTotal(toBD(h.get("sgst_total")));
        inv.setIgstTotal(toBD(h.get("igst_total")));
        inv.setTaxTotal(toBD(h.get("tax_total")));
        inv.setTdsPct(toBD(h.get("tds_pct")));
        inv.setTdsAmount(toBD(h.get("tds_amount")));
        inv.setRoundOff(toBD(h.get("round_off")));
        inv.setGrandTotal(toBD(h.get("grand_total")));
        inv.setPaidAmount(toBD(h.get("paid_amount")));
        inv.setBalanceAmount(toBD(h.get("balance_amount")));
        inv.setNotes((String) h.get("notes"));
        inv.setTerms((String) h.get("terms"));
        inv.setInterstate(Boolean.TRUE.equals(h.get("is_interstate")));
        inv.setCreatedAt(str(h.get("created_at")));
        inv.setSupplierRefNo((String) h.get("supplier_ref_no"));
        inv.setEWayBillNo((String) h.get("e_way_bill_no"));
        inv.setGenerateEWayBill(Boolean.TRUE.equals(h.get("generate_e_way_bill")));
        inv.setDcNo((String) h.get("dc_no"));
        inv.setDcDate(str(h.get("dc_date")));
        inv.setSelectDc((String) h.get("select_dc"));
        inv.setVehicleNo((String) h.get("vehicle_no"));
        inv.setLrNo((String) h.get("lr_no"));
        inv.setDistance(h.get("distance") != null ? toBD(h.get("distance")) : null);
        inv.setTransporterId((String) h.get("transporter_id"));
        inv.setDelThrough((String) h.get("del_through"));
        inv.setDelDestn((String) h.get("del_destn"));
        inv.setOrderNo((String) h.get("order_no"));
        inv.setOrderDate(str(h.get("order_date")));
        inv.setSoNo((String) h.get("so_no"));
        inv.setCurrency((String) h.get("currency"));
        inv.setDispatchAddressId(h.get("dispatch_address_id") != null ? toInt(h.get("dispatch_address_id")) : null);
        inv.setDispatchName((String) h.get("DispatchName"));
        inv.setDispatchAddressLine1((String) h.get("DispatchAddressLine1"));
        inv.setDispatchAddressLine2((String) h.get("DispatchAddressLine2"));
        inv.setDispatchStateName((String) h.get("DispatchStateName"));
        inv.setDispatchPincode((String) h.get("DispatchPincode"));
        inv.setShipToAddressId(h.get("ship_to_address_id") != null ? toInt(h.get("ship_to_address_id")) : null);
        inv.setShipToName((String) h.get("ShipToName"));
        inv.setShipToAddressLine1((String) h.get("ShipToAddressLine1"));
        inv.setShipToAddressLine2((String) h.get("ShipToAddressLine2"));
        inv.setShipToStateName((String) h.get("ShipToStateName"));
        inv.setShipToPincode((String) h.get("ShipToPincode"));
        inv.setShipToGstin((String) h.get("ShipToGstin"));
        inv.setItems(itemRows.stream().map(ir -> {
            InvoiceItemResponseDto item = new InvoiceItemResponseDto();
            item.setItemId(toInt(ir.get("item_id")));
            item.setProductId(ir.get("product_id") != null ? toInt(ir.get("product_id")) : null);
            item.setProductName((String) ir.get("product_name"));
            item.setHsnCode((String) ir.get("hsn_code"));
            item.setQuantity(toBD(ir.get("quantity")));
            item.setUnit((String) ir.get("unit"));
            item.setUnitPrice(toBD(ir.get("unit_price")));
            item.setDiscountPct(toBD(ir.get("discount_pct")));
            item.setDiscountAmount(toBD(ir.get("discount_amount")));
            item.setTaxableAmount(toBD(ir.get("taxable_amount")));
            item.setCgstRate(toBD(ir.get("cgst_rate")));
            item.setCgstAmount(toBD(ir.get("cgst_amount")));
            item.setSgstRate(toBD(ir.get("sgst_rate")));
            item.setSgstAmount(toBD(ir.get("sgst_amount")));
            item.setIgstRate(toBD(ir.get("igst_rate")));
            item.setIgstAmount(toBD(ir.get("igst_amount")));
            item.setTotalAmount(toBD(ir.get("total_amount")));
            return item;
        }).collect(Collectors.toList()));
        return inv;
    }

    public String     nvl(String s)   { return s != null ? s : ""; }
    public String     str(Object o)   { return o != null ? o.toString() : null; }
    public int        toInt(Object o) { if (o instanceof Number) return ((Number) o).intValue(); return 0; }
    public long        toLong(Object o){ if (o instanceof Number) return ((Number) o).longValue(); return 0L; }
    public BigDecimal toBD(Object o)  { if (o == null) return BigDecimal.ZERO; if (o instanceof BigDecimal) return (BigDecimal) o; return new BigDecimal(o.toString()); }
    public long firstLong(List<Map<String, Object>> rows, String key, long dflt) {
        if (rows.isEmpty()) return dflt;
        Object v = rows.get(0).get(key);
        if (v instanceof Number) return ((Number) v).longValue();
        return dflt;
    }
}

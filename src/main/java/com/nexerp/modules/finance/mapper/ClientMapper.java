package com.nexerp.modules.finance.mapper;

import com.nexerp.modules.finance.dto.request.ClientRequestDto;
import com.nexerp.modules.finance.dto.response.ClientResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClientMapper {

    // ── Clients ──────────────────────────────
    public Map<String, Object> toClientParams(ClientRequestDto r) {
        Map<String, Object> p = new HashMap<>();
        p.put("ClientName",    nvl(r.getClientName()));
        p.put("AliasName",     nvl(r.getAliasName()));
        p.put("VendorCode",    nvl(r.getVendorCode()));
        p.put("Email",         nvl(r.getEmail()));
        p.put("Website",       nvl(r.getWebsite()));
        p.put("LandlinePhone", nvl(r.getLandlinePhone()));
        p.put("Phone",         nvl(r.getPhone()));
        p.put("GstnType",      nvl(r.getGstnType()));
        p.put("GstNumber",     nvl(r.getGstNumber()));
        p.put("PanNumber",     nvl(r.getPanNumber()));
        p.put("Address",       nvl(r.getAddress()));
        p.put("AddressLine2",  nvl(r.getAddressLine2()));
        p.put("City",          nvl(r.getCity()));
        p.put("Country",       nvl(r.getCountry()));
        p.put("State",         nvl(r.getState()));
        p.put("Pincode",       nvl(r.getPincode()));
        p.put("Currency",      nvl(r.getCurrency()));
        p.put("PaymentTerms",  nvl(r.getPaymentTerms()));
        return p;
    }

    public ClientResponseDto toClient(Map<String, Object> r) {
        ClientResponseDto c = new ClientResponseDto();
        c.setClientId(toInt(r.get("client_id")));
        c.setClientName((String) r.get("client_name"));
        c.setAliasName((String) r.get("alias_name"));
        c.setVendorCode((String) r.get("vendor_code"));
        c.setEmail((String) r.get("email"));
        c.setWebsite((String) r.get("website"));
        c.setLandlinePhone((String) r.get("landline_phone"));
        c.setPhone((String) r.get("phone"));
        c.setGstnType((String) r.get("gstn_type"));
        c.setGstNumber((String) r.get("gst_number"));
        c.setPanNumber((String) r.get("pan_number"));
        c.setAddress((String) r.get("address"));
        c.setAddressLine2((String) r.get("address_line2"));
        c.setCity((String) r.get("city"));
        c.setState((String) r.get("state"));
        c.setCountry((String) r.get("country"));
        c.setPincode((String) r.get("pincode"));
        c.setCurrency((String) r.get("currency"));
        c.setPaymentTerms((String) r.get("payment_terms"));
        c.setActive(Boolean.TRUE.equals(r.get("is_active")));
        c.setCreatedAt(r.get("created_at") != null ? r.get("created_at").toString() : null);
        c.setCreatedBy(str(r.get("created_by")));
        c.setCreatedDate(r.get("CreatedDate") != null ? r.get("CreatedDate").toString() : null);
        c.setModifiedBy(str(r.get("modified_by")));
        c.setModifiedDate(r.get("ModifiedDate") != null ? r.get("ModifiedDate").toString() : null);
        return c;
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

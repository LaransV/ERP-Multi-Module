package com.nexerp.modules.auth.mapper;

import com.nexerp.modules.auth.dto.response.CompanyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CompanyDirectoryMapper {

    public List<CompanyDto> toCompanies(List<Map<String, Object>> rows) {
        return rows.stream().map(r -> {
            CompanyDto c = new CompanyDto();
            c.setCompanyId(((Number) r.get("company_id")).intValue());
            c.setCompanyName((String) r.get("company_name"));
            c.setCurrency((String) r.get("currency"));
            c.setGstin((String) r.get("gstin"));
            return c;
        }).collect(Collectors.toList());
    }
}

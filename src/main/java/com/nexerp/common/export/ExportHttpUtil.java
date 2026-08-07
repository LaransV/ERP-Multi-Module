package com.nexerp.common.export;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * One-liner helpers so every controller's export endpoint looks the same:
 *
 * <pre>
 *   byte[] excel = exportService.exportExcel("Clients", headers, rows);
 *   return ExportHttpUtil.excelResponse(excel, "clients.xlsx");
 * </pre>
 */
public final class ExportHttpUtil {

    private ExportHttpUtil() { }

    public static ResponseEntity<byte[]> excelResponse(byte[] content, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    public static ResponseEntity<byte[]> pdfResponse(byte[] content, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }
}

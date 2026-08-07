package com.nexerp.common.export;

import java.util.List;

/**
 * Common Export contract — Excel &amp; PDF — used by EVERY module's controller.
 * Any screen that needs an "Export" button calls this instead of writing its
 * own POI/OpenPDF code (previously duplicated per-module, e.g. IClientService).
 *
 * Usage from any controller:
 * <pre>
 *   List&lt;String&gt; headers = Arrays.asList("#", "Name", "Email");
 *   List&lt;List&lt;String&gt;&gt; rows = items.stream()
 *       .map(i -&gt; Arrays.asList(String.valueOf(i.getId()), i.getName(), i.getEmail()))
 *       .collect(Collectors.toList());
 *   byte[] excel = exportService.exportExcel("Clients", headers, rows);
 * </pre>
 */
public interface IExportService {

    /** Builds an .xlsx workbook with a single styled sheet. */
    byte[] exportExcel(String sheetTitle, List<String> headers, List<List<String>> rows);

    /** Builds a landscape A4 PDF with a title and a styled table. */
    byte[] exportPdf(String title, List<String> headers, List<List<String>> rows);

    /** Same as {@link #exportPdf} but with explicit relative column widths (must match headers.size()). */
    byte[] exportPdf(String title, List<String> headers, List<List<String>> rows, float[] columnWidths);
}

package com.nexerp.common.export;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Shared Excel/PDF export logic — replaces the per-module copies that used
 * to live inside individual services (e.g. IClientService.exportClientsExcel/Pdf).
 * Brand colour + layout kept identical to what those screens already used.
 */
@Slf4j
@Service
public class ExportServiceImpl implements IExportService {

    private static final byte[] BRAND_RGB = {(byte) 0xC0, (byte) 0x39, (byte) 0x3B};
    private static final Color BRAND_COLOR = new Color(0xC0, 0x39, 0x3B);

    @Override
    public byte[] exportExcel(String sheetTitle, List<String> headers, List<List<String>> rows) {
        log.info("exportExcel START | sheet={}, rows={}", sheetTitle, rows.size());
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(sheetTitle);

            CellStyle headerStyle = wb.createCellStyle();
            XSSFFont headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            ((XSSFCellStyle) headerStyle).setFillForegroundColor(new XSSFColor(BRAND_RGB, null));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row hRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = hRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, i == 0 ? 1500 : 5500);
            }

            int rowNum = 1;
            for (List<String> rowData : rows) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < rowData.size(); i++) {
                    row.createCell(i).setCellValue(rowData.get(i) != null ? rowData.get(i) : "");
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            log.info("exportExcel END | sheet={}", sheetTitle);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("exportExcel | Exception occurred | sheet={}", sheetTitle, e);
            throw new RuntimeException("Failed to generate Excel export", e);
        }
    }

    @Override
    public byte[] exportPdf(String title, List<String> headers, List<List<String>> rows) {
        float[] widths = new float[headers.size()];
        java.util.Arrays.fill(widths, 1f);
        return exportPdf(title, headers, rows, widths);
    }

    @Override
    public byte[] exportPdf(String title, List<String> headers, List<List<String>> rows, float[] columnWidths) {
        log.info("exportPdf START | title={}, rows={}", title, rows.size());
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD, BRAND_COLOR);
            doc.add(new Paragraph(title, titleFont));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(headers.size());
            table.setWidthPercentage(100);
            table.setWidths(columnWidths);

            Font headerFont = new Font(Font.HELVETICA, 9, Font.BOLD, Color.WHITE);
            Font cellFont = new Font(Font.HELVETICA, 8);

            for (String col : headers) {
                PdfPCell hCell = new PdfPCell(new Phrase(col, headerFont));
                hCell.setBackgroundColor(BRAND_COLOR);
                hCell.setPadding(5);
                table.addCell(hCell);
            }

            int idx = 0;
            for (List<String> rowData : rows) {
                Color rowBg = (idx++ % 2 == 0) ? new Color(0xF5, 0xF5, 0xF5) : Color.WHITE;
                for (String val : rowData) {
                    PdfPCell cell = new PdfPCell(new Phrase(val != null ? val : "", cellFont));
                    cell.setBackgroundColor(rowBg);
                    cell.setPadding(4);
                    table.addCell(cell);
                }
            }
            doc.add(table);
            doc.close();
            log.info("exportPdf END | title={}", title);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("exportPdf | Exception occurred | title={}", title, e);
            throw new RuntimeException("Failed to generate PDF export", e);
        }
    }
}

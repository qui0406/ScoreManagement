package com.scm.helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.scm.dto.responses.WriteScoreStudentPDFResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class PDFHelper {
    private static final int DEFAULT_COLUMNS = 4;

    public static byte[] exportScoreListToPDFBytes(List<WriteScoreStudentPDFResponse> scoreList,
                                                   List<String> listScoreTypeName) throws Exception {
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        Paragraph title = new Paragraph("DANH SÁCH ĐIỂM SINH VIÊN", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        int columns = listScoreTypeName.size();
        PdfPTable table = new PdfPTable(columns + DEFAULT_COLUMNS);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        BaseColor headerBgColor = new BaseColor(0, 121, 182);

        addTableHeader(table, "STT", headerFont, headerBgColor);
        addTableHeader(table, "Họ và tên", headerFont, headerBgColor);
        addTableHeader(table, "Mã số sinh viên", headerFont, headerBgColor);
        addTableHeader(table, "Lớp", headerFont, headerBgColor);

        for (String scoreType : listScoreTypeName) {
            addTableHeader(table, scoreType, headerFont, headerBgColor);
        }

        int index = 1;
        for (WriteScoreStudentPDFResponse item : scoreList) {
            table.addCell(new PdfPCell(new Phrase(String.valueOf(index++), cellFont)));
            table.addCell(new PdfPCell(new Phrase(item.getFullName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(item.getMssv(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(item.getClassName(), cellFont)));

            Map<Integer, BigDecimal> scores = item.getScores();
            for (int i = 1; i <= columns; i++) {
                String value = scores.containsKey(i) ? String.valueOf(scores.get(i)) : "";
                table.addCell(new PdfPCell(new Phrase(value, cellFont)));
            }
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }


    private static void addTableHeader(PdfPTable table, String headerTitle, Font font, BaseColor bgColor) {
        PdfPCell header = new PdfPCell(new Phrase(headerTitle, font));
        header.setBackgroundColor(bgColor);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header.setPadding(5f);
        table.addCell(header);
    }
}

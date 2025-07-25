package com.scm.helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.scm.dto.requests.ListScoreStudentRequest;
import jakarta.persistence.Table;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PDFHelper {
    public static void exportScoreListToPDF(List<ListScoreStudentRequest> scoreList) throws Exception {
        Document document = new Document();
        String path = "C:/Users/QUI/Desktop/iTextTable.pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        PdfPTable table = new PdfPTable(10); // 3 cột đầu + 7 cột điểm

        table.addCell("STT");
        table.addCell("MSSV");
        table.addCell("Class name");
        for (int i = 1; i <= 7; i++) {
            table.addCell("Score " + i);
        }

        int index = 1;
        for (ListScoreStudentRequest item : scoreList) {
            table.addCell(String.valueOf(index++));
            table.addCell(item.getStudentId());
            table.addCell(item.getClassSubjectId());

            Map<Integer, BigDecimal> scores = item.getScores();
            for (int i = 1; i <= 7; i++) {
                table.addCell(scores.containsKey(i) ? String.valueOf(scores.get(i)) : "");
            }
        }

        document.add(table);
        document.close();
    }
}

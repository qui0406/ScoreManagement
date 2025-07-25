package com.scm.helpers;

import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvValidationException;
import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ScoreRequest;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class CSVHelper {


    public static List<ListScoreStudentRequest> parseScoreCSV(MultipartFile inputFile) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputFile.getInputStream()))) {
            String[] headers = csvReader.readNext();
            List<ListScoreStudentRequest> resultList = new ArrayList<>();
            String[] row;

            while ((row = csvReader.readNext()) != null) {
                Map<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < headers.length && i < row.length; i++) {
                    rowMap.put(headers[i].trim(), row[i].trim());
                }

                ListScoreStudentRequest request = new ListScoreStudentRequest();
                request.setStudentId(rowMap.get("studentId"));
                request.setClassSubjectId(rowMap.get("classSubjectId"));

                Map<Integer, BigDecimal> scores = new HashMap<>();
                for (int i = 1; i <= 7; i++) {
                    String col = "score" + i;
                    if (rowMap.containsKey(col)) {
                        String val = rowMap.get(col);
                        if (val != null && !val.isEmpty()) {
                            try {
                                scores.put(i, new BigDecimal(val));
                            } catch (NumberFormatException e) {
                                // skip invalid score
                            }
                        }
                    }
                }

                request.setScores(scores);
                resultList.add(request);
            }

            return resultList;

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeScores(String filePath, List<ScoreRequest> scores) {
        try (Writer writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<ScoreRequest> beanToCsv = new StatefulBeanToCsvBuilder<ScoreRequest>(writer)
                    .withSeparator(',')
                    .build();

            beanToCsv.write(scores);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi ghi CSV: " + e.getMessage(), e);
        }
    }
}

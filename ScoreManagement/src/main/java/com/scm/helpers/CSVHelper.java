package com.scm.helpers;

import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvValidationException;
import com.scm.dto.requests.ListScoreStudentRequest;
import com.scm.dto.requests.ReadFileCSVRequest;
import com.scm.dto.requests.ScoreRequest;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scm.pojo.enums.ScoreTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class CSVHelper {
    public static List<ReadFileCSVRequest> parseScoreCSV(MultipartFile inputFile) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputFile.getInputStream()))) {
            String[] headers = csvReader.readNext();
            List<ReadFileCSVRequest> resultList = new ArrayList<>();
            String[] row;

            while ((row = csvReader.readNext()) != null) {
                Map<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < headers.length && i < row.length; i++) {
                    rowMap.put(headers[i].trim(), row[i].trim());
                }

                ReadFileCSVRequest request = new ReadFileCSVRequest();
                request.setFullName(rowMap.get("fullName"));
                request.setMssv(rowMap.get("mssv"));
                request.setClassName(rowMap.get("className"));

                Map<Integer, BigDecimal> scores = new HashMap<>();
                for (ScoreTypeEnum scoreType : ScoreTypeEnum.values()) {
                    String columnName = scoreType.name(); // Sử dụng trực tiếp tên enum
                    if (rowMap.containsKey(columnName)) {
                        String scoreValue = rowMap.get(columnName);
                        if (scoreValue != null && !scoreValue.isEmpty()) {
                            try {
                                scores.put(scoreType.getValue(), new BigDecimal(scoreValue));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
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


}

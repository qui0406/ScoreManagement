package com.scm.helpers;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;
import com.scm.dto.requests.CSVScoreRequest;
import com.scm.dto.requests.ScoreRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class CSVHelper {
    public static List<CSVScoreRequest> parseScoreCSV(InputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            CsvToBean<CSVScoreRequest> csvToBean = new CsvToBeanBuilder<CSVScoreRequest>(reader)
                .withType(CSVScoreRequest.class)
                    .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(1)
                .build();
            log.info(csvToBean.toString());
            return csvToBean.parse();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
        }
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

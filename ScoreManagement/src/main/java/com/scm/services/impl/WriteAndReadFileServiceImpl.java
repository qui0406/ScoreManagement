package com.scm.services.impl;

import com.scm.dto.responses.ScoreTypeResponse;
import com.scm.dto.responses.WriteScoreStudentPDFResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.helpers.PDFHelper;
import com.scm.services.ScoreStudentService;
import com.scm.services.ScoreTypeService;
import com.scm.services.WriteAndReadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriteAndReadFileServiceImpl implements WriteAndReadFileService {
    @Autowired
    private ScoreStudentService scoreStudentService;
    @Autowired
    private ScoreTypeService scoreTypeService;


    @Override
    public byte[] exportScoresToPDF(String classDetailId, String teacherId) {
        List<WriteScoreStudentPDFResponse> request = scoreStudentService.listScorePDF(classDetailId, teacherId);
        List<ScoreTypeResponse> listScoreType = scoreTypeService.getScoreTypesByClassDetails(classDetailId);
        List<String> listScoreTypeName = listScoreType.stream()
                .map(ScoreTypeResponse::getScoreTypeName)
                .collect(Collectors.toList());
        try {
            return PDFHelper.exportScoreListToPDFBytes(request, listScoreTypeName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    return null;}
}

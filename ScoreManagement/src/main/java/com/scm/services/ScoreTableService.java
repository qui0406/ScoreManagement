package com.scm.services;

import com.scm.dto.requests.ScoreTableRequest;
import com.scm.dto.responses.ScoreTableResponse;
import com.scm.pojo.Score;

import java.util.List;


public interface ScoreTableService {
    List<ScoreTableResponse> getScoreSubjectByStudentId(ScoreTableRequest scoreTableRequest);
}

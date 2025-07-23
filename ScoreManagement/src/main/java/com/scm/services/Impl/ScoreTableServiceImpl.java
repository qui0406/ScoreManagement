package com.scm.services.Impl;

import com.scm.dto.requests.ScoreTableRequest;
import com.scm.dto.responses.ScoreResponse;
import com.scm.dto.responses.ScoreTableResponse;
import com.scm.mapper.ScoreTableMapper;
import com.scm.pojo.Score;
import com.scm.repositories.ScoreTableRepository;
import com.scm.services.ScoreTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreTableServiceImpl implements ScoreTableService {
    @Autowired
    private ScoreTableMapper scoreTableMapper;
    @Autowired
    private ScoreTableRepository scoreTableRepository;
    @Override
    public ScoreTableResponse getScoreSubjectByStudentId(ScoreTableRequest scoreTableRequest) {
        Score score = scoreTableRepository.getScoreSubjectByStudentId(
                scoreTableRequest.getStudentId(),
                scoreTableRequest.getClassSubjectId(),
                scoreTableRequest.getTeacherId()
        );
        ScoreTableResponse response = scoreTableMapper.toScoreTableResponse(score);

        return response;
    }

    @Override
    public List<ScoreTableResponse> getAllStudentsInClassSubject(String classSubjectId, String teacherId) {
        List<Score> scores = scoreTableRepository.getAllStudentsInClass(classSubjectId, teacherId);

        List<ScoreTableResponse> scoreResponses = new ArrayList<>();
        for(Score score : scores){
            ScoreTableResponse response = scoreTableMapper.toScoreTableResponse(score);
            scoreResponses.add(response);
        }
        return scoreResponses;
    }
}

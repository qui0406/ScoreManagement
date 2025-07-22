package com.scm.services.Impl;

import com.scm.dto.requests.ScoreTableRequest;
import com.scm.dto.responses.ScoreTableResponse;
import com.scm.mapper.ScoreTableMapper;
import com.scm.pojo.Score;
import com.scm.repositories.ScoreTableRepository;
import com.scm.services.ScoreTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreTableServiceImpl implements ScoreTableService {
    @Autowired
    private ScoreTableMapper scoreTableMapper;
    @Autowired
    private ScoreTableRepository scoreTableRepository;
    @Override
    public List<ScoreTableResponse> getScoreSubjectByStudentId(ScoreTableRequest scoreTableRequest) {
        List<Score> scores = scoreTableRepository.getScoreSubjectByStudentId(
                scoreTableRequest.getStudentId(),
                scoreTableRequest.getClassSubjectId(),
                scoreTableRequest.getTeacherId()
        );

        return scores.stream()
                .map(scoreTableMapper::toScoreTableResponse)
                .collect(Collectors.toList());
    }

}

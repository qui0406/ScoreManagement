//package com.scm.mapper.decorator;
//
//import com.scm.dto.*;
//import com.scm.dto.requests.ScoreRequest;
//import com.scm.dto.responses.ScoreStudentResponse;
//import com.scm.mapper.ScoreStudentMapper;
//import com.scm.pojo.ClassDetails;
//import com.scm.pojo.Score;
//import com.scm.pojo.Student;
//import com.scm.repositories.*;
//import com.scm.services.ScoreService;
//import com.scm.services.TeacherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class ScoreStudentMapperDecorator implements ScoreStudentMapper {
//    @Autowired
//    @Qualifier("delegate")
//
//
//    @Override
//    public ScoreStudentResponse toScoreResponse(Score score) {
//            return null;
//    }
//
//    @Override
//    public Score toScore(ScoreRequest scoreRequest) {
//        return null;
//    }
//
//
//
//}

package com.scm.mapper;

import com.scm.dto.ClassroomDTO;
import com.scm.dto.StudentDTO;
import com.scm.dto.SubjectDTO;
import com.scm.dto.requests.ClassroomSubjectRequest;
import com.scm.dto.responses.ClassroomSubjectResponse;
import com.scm.dto.responses.ScoreStudentResponse;
import com.scm.pojo.ClassSubject;
import com.scm.pojo.Classroom;
import com.scm.pojo.Semester;
import com.scm.pojo.Student;
import com.scm.pojo.StudentEnrollment;
import com.scm.pojo.Subject;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-25T16:13:59+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ClassroomSubjectMapperImpl_ implements ClassroomSubjectMapper {

    @Override
    public ClassSubject toClassroomSubject(ClassroomSubjectRequest dto) {
        if ( dto == null ) {
            return null;
        }

        ClassSubject classSubject = new ClassSubject();

        return classSubject;
    }

    @Override
    public ClassroomSubjectResponse toClassroomSubjectResponse(ClassSubject dto) {
        if ( dto == null ) {
            return null;
        }

        ClassroomSubjectResponse classroomSubjectResponse = new ClassroomSubjectResponse();

        classroomSubjectResponse.setId( dto.getId() );
        classroomSubjectResponse.setSubject( subjectToSubjectDTO( dto.getSubject() ) );
        classroomSubjectResponse.setSemester( semesterToSemesterDTO( dto.getSemester() ) );
        classroomSubjectResponse.setClassroom( classroomToClassroomDTO( dto.getClassroom() ) );

        return classroomSubjectResponse;
    }

    @Override
    public ScoreStudentResponse toScoreTableResponse(StudentEnrollment studentEnrollment) {
        if ( studentEnrollment == null ) {
            return null;
        }

        ScoreStudentResponse scoreStudentResponse = new ScoreStudentResponse();

        scoreStudentResponse.setStudent( studentToStudentDTO( studentEnrollment.getStudent() ) );

        return scoreStudentResponse;
    }

    protected SubjectDTO subjectToSubjectDTO(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setId( subject.getId() );
        subjectDTO.setSubjectName( subject.getSubjectName() );

        return subjectDTO;
    }

    protected ClassroomSubjectResponse.SemesterDTO semesterToSemesterDTO(Semester semester) {
        if ( semester == null ) {
            return null;
        }

        ClassroomSubjectResponse.SemesterDTO semesterDTO = new ClassroomSubjectResponse.SemesterDTO();

        semesterDTO.setName( semester.getName() );

        return semesterDTO;
    }

    protected ClassroomDTO classroomToClassroomDTO(Classroom classroom) {
        if ( classroom == null ) {
            return null;
        }

        ClassroomDTO classroomDTO = new ClassroomDTO();

        classroomDTO.setId( classroom.getId() );
        classroomDTO.setName( classroom.getName() );

        return classroomDTO;
    }

    protected StudentDTO studentToStudentDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setMssv( student.getMssv() );

        return studentDTO;
    }
}

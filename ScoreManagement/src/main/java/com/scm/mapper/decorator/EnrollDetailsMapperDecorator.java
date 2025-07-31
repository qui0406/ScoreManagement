package com.scm.mapper.decorator;

import com.scm.dto.requests.EnrollClassRequest;
import com.scm.mapper.ClassDetailMapper;
import com.scm.mapper.EnrollDetailsMapper;
import com.scm.pojo.ClassDetails;
import com.scm.pojo.EnrollDetails;
import com.scm.pojo.Student;
import com.scm.repositories.ClassDetailsRepository;
import com.scm.repositories.StudentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class EnrollDetailsMapperDecorator implements EnrollDetailsMapper {
    @Autowired
    @Qualifier("delegate")
    private EnrollDetailsMapper delegate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Override
    public EnrollDetails toEnrollDetails(EnrollClassRequest request) {
        EnrollDetails enrollDetails = new EnrollDetails();

        Student student = studentRepository.findById(request.getStudentId());
        ClassDetails classDetails = classDetailsRepository.findById(request.getClassDetailId());

        enrollDetails.setStudent(student);
        enrollDetails.setClassDetails(classDetails);
        return enrollDetails;
    }
}

package com.scm.services.impl;

import com.scm.dto.requests.FacultyRequest;
import com.scm.dto.responses.FacultyResponse;
import com.scm.mapper.FacultyMapper;
import com.scm.pojo.Faculty;
import com.scm.repositories.FacultyRepository;
import com.scm.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyMapper facultyMapper;

    @Override
    public void create(FacultyRequest request) {
        this.facultyRepository.create(this.facultyMapper.toFaculty(request));
    }

    @Override
    public void delete(String id) {
        Faculty faculty = this.facultyRepository.findById(id);
        this.facultyRepository.delete(faculty);
    }

    @Override
    public List<FacultyResponse> getAllFaculties() {
        return this.facultyMapper.toFacultyRequest(this.facultyRepository.findAllFaculties());
    }
}

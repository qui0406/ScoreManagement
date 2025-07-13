package com.scm.repositories;


import com.scm.pojo.Classroom;

import java.util.List;

public interface ClassRoomRepository {
    List<Classroom> getListClassRoom();
    Classroom getClassRoomById(Integer classroomId);
}

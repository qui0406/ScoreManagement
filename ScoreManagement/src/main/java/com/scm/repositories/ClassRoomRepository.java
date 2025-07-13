package com.scm.repositories;


import com.scm.pojo.ClassRoom;

import java.util.List;

public interface ClassRoomRepository {
    List<ClassRoom> getListClassRoom();
    ClassRoom getClassRoomById(Integer classroomId);
}

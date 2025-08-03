package com.scm.repositories;

import com.scm.pojo.Conversation;
import com.scm.pojo.Student;
import com.scm.pojo.Teacher;

import java.util.List;

public interface ConversationRepository {
    void create(Conversation conversation);
    void delete(Conversation conversation);

    Conversation findById(String id);

    List<Conversation> getAllConversationsByUserId(String userId);

    List<Conversation> findByTeacher(Teacher teacher);
    List<Conversation> findByStudent(Student teacher);

    List<Conversation> findByTeacherOrStudent(Teacher teacher, Student student);
}

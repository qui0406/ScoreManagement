package com.scm.repositories;

import com.scm.pojo.Forum;
import com.scm.pojo.ForumDetails;

import java.util.List;

public interface ForumRepository {
    void create(Forum forum);
    void delete(String id);
    void update(Forum forum);
    Forum findById(String id);

    List<Forum> getAllForumsByClassDetailId(String classDetailId);
}

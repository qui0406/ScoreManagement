package com.scm.repositories;

import com.scm.dto.responses.ForumDetailsResponse;
import com.scm.pojo.ForumDetails;

import java.util.List;
import java.util.Map;

public interface ForumDetailsRepository {
    void create(ForumDetails forumDetails);

    void delete(String id);

    void update(ForumDetails forumDetails);

    ForumDetails findById(String id);

    List<ForumDetails> findAllByForumId(Map<String,String> params);
}

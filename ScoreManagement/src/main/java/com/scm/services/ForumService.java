package com.scm.services;

import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumResponse;
import com.scm.pojo.Forum;

import java.util.List;

public interface ForumService {
    void create(ForumRequest request, String userCreatedId);
    void delete(String id, String userCreatedId);
    void update(String id);
    List<ForumResponse> getAllForumsByClassDetailId(String classDetailId, String userId);
}

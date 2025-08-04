package com.scm.services;

import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumDetailsResponse;
import com.scm.pojo.ForumDetails;

import java.util.List;
import java.util.Map;

public interface ForumDetailsService {
    void create(ForumDetailsRequest request, String forumId, String userResponseId);
    void delete(String forumDetailId,  String userResponseId);
    void update(ForumDetailsRequest request, String forumDetailId, String userResponseId);

    List<ForumDetailsResponse> findAllByForumId(Map<String,String> params);

    List<ForumDetailsResponse> getAllForumByForumId(String forumId);
}

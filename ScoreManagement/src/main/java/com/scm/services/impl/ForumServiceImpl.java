package com.scm.services.impl;

import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ForumMapper;
import com.scm.pojo.Forum;
import com.scm.pojo.ForumDetails;
import com.scm.repositories.ForumRepository;
import com.scm.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumMapper forumMapper;

    @Override
    public void create(ForumRequest request, String userCreatedId) {
        request.setUserCreatedId(userCreatedId);
        this.forumRepository.create(forumMapper.toForum(request));
    }

    @Override
    public void delete(String id,  String userCreatedId) {
        Forum f = forumRepository.findById(userCreatedId);
        if (!f.getUser().getId().toString().equals(userCreatedId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        this.forumRepository.delete(id);
    }

    @Override
    public void update(String id) {
        Forum f = forumRepository.findById(id);
        this.forumRepository.update(f);
    }

    @Override
    public List<ForumResponse> getAllForumsByClassDetailId(String classDetailId) {
        List<Forum> forums = this.forumRepository.getAllForumsByClassDetailId(classDetailId);
        List<ForumResponse> responses = new ArrayList<>();

        for (Forum forum : forums) {
            responses.add(forumMapper.toResponse(forum));
        }
        return responses;
    }
}

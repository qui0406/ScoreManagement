package com.scm.services.impl;

import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumDetailsResponse;
import com.scm.exceptions.AppException;
import com.scm.exceptions.ErrorCode;
import com.scm.mapper.ForumDetailsMapper;
import com.scm.mapper.ForumMapper;
import com.scm.pojo.ForumDetails;
import com.scm.repositories.ForumDetailsRepository;
import com.scm.services.ForumDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ForumDetailsServiceImpl implements ForumDetailsService {
    @Autowired
    private ForumDetailsRepository forumDetailsRepository;

    @Autowired
    private ForumDetailsMapper forumDetailsMapper;

    @Override
    public void create(ForumDetailsRequest request, String forumId, String userResponseId) {
        request.setForumId(forumId);
        request.setUserResponseId(userResponseId);
        this.forumDetailsRepository.create(forumDetailsMapper.toForum(request));
    }

    @Override
    public void delete(String forumDetailId, String userResponseId) {
        ForumDetails f = this.forumDetailsRepository.findById(forumDetailId);

        if(f.getUser().getId().toString().equals(userResponseId)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        this.forumDetailsRepository.delete(forumDetailId);
    }

    @Override
    public void update(ForumDetailsRequest request, String forumDetailId, String userResponseId) {
        ForumDetails f = this.forumDetailsRepository.findById(forumDetailId);

        if(!f.getUser().getId().toString().equals(userResponseId)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        f.setMessage(request.getMessage());
        f.setCreatedAt(LocalDateTime.now());

        this.forumDetailsRepository.update(f);
    }

    @Override
    public List<ForumDetailsResponse> findAllByForumId(Map<String, String> params) {
        List<ForumDetails> forumDetails = this.forumDetailsRepository.findAllByForumId(params);

        List<ForumDetailsResponse> responses = new ArrayList<>();
        for (ForumDetails forumDetail : forumDetails) {
            responses.add(this.forumDetailsMapper.toForumDetailsResponse(forumDetail));
        }
        return responses;
    }

    @Override
    public List<ForumDetailsResponse> getAllForumByForumId(String forumId) {
        List<ForumDetails> forumDetailsList = this.forumDetailsRepository.getAllByForumId(forumId);
        List<ForumDetailsResponse> responses = new ArrayList<>();
        for (ForumDetails forumDetail : forumDetailsList) {
            responses.add(this.forumDetailsMapper.toForumDetailsResponse(forumDetail));
        }
        return responses;
    }
}

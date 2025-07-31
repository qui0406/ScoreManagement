package com.scm.mapper;

import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.responses.ForumDetailsResponse;
import com.scm.mapper.decorator.ForumDetailsMapperDecorator;
import com.scm.pojo.Forum;
import com.scm.pojo.ForumDetails;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(ForumDetailsMapperDecorator.class)
public interface ForumDetailsMapper {
    ForumDetailsResponse toForumDetailsResponse(ForumDetails forum);

    ForumDetails toForum(ForumDetailsRequest request);
}

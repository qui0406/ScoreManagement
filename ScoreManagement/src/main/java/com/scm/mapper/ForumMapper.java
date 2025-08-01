package com.scm.mapper;

import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumResponse;
import com.scm.mapper.decorator.ForumMapperDecorator;
import com.scm.pojo.Forum;
import com.scm.pojo.ForumDetails;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(ForumMapperDecorator.class)
public interface ForumMapper {
    ForumResponse toResponse(Forum forum);

    Forum toForum(ForumRequest request);
}

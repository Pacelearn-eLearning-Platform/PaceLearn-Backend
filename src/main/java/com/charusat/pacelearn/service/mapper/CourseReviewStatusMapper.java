package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseReviewStatus;
import com.charusat.pacelearn.service.dto.CourseReviewStatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CourseReviewStatus} and its DTO {@link CourseReviewStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, com.charusat.pacelearn.service.mapper.CourseSessionMapper.class })
public interface CourseReviewStatusMapper extends EntityMapper<CourseReviewStatusDTO, CourseReviewStatus> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "courseSession", source = "courseSession", qualifiedByName = "sessionTitle")
    CourseReviewStatusDTO toDto(CourseReviewStatus s);
}

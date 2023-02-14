package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseSessionProgress;
import com.charusat.pacelearn.service.dto.CourseSessionProgressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CourseSessionProgress} and its DTO {@link CourseSessionProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CourseSessionMapper.class })
public interface CourseSessionProgressMapper extends EntityMapper<CourseSessionProgressDTO, CourseSessionProgress> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "courseSession", source = "courseSession", qualifiedByName = "sessionTitle")
    CourseSessionProgressDTO toDto(CourseSessionProgress s);
}

package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseSession;
import com.charusat.pacelearn.service.dto.CourseSessionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CourseSession} and its DTO {@link CourseSessionDTO}.
 */
@Mapper(componentModel = "spring", uses = { com.charusat.pacelearn.service.mapper.CourseSectionMapper.class })
public interface CourseSessionMapper extends EntityMapper<CourseSessionDTO, CourseSession> {
    @Mapping(target = "courseSection", source = "courseSection", qualifiedByName = "sectionTitle")
    CourseSessionDTO toDto(CourseSession s);

    @Named("sessionTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sessionTitle", source = "sessionTitle")
    CourseSessionDTO toDtoSessionTitle(CourseSession courseSession);
}

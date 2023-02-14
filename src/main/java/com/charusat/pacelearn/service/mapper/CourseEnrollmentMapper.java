package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseEnrollment;
import com.charusat.pacelearn.service.dto.CourseEnrollmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CourseEnrollment} and its DTO {@link CourseEnrollmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, com.charusat.pacelearn.service.mapper.CourseMapper.class })
public interface CourseEnrollmentMapper extends EntityMapper<CourseEnrollmentDTO, CourseEnrollment> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    @Mapping(target = "course", source = "course", qualifiedByName = "id")
    CourseEnrollmentDTO toDto(CourseEnrollment s);
}

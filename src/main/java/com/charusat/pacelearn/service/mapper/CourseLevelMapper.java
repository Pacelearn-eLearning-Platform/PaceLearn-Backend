package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseLevel;
import com.charusat.pacelearn.service.dto.CourseLevelDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CourseLevel} and its DTO {@link CourseLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseLevelMapper extends EntityMapper<CourseLevelDTO, CourseLevel> {
    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    CourseLevelDTO toDtoTitle(CourseLevel courseLevel);
}

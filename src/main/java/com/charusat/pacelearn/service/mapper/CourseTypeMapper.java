package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseType;
import com.charusat.pacelearn.service.dto.CourseTypeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CourseType} and its DTO {@link CourseTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseTypeMapper extends EntityMapper<CourseTypeDTO, CourseType> {
    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    CourseTypeDTO toDtoTitle(CourseType courseType);
}

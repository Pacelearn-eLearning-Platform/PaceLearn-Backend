package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseCategory;
import com.charusat.pacelearn.service.dto.CourseCategoryDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CourseCategory} and its DTO {@link CourseCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseCategoryMapper extends EntityMapper<CourseCategoryDTO, CourseCategory> {
    @Named("courseCategoryTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "courseCategoryTitle", source = "courseCategoryTitle")
    CourseCategoryDTO toDtoCourseCategoryTitle(CourseCategory courseCategory);
}

package com.charusat.pacelearn.service.mapper;

import com.charusat.pacelearn.domain.CourseSection;
import com.charusat.pacelearn.service.dto.CourseSectionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CourseSection} and its DTO {@link CourseSectionDTO}.
 */
@Mapper(componentModel = "spring", uses = { CourseMapper.class })
public interface CourseSectionMapper extends EntityMapper<CourseSectionDTO, CourseSection> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseTitle")
    CourseSectionDTO toDto(CourseSection s);

    @Named("sectionTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sectionTitle", source = "sectionTitle")
    CourseSectionDTO toDtoSectionTitle(CourseSection courseSection);
}

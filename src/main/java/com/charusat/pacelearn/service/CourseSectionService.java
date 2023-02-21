package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.CourseSection;
import com.charusat.pacelearn.service.dto.CourseSectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.charusat.pacelearn.domain.CourseSection}.
 */
public interface CourseSectionService {
    /**
     * Save a courseSection.
     *
     * @param courseSectionDTO the entity to save.
     * @return the persisted entity.
     */
    CourseSectionDTO save(CourseSectionDTO courseSectionDTO);

    /**
     * Partially updates a courseSection.
     *
     * @param courseSectionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseSectionDTO> partialUpdate(CourseSectionDTO courseSectionDTO);

    /**
     * Get all the courseSections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseSectionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseSection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseSectionDTO> findOne(Long id);

    List<CourseSection> findSectionsByCourse(Long id);

    List<CourseSection> findCourseSectionsByCourse(Long id);

    CourseSection save(Long courseId, CourseSectionDTO courseSectionDTO);

    /**
     * Delete the "id" courseSection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

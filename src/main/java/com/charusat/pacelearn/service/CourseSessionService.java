package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.CourseSession;
import com.charusat.pacelearn.service.dto.CourseSessionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.charusat.pacelearn.domain.CourseSession}.
 */
public interface CourseSessionService {
    /**
     * Save a courseSession.
     *
     * @param courseSessionDTO the entity to save.
     * @return the persisted entity.
     */
    CourseSessionDTO save(CourseSessionDTO courseSessionDTO);

    /**
     * Partially updates a courseSession.
     *
     * @param courseSessionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseSessionDTO> partialUpdate(CourseSessionDTO courseSessionDTO);

    /**
     * Get all the courseSessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseSessionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseSession.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseSessionDTO> findOne(Long id);

    /**
     * Delete the "id" courseSession.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CourseSession> findSessionByCourseSection(Long id);

    List<CourseSession> findCourseSessionsByCourseSectionIn(Long id);
}

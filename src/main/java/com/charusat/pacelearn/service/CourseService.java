package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.User;
import com.charusat.pacelearn.service.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing {@link com.charusat.pacelearn.domain.Course}.
 */
@Transactional(readOnly = true)
public interface CourseService {
    /**
     * Save a course.
     *
     * @param courseDTO the entity to save.
     * @return the persisted entity.
     */
    CourseDTO save(CourseDTO courseDTO);

    /**
     * Partially updates a course.
     *
     * @param courseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseDTO> partialUpdate(CourseDTO courseDTO);

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
//    Page<CourseDTO> findAllOpen(Pageable pageable);
    List<Course> findAllOpen();

    /**
     * Get the "id" course.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseDTO> findOne(Long id);

    /**
     * Delete the "id" course.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get courses by current semester
     *
     * */
    //    List<CourseDTO> findAllByCurrentSemester();

    List<CourseDTO> getEnrolledCourses() throws Exception;

    /**
     * CUSTOM
     * */
    List<CourseDTO> getByCategoryId(Long id) throws Exception;

    ResponseEntity<Map<String,Integer>> getStudentEnrolledCountByCourse(Long courseId);

    ResponseEntity enrollInCourse(String courseId);

    ResponseEntity<Map<String, String>> getOverview();

    ResponseEntity<Set<User>> getEnrolledUsersByCourseId(Long courseId);

    List<Course> findAll();

    Course approveCourse(Long courseId);
    Course disApproveCourse(Long courseId);
}

package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.CourseSection;
import com.charusat.pacelearn.domain.CourseSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the CourseSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Long>, JpaSpecificationExecutor<CourseSession> {

    /**
     * CUSTOM
     * AUTHOR : KIRTAN SHAH
     */
    List<CourseSession> findAllByCourseSection_Id(Long courseSectionId);
    
    
    List<CourseSession> findCourseSessionsByCourseSection(Optional<CourseSection> courseSection);

    List<CourseSession> findCourseSessionsByCourseSectionIn(Collection<CourseSection> courseSection);

    @Query(value = "select count(*) from course_session", nativeQuery = true)
    Integer countAll();

}

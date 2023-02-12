package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the CourseSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Long>, JpaSpecificationExecutor<CourseSection> {
    List<CourseSection> findCourseSectionByCourse(Optional<Course> course);

    List<CourseSection> findCourseSectionsByCourse(Optional<Course> course);
}

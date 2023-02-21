package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.CourseSection;
import com.charusat.pacelearn.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the CourseSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Long>, JpaSpecificationExecutor<CourseSection> {

    /**
     * CUSTOM
     * */
    Page<CourseSection> findCourseSectionByCourse_Id(Long courseId, Pageable pageable);

    Page<CourseSection> findCourseSectionByCourse_IdAndIsApproved(Long courseId, Boolean value, Pageable pageable);

    List<CourseSection> findCourseSectionByCourse_Id(Long courseId);

    Page<CourseSection> findCourseSectionByCourse_User_IdAndCourse_Id(Long userId, Long courseId, Pageable pageable);

    Page<CourseSection> findCourseSectionByCourse_IdAndCourseEnrolledUsersListsContaining(Long courseId, User user, Pageable pageable);

    @Modifying
    @Query(value = "delete from CourseSection courseSection where courseSection.course.id = :courseId")
    void deleteCourseSectionByCourseId(@Param("courseId") Long courseId);

    Integer countCourseSectionByCourseAndIsApproved(Course course, Boolean approvalValue);

    List<CourseSection> findCourseSectionByCourse(Optional<Course> course);

    List<CourseSection> findCourseSectionsByCourse(Optional<Course> course);
}

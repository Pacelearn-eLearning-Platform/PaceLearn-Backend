package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Spring Data SQL repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("select course from Course course where course.user.login = ?#{principal.username}")
    List<Course> findByUserIsCurrentUser();

    //    @Query("select course from Course course where course.semester in (?1, ?2)")
    //    List<Course> findAllBySemester(int a, int b);

    List<Course> findCourseByEnrolledUsersListsContaining(User user);

    @Query(
        value = "select course from Course course where course.courseCategory.id = :id and course.isApproved = true order by course.courseTitle"
    )
    List<Course> findByCategoryId(@Param("id") Long id);

    @Query(value = "select count(*) from rel_course__enrolled_users_list", nativeQuery = true)
    Integer findTotalEnrollment();

    List<Course> findAllByIsApproved(Boolean value);

    List<Course> findCourseByUserEquals(User author);

    /**
     *  CUSTOM
     *  AUTHOR : KIRTAN SHAH
     */

    Integer countCoursesByIsApproved(@NotNull Boolean isApproved);

    @Query(value = "select count(distinct course_id) from rel_course__enrolled_users_list", nativeQuery = true)
    Integer findEnrolledCourses();
}

package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CourseCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long>, JpaSpecificationExecutor<CourseCategory> {
    @Query(
        value = "SELECT course_category from CourseCategory course_category where course_category.isParent = true order by course_category.courseCategoryTitle"
    )
    List<CourseCategory> findParentCategory();

    @Query(
        value = "SELECT course_category from CourseCategory course_category where course_category.parentId = :id and course_category.id in (" +
        "select course.courseCategory.id from Course course" +
        ") order by course_category.courseCategoryTitle"
    )
    List<CourseCategory> findByParentId(@Param("id") Integer id);

    List<CourseCategory> findCourseCategoryByIsParent(Boolean value);

    @Query(
        value = "select count(course) from Course course where course.courseCategory.id = (" +
        "select courseCategory.id from CourseCategory courseCategory where courseCategory.id = :categoryId and courseCategory.isParent = false" +
        ")"
    )
    Integer getCourseCountBySubCategory(@Param("categoryId") Long categoryId);

    @Query(
        value = "select count(course) from Course course where course.courseCategory.id in (" +
        "select courseCategory.id from CourseCategory courseCategory where courseCategory.isParent = false and courseCategory.parentId = :categoryId" +
        ")"
    )
    Integer getCourseCountByParentCategory(@Param("categoryId") Integer categoryId);
}

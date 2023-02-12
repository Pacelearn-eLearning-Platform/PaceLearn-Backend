package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseLevelRepository extends JpaRepository<CourseLevel, Long>, JpaSpecificationExecutor<CourseLevel> {}

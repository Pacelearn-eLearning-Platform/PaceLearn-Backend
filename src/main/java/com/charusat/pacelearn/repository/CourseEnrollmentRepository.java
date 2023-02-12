package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CourseEnrollment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long>, JpaSpecificationExecutor<CourseEnrollment> {
    @Query("select courseEnrollment from CourseEnrollment courseEnrollment where courseEnrollment.user.login = ?#{principal.username}")
    List<CourseEnrollment> findByUserIsCurrentUser();
}

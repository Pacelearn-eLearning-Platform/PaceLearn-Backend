package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.CourseReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CourseReviewStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseReviewStatusRepository
    extends JpaRepository<CourseReviewStatus, Long>, JpaSpecificationExecutor<CourseReviewStatus> {
    @Query(
        "select courseReviewStatus from CourseReviewStatus courseReviewStatus where courseReviewStatus.user.login = ?#{principal.username}"
    )
    List<CourseReviewStatus> findByUserIsCurrentUser();
}

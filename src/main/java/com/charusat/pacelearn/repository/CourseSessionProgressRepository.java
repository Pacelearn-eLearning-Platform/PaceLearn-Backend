package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.CourseSessionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CourseSessionProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSessionProgressRepository
    extends JpaRepository<CourseSessionProgress, Long>, JpaSpecificationExecutor<CourseSessionProgress> {
    @Query(
        "select courseSessionProgress from CourseSessionProgress courseSessionProgress where courseSessionProgress.user.login = ?#{principal.username}"
    )
    List<CourseSessionProgress> findByUserIsCurrentUser();
}

package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.*;
import com.charusat.pacelearn.domain.CourseReviewStatus;
import com.charusat.pacelearn.repository.CourseReviewStatusRepository;
import com.charusat.pacelearn.service.criteria.CourseReviewStatusCriteria;
import com.charusat.pacelearn.service.dto.CourseReviewStatusDTO;
import com.charusat.pacelearn.service.mapper.CourseReviewStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link CourseReviewStatus} entities in the database.
 * The main input is a {@link CourseReviewStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseReviewStatusDTO} or a {@link Page} of {@link CourseReviewStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseReviewStatusQueryService extends QueryService<CourseReviewStatus> {

    private final Logger log = LoggerFactory.getLogger(CourseReviewStatusQueryService.class);

    private final CourseReviewStatusRepository courseReviewStatusRepository;

    private final CourseReviewStatusMapper courseReviewStatusMapper;

    public CourseReviewStatusQueryService(
        CourseReviewStatusRepository courseReviewStatusRepository,
        CourseReviewStatusMapper courseReviewStatusMapper
    ) {
        this.courseReviewStatusRepository = courseReviewStatusRepository;
        this.courseReviewStatusMapper = courseReviewStatusMapper;
    }

    /**
     * Return a {@link List} of {@link CourseReviewStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseReviewStatusDTO> findByCriteria(CourseReviewStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseReviewStatus> specification = createSpecification(criteria);
        return courseReviewStatusMapper.toDto(courseReviewStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseReviewStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseReviewStatusDTO> findByCriteria(CourseReviewStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseReviewStatus> specification = createSpecification(criteria);
        return courseReviewStatusRepository.findAll(specification, page).map(courseReviewStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseReviewStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseReviewStatus> specification = createSpecification(criteria);
        return courseReviewStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseReviewStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseReviewStatus> createSpecification(CourseReviewStatusCriteria criteria) {
        Specification<CourseReviewStatus> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseReviewStatus_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CourseReviewStatus_.status));
            }
            if (criteria.getStatusUpdatedOn() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getStatusUpdatedOn(), CourseReviewStatus_.statusUpdatedOn));
            }
            if (criteria.getFeedback() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeedback(), CourseReviewStatus_.feedback));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(CourseReviewStatus_.user, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getCourseSessionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCourseSessionId(),
                            root -> root.join(CourseReviewStatus_.courseSession, JoinType.LEFT).get(CourseSession_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

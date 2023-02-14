package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.*;
import com.charusat.pacelearn.domain.CourseLevel;
import com.charusat.pacelearn.repository.CourseLevelRepository;
import com.charusat.pacelearn.service.criteria.CourseLevelCriteria;
import com.charusat.pacelearn.service.dto.CourseLevelDTO;
import com.charusat.pacelearn.service.mapper.CourseLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;

/**
 * Service for executing complex queries for {@link CourseLevel} entities in the database.
 * The main input is a {@link CourseLevelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CourseLevelDTO} or a {@link Page} of {@link CourseLevelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourseLevelQueryService extends QueryService<CourseLevel> {

    private final Logger log = LoggerFactory.getLogger(CourseLevelQueryService.class);

    private final CourseLevelRepository courseLevelRepository;

    private final CourseLevelMapper courseLevelMapper;

    public CourseLevelQueryService(CourseLevelRepository courseLevelRepository, CourseLevelMapper courseLevelMapper) {
        this.courseLevelRepository = courseLevelRepository;
        this.courseLevelMapper = courseLevelMapper;
    }

    /**
     * Return a {@link List} of {@link CourseLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CourseLevelDTO> findByCriteria(CourseLevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CourseLevel> specification = createSpecification(criteria);
        return courseLevelMapper.toDto(courseLevelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CourseLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CourseLevelDTO> findByCriteria(CourseLevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CourseLevel> specification = createSpecification(criteria);
        return courseLevelRepository.findAll(specification, page).map(courseLevelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourseLevelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CourseLevel> specification = createSpecification(criteria);
        return courseLevelRepository.count(specification);
    }

    /**
     * Function to convert {@link CourseLevelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CourseLevel> createSpecification(CourseLevelCriteria criteria) {
        Specification<CourseLevel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CourseLevel_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), CourseLevel_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CourseLevel_.description));
            }
        }
        return specification;
    }
}

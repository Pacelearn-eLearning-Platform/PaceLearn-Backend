package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.CourseReviewStatus;
import com.charusat.pacelearn.repository.CourseReviewStatusRepository;
import com.charusat.pacelearn.service.CourseReviewStatusService;
import com.charusat.pacelearn.service.dto.CourseReviewStatusDTO;
import com.charusat.pacelearn.service.mapper.CourseReviewStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseReviewStatus}.
 */
@Service
@Transactional
public class CourseReviewStatusServiceImpl implements CourseReviewStatusService {

    private final Logger log = LoggerFactory.getLogger(CourseReviewStatusServiceImpl.class);

    private final CourseReviewStatusRepository courseReviewStatusRepository;

    private final CourseReviewStatusMapper courseReviewStatusMapper;

    public CourseReviewStatusServiceImpl(
        CourseReviewStatusRepository courseReviewStatusRepository,
        CourseReviewStatusMapper courseReviewStatusMapper
    ) {
        this.courseReviewStatusRepository = courseReviewStatusRepository;
        this.courseReviewStatusMapper = courseReviewStatusMapper;
    }

    @Override
    public CourseReviewStatusDTO save(CourseReviewStatusDTO courseReviewStatusDTO) {
        log.debug("Request to save CourseReviewStatus : {}", courseReviewStatusDTO);
        CourseReviewStatus courseReviewStatus = courseReviewStatusMapper.toEntity(courseReviewStatusDTO);
        courseReviewStatus = courseReviewStatusRepository.save(courseReviewStatus);
        return courseReviewStatusMapper.toDto(courseReviewStatus);
    }

    @Override
    public Optional<CourseReviewStatusDTO> partialUpdate(CourseReviewStatusDTO courseReviewStatusDTO) {
        log.debug("Request to partially update CourseReviewStatus : {}", courseReviewStatusDTO);

        return courseReviewStatusRepository
            .findById(courseReviewStatusDTO.getId())
            .map(existingCourseReviewStatus -> {
                courseReviewStatusMapper.partialUpdate(existingCourseReviewStatus, courseReviewStatusDTO);

                return existingCourseReviewStatus;
            })
            .map(courseReviewStatusRepository::save)
            .map(courseReviewStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseReviewStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseReviewStatuses");
        return courseReviewStatusRepository.findAll(pageable).map(courseReviewStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseReviewStatusDTO> findOne(Long id) {
        log.debug("Request to get CourseReviewStatus : {}", id);
        return courseReviewStatusRepository.findById(id).map(courseReviewStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseReviewStatus : {}", id);
        courseReviewStatusRepository.deleteById(id);
    }
}

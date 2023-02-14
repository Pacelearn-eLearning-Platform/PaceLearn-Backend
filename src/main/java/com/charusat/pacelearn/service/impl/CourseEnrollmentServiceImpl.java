package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.CourseEnrollment;
import com.charusat.pacelearn.repository.CourseEnrollmentRepository;
import com.charusat.pacelearn.service.CourseEnrollmentService;
import com.charusat.pacelearn.service.dto.CourseEnrollmentDTO;
import com.charusat.pacelearn.service.mapper.CourseEnrollmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseEnrollment}.
 */
@Service
@Transactional
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final Logger log = LoggerFactory.getLogger(CourseEnrollmentServiceImpl.class);

    private final CourseEnrollmentRepository courseEnrollmentRepository;

    private final CourseEnrollmentMapper courseEnrollmentMapper;

    public CourseEnrollmentServiceImpl(
        CourseEnrollmentRepository courseEnrollmentRepository,
        CourseEnrollmentMapper courseEnrollmentMapper
    ) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseEnrollmentMapper = courseEnrollmentMapper;
    }

    @Override
    public CourseEnrollmentDTO save(CourseEnrollmentDTO courseEnrollmentDTO) {
        log.debug("Request to save CourseEnrollment : {}", courseEnrollmentDTO);
        CourseEnrollment courseEnrollment = courseEnrollmentMapper.toEntity(courseEnrollmentDTO);
        courseEnrollment = courseEnrollmentRepository.save(courseEnrollment);
        return courseEnrollmentMapper.toDto(courseEnrollment);
    }

    @Override
    public Optional<CourseEnrollmentDTO> partialUpdate(CourseEnrollmentDTO courseEnrollmentDTO) {
        log.debug("Request to partially update CourseEnrollment : {}", courseEnrollmentDTO);

        return courseEnrollmentRepository
            .findById(courseEnrollmentDTO.getId())
            .map(existingCourseEnrollment -> {
                courseEnrollmentMapper.partialUpdate(existingCourseEnrollment, courseEnrollmentDTO);

                return existingCourseEnrollment;
            })
            .map(courseEnrollmentRepository::save)
            .map(courseEnrollmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseEnrollmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseEnrollments");
        return courseEnrollmentRepository.findAll(pageable).map(courseEnrollmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseEnrollmentDTO> findOne(Long id) {
        log.debug("Request to get CourseEnrollment : {}", id);
        return courseEnrollmentRepository.findById(id).map(courseEnrollmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseEnrollment : {}", id);
        courseEnrollmentRepository.deleteById(id);
    }
}

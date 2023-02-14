package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.CourseLevel;
import com.charusat.pacelearn.repository.CourseLevelRepository;
import com.charusat.pacelearn.service.CourseLevelService;
import com.charusat.pacelearn.service.dto.CourseLevelDTO;
import com.charusat.pacelearn.service.mapper.CourseLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseLevel}.
 */
@Service
@Transactional
public class CourseLevelServiceImpl implements CourseLevelService {

    private final Logger log = LoggerFactory.getLogger(CourseLevelServiceImpl.class);

    private final CourseLevelRepository courseLevelRepository;

    private final CourseLevelMapper courseLevelMapper;

    public CourseLevelServiceImpl(CourseLevelRepository courseLevelRepository, CourseLevelMapper courseLevelMapper) {
        this.courseLevelRepository = courseLevelRepository;
        this.courseLevelMapper = courseLevelMapper;
    }

    @Override
    public CourseLevelDTO save(CourseLevelDTO courseLevelDTO) {
        log.debug("Request to save CourseLevel : {}", courseLevelDTO);
        CourseLevel courseLevel = courseLevelMapper.toEntity(courseLevelDTO);
        courseLevel = courseLevelRepository.save(courseLevel);
        return courseLevelMapper.toDto(courseLevel);
    }

    @Override
    public Optional<CourseLevelDTO> partialUpdate(CourseLevelDTO courseLevelDTO) {
        log.debug("Request to partially update CourseLevel : {}", courseLevelDTO);

        return courseLevelRepository
            .findById(courseLevelDTO.getId())
            .map(existingCourseLevel -> {
                courseLevelMapper.partialUpdate(existingCourseLevel, courseLevelDTO);

                return existingCourseLevel;
            })
            .map(courseLevelRepository::save)
            .map(courseLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseLevels");
        return courseLevelRepository.findAll(pageable).map(courseLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseLevelDTO> findOne(Long id) {
        log.debug("Request to get CourseLevel : {}", id);
        return courseLevelRepository.findById(id).map(courseLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseLevel : {}", id);
        courseLevelRepository.deleteById(id);
    }
}

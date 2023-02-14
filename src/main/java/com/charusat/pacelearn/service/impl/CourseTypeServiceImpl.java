package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.CourseType;
import com.charusat.pacelearn.repository.CourseTypeRepository;
import com.charusat.pacelearn.service.CourseTypeService;
import com.charusat.pacelearn.service.dto.CourseTypeDTO;
import com.charusat.pacelearn.service.mapper.CourseTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseType}.
 */
@Service
@Transactional
public class CourseTypeServiceImpl implements CourseTypeService {

    private final Logger log = LoggerFactory.getLogger(CourseTypeServiceImpl.class);

    private final CourseTypeRepository courseTypeRepository;

    private final CourseTypeMapper courseTypeMapper;

    public CourseTypeServiceImpl(CourseTypeRepository courseTypeRepository, CourseTypeMapper courseTypeMapper) {
        this.courseTypeRepository = courseTypeRepository;
        this.courseTypeMapper = courseTypeMapper;
    }

    @Override
    public CourseTypeDTO save(CourseTypeDTO courseTypeDTO) {
        log.debug("Request to save CourseType : {}", courseTypeDTO);
        CourseType courseType = courseTypeMapper.toEntity(courseTypeDTO);
        courseType = courseTypeRepository.save(courseType);
        return courseTypeMapper.toDto(courseType);
    }

    @Override
    public Optional<CourseTypeDTO> partialUpdate(CourseTypeDTO courseTypeDTO) {
        log.debug("Request to partially update CourseType : {}", courseTypeDTO);

        return courseTypeRepository
            .findById(courseTypeDTO.getId())
            .map(existingCourseType -> {
                courseTypeMapper.partialUpdate(existingCourseType, courseTypeDTO);

                return existingCourseType;
            })
            .map(courseTypeRepository::save)
            .map(courseTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseTypes");
        return courseTypeRepository.findAll(pageable).map(courseTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseTypeDTO> findOne(Long id) {
        log.debug("Request to get CourseType : {}", id);
        return courseTypeRepository.findById(id).map(courseTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseType : {}", id);
        courseTypeRepository.deleteById(id);
    }
}

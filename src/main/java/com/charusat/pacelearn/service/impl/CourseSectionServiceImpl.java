package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.CourseSection;
import com.charusat.pacelearn.domain.User;
import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.repository.CourseSectionRepository;
import com.charusat.pacelearn.service.CourseSectionService;
import com.charusat.pacelearn.service.UserService;
import com.charusat.pacelearn.service.dto.CourseDTO;
import com.charusat.pacelearn.service.dto.CourseSectionDTO;
import com.charusat.pacelearn.service.mapper.CourseMapper;
import com.charusat.pacelearn.service.mapper.CourseSectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseSection}.
 */
@Service
@Transactional
public class CourseSectionServiceImpl implements CourseSectionService {

    private final Logger log = LoggerFactory.getLogger(CourseSectionServiceImpl.class);

    private final CourseSectionRepository courseSectionRepository;

    private final CourseServiceImpl courseService;

    private final UserService userService;

    private final CourseSectionMapper courseSectionMapper;

    private final CourseMapper courseMapper;

    private final CourseRepository courseRepository;

    public CourseSectionServiceImpl(
            CourseSectionRepository courseSectionRepository,
            CourseServiceImpl courseService, UserService userService, CourseSectionMapper courseSectionMapper,
            CourseMapper courseMapper,
            CourseRepository courseRepository
    ) {
        this.courseSectionRepository = courseSectionRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.courseSectionMapper = courseSectionMapper;
        this.courseMapper = courseMapper;
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseSectionDTO save(CourseSectionDTO courseSectionDTO) {
        log.debug("Request to save CourseSection : {}", courseSectionDTO);
        CourseSection courseSection = courseSectionMapper.toEntity(courseSectionDTO);
        courseSection = courseSectionRepository.save(courseSection);
        return courseSectionMapper.toDto(courseSection);
    }

    @Override
    public Optional<CourseSectionDTO> partialUpdate(CourseSectionDTO courseSectionDTO) {
        log.debug("Request to partially update CourseSection : {}", courseSectionDTO);

        return courseSectionRepository
            .findById(courseSectionDTO.getId())
            .map(existingCourseSection -> {
                courseSectionMapper.partialUpdate(existingCourseSection, courseSectionDTO);

                return existingCourseSection;
            })
            .map(courseSectionRepository::save)
            .map(courseSectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSections");
        return courseSectionRepository.findAll(pageable).map(courseSectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseSectionDTO> findOne(Long id) {
        log.debug("Request to get CourseSection : {}", id);
        return courseSectionRepository.findById(id).map(courseSectionMapper::toDto);
    }

    @Override
    public List<CourseSection> findSectionsByCourse(Long id) {
        return courseSectionRepository.findCourseSectionByCourse(courseRepository.findById(id));
    }

    @Override
    public List<CourseSection> findCourseSectionsByCourse(Long id) {
        return courseSectionRepository.findCourseSectionsByCourse(courseRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseSection : {}", id);
        courseSectionRepository.deleteById(id);
    }

    public CourseSection save(Long courseId, CourseSectionDTO courseSectionDTO) {
        Optional<CourseDTO> course = courseService.findOne(courseId);
        Course course1 = courseMapper.toEntity(course);
//        System.out.println("Course is --> "+courseMapper.toEntity(course.get()));
        Optional<User> user = userService.getUserWithAuthorities();
//        System.out.println("Logged in user is --> "+user.get().getId());
//        System.out.println("Expected user in user is --> "+course.get().getUser().getId());
        if (course.isPresent() && course.get().getUser().getId().equals(user.get().getId())) {
            CourseSection courseSection = new CourseSection(courseSectionDTO);
//            courseSection.course(course.get());
//            System.out.println("Inside if part of save method");
            courseSection.course(courseMapper.toEntity(course.get()));
            courseSection.isDraft(false);
            courseSection.sectionOrder(courseSectionRepository.findCourseSectionByCourse_Id(courseId).size() + 1);
            courseSection.isApproved(false);
            return courseSectionRepository.save(courseSection);
        } else {
            return null;
        }
    }
}

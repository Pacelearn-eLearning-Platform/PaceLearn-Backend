package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.CourseSession;
import com.charusat.pacelearn.domain.User;
import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.repository.CourseSectionRepository;
import com.charusat.pacelearn.repository.CourseSessionRepository;
import com.charusat.pacelearn.service.CourseSectionService;
import com.charusat.pacelearn.service.CourseService;
import com.charusat.pacelearn.service.CourseSessionService;
import com.charusat.pacelearn.service.UserService;
import com.charusat.pacelearn.service.dto.CourseDTO;
import com.charusat.pacelearn.service.dto.CourseSectionDTO;
import com.charusat.pacelearn.service.dto.CourseSessionDTO;
import com.charusat.pacelearn.service.dto.CourseSessionDTOManual;
import com.charusat.pacelearn.service.mapper.CourseSectionMapper;
import com.charusat.pacelearn.service.mapper.CourseSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseSession}.
 */
@Service
@Transactional
public class CourseSessionServiceImpl implements CourseSessionService {

    private final Logger log = LoggerFactory.getLogger(CourseSessionServiceImpl.class);

    private final CourseSessionRepository courseSessionRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final CourseRepository courseRepository;

    private final UserService userService;
    private final CourseService courseService;
    private final CourseSectionService courseSectionService;

    private final CourseSessionMapper courseSessionMapper;
    private final CourseSectionMapper courseSectionMapper;

    public CourseSessionServiceImpl(
            CourseSessionRepository courseSessionRepository,
            CourseSectionRepository courseSectionRepository,
            CourseRepository courseRepository,
            UserService userService, CourseService courseService, CourseSectionService courseSectionService, CourseSessionMapper courseSessionMapper,
            CourseSectionMapper courseSectionMapper) {
        this.courseSessionRepository = courseSessionRepository;
        this.courseSectionRepository = courseSectionRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseService = courseService;
        this.courseSectionService = courseSectionService;
        this.courseSessionMapper = courseSessionMapper;
        this.courseSectionMapper = courseSectionMapper;
    }

    @Override
    public CourseSessionDTO save(CourseSessionDTO courseSessionDTO) {
        log.debug("Request to save CourseSession : {}", courseSessionDTO);
        CourseSession courseSession = courseSessionMapper.toEntity(courseSessionDTO);
        courseSession = courseSessionRepository.save(courseSession);
        return courseSessionMapper.toDto(courseSession);
    }

    @Override
    public Optional<CourseSessionDTO> partialUpdate(CourseSessionDTO courseSessionDTO) {
        log.debug("Request to partially update CourseSession : {}", courseSessionDTO);

        return courseSessionRepository
            .findById(courseSessionDTO.getId())
            .map(existingCourseSession -> {
                courseSessionMapper.partialUpdate(existingCourseSession, courseSessionDTO);

                return existingCourseSession;
            })
            .map(courseSessionRepository::save)
            .map(courseSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSessions");
        return courseSessionRepository.findAll(pageable).map(courseSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseSessionDTO> findOne(Long id) {
        log.debug("Request to get CourseSession : {}", id);
        return courseSessionRepository.findById(id).map(courseSessionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseSession : {}", id);
        courseSessionRepository.deleteById(id);
    }

    @Override
    public List<CourseSession> findSessionByCourseSection(Long id) {
        return courseSessionRepository.findCourseSessionsByCourseSection(courseSectionRepository.findById(id));
    }

    @Override
    public List<CourseSession> findCourseSessionsByCourseSectionIn(Long id) {
        return courseSessionRepository.findCourseSessionsByCourseSectionIn(
            courseSectionRepository.findCourseSectionsByCourse(courseRepository.findById(id))
        );
    }

    /**
     *  CUSTOM CODE
     *  Author : Kirtan Shah
     */
    public CourseSession save(Long courseId, CourseSessionDTOManual courseSessionDTOManual) throws IOException {
//        System.out.println(courseSessionDTOManual.getSessionVideo());
        Optional<User> user = userService.getUserWithAuthorities();
        //        Checking if user is present
        if (user.isPresent()) {
            Optional<CourseDTO> course = courseService.findOne(courseId);
            //            Checking if course is present and the current user is the owner of that course.
            if (course.isPresent() && course.get().getUser().getId().equals(user.get().getId())) {
                Optional<CourseSectionDTO> courseSection = courseSectionService.findOne(courseSessionDTOManual.getSectionId().longValue());
                //                Checking if courseSection is present and if the courseSection is part of the course.
                if (courseSection.isPresent() && courseSection.get().getCourse().equals(course.get())) {
                    CourseSession courseSession = new CourseSession(courseSessionDTOManual);
//                    System.out.println(courseSessionDTOManual.getSessionVideo());
                    //courseSession.setSessionVideo(compressAndUpload(courseSessionDTO.getSessionVideo()));
                    courseSession.setSessionVideo(courseSessionDTOManual.getSessionVideo());
                    courseSession.setCourseSection(courseSectionMapper.toEntity(courseSection.get()));
                    courseSession.setSessionTitle(courseSessionDTOManual.getSessionTitle());
                    courseSession.setSessionDescription(courseSessionDTOManual.getSessionDescription());
                    courseSession.setSessionResource(courseSessionDTOManual.getSessionResource());

//                    courseSession.setCourseSection(courseSection.get());
                    courseSession.isApproved(false);
                    courseSession.isDraft(false);
                    if (courseSessionDTOManual.getSessionDuration() == null){
                        courseSession.setSessionDuration(0L);
                    }else{
                        courseSession.setSessionDuration(courseSessionDTOManual.getSessionDuration());
                    }
//                    courseSession.setSessionDuration(0L);
                    courseSession.setSessionLocation("");
                    courseSession.isPublished(false);
                    if (courseSessionDTOManual.getIsDraft() == null) {
                        courseSession.setIsDraft(false);
                    }
                    if (courseSessionDTOManual.getIsPreview() == null) {
                        courseSession.setIsPreview(false);
                    }
//                    System.out.println("Section is --> "+courseSessionDTOManual.getSectionId().longValue());
                    courseSession.sessionOrder(courseSessionRepository.findAllByCourseSection_Id(courseSessionDTOManual.getSectionId().longValue()).size() + 1);
                    return courseSessionRepository.save(courseSession);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

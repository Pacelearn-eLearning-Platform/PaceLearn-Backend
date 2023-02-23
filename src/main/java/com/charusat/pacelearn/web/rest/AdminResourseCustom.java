package com.charusat.pacelearn.web.rest;

import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.repository.CourseSessionRepository;
import com.charusat.pacelearn.repository.UserRepository;
import com.charusat.pacelearn.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminResourseCustom {

    private final Logger log = LoggerFactory.getLogger(AdminResourseCustom.class);


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;

    public AdminResourseCustom(UserRepository userRepository, CourseRepository courseRepository, CourseSessionRepository courseSessionRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseSessionRepository = courseSessionRepository;
    }


    /**
     *  CUSTOM
     *  AUTHOR : KIRTAN SHAH
     */

    /**
     *
     * {@code GET  /coreMetaData} : Get the core meta data of the platform for dashboard
     *
     */
    @GetMapping("/coreMetaData")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Map<String,Long>> getCoreMetaData(){
        log.debug("REST request to get the core meta data for dashboard : {}");
        HashMap<String,Long> body = new HashMap<>();

        Long students = Long.valueOf(userRepository.countAllByAuthoritiesContains("ROLE_STUDENT"));
        Long instructor = Long.valueOf(userRepository.countAllByAuthoritiesContains("ROLE_FACULTY"));
        Long reviewer = Long.valueOf(userRepository.countAllByAuthoritiesContains("ROLE_REVIEWER"));
        Long approvedCourses = Long.valueOf(courseRepository.countCoursesByIsApproved(true));
        Long approvalPendingCourses = Long.valueOf(courseRepository.countCoursesByIsApproved(false));
        Long totalEnrollment = Long.valueOf(courseRepository.findEnrolledCourses());
        Long courseVideos = Long.valueOf(courseSessionRepository.countAll());

        body.put("students",students);
        body.put("instructor",instructor);
        body.put("reviewer",reviewer);
        body.put("approvedCourses",approvedCourses);
        body.put("approvalPendingCourses",approvalPendingCourses);
        body.put("totalEnrollment",totalEnrollment);
        body.put("courseVideos",courseVideos);

        return  ResponseEntity.ok(body);


    }

}

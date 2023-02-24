package com.charusat.pacelearn.web.rest;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.repository.CourseSessionRepository;
import com.charusat.pacelearn.repository.UserRepository;
import com.charusat.pacelearn.security.AuthoritiesConstants;
import com.charusat.pacelearn.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
public class AdminResourseCustom {

    private final Logger log = LoggerFactory.getLogger(AdminResourseCustom.class);


    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final CourseService courseService;

    public AdminResourseCustom(UserRepository userRepository, CourseRepository courseRepository, CourseSessionRepository courseSessionRepository, CourseService courseService) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.courseService = courseService;
    }


    /**
     *  CUSTOM  Resource
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

    @PutMapping("/course/{courseId}/approve")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Map<String,String>> approveCourse(@PathVariable Long courseId) throws URISyntaxException {
        log.debug("REST request to approve CourseId : {}", courseId);
        Course result = courseService.approveCourse(courseId);
        HashMap<String,String> body = new HashMap<>();
        body.put("message","Course approved successfully");
        return ResponseEntity.ok().body(body);
//                .created(new URI("/api/courses/" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//                .body(result);
    }

    @PutMapping("/course/{courseId}/revoke-approval")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Map<String,String>> disApproveCourse(@PathVariable Long courseId) throws URISyntaxException {
        log.debug("REST request to approve CourseId : {}", courseId);
        Course result = courseService.disApproveCourse(courseId);
        HashMap<String,String> body = new HashMap<>();
        body.put("message","Course disapproved successfully");
        return ResponseEntity.ok().body(body);
//                .created(new URI("/api/courses/" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//                .body(result);
    }

    @GetMapping("/courses/approval/request")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Map<String, List<Course>>> approvalPendingCourses(){
        log.debug("REST request to get pending approval courses : {}");
        List<Course> result = courseRepository.findAllByIsApproved(false);
        HashMap<String,List<Course>> body = new HashMap();
        body.put("pendingApprovalCourses",result);

        return ResponseEntity.ok().body(body);
    }


    @GetMapping("/courses/approved-courses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Map<String,List<Course>>> approvedCourses(){
        log.debug("REST request to get pending approval courses : {}");
        List<Course> result = courseRepository.findAllByIsApproved(true);
        HashMap<String,List<Course>> body = new HashMap();
        body.put("approvedCourses",result);

        return ResponseEntity.ok().body(body);
    }

}

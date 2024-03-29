package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.Authority;
import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.User;
import com.charusat.pacelearn.repository.AuthorityRepository;
import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.repository.CourseReviewStatusRepository;
import com.charusat.pacelearn.repository.UserRepository;
import com.charusat.pacelearn.security.AuthoritiesConstants;
import com.charusat.pacelearn.service.CourseService;
import com.charusat.pacelearn.service.MailService;
import com.charusat.pacelearn.service.UserService;
import com.charusat.pacelearn.service.dto.CourseDTO;
import com.charusat.pacelearn.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service Implementation for managing {@link Course}.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;
    private final CourseReviewStatusRepository courseReviewStatusRepository;

    private final MailService mailService;

    private final CourseMapper courseMapper;

    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            UserRepository userRepository,
            CourseReviewStatusRepository courseReviewStatusRepository, MailService mailService, CourseMapper courseMapper,
            UserService userService, AuthorityRepository authorityRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseReviewStatusRepository = courseReviewStatusRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.courseMapper = courseMapper;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        /**
         * Setting the default values that needs to set during the course creation.
         * */
        if (course.getCourseCreatedOn() == null) {
            course.setCourseCreatedOn(LocalDate.now());
        }
//        System.out.println("Insisde COurse creation implementation");
        course.setIsApproved(false);
        course.isPublished(false);
        course.setIsDraft(true);
        course.setCourseUpdatedOn(LocalDate.now());
        course.setUser(userService.getUserWithAuthorities().get());
        /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public Optional<CourseDTO> partialUpdate(CourseDTO courseDTO) {
        log.debug("Request to partially update Course : {}", courseDTO);

        return courseRepository
            .findById(courseDTO.getId())
            .map(existingCourse -> {
                existingCourse.setCourseUpdatedOn(LocalDate.now());
                courseMapper.partialUpdate(existingCourse, courseDTO);

                return existingCourse;
            })
            .map(courseRepository::save)
            .map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllOpen() {
        log.debug("Request to get all Courses");
//        return courseRepository.findAll().map(courseMapper::toDto);
        return courseRepository.findAllByIsApproved(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDTO> findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }

    //
    //    /**
    //     * This method will fetch the courses for the given user according to the user's email.
    //     * @return List<CourseDTO>
    //     * */
    //    public List<CourseDTO> findAllByCurrentSemester() {
    //        log.debug("Request to get all Courses");
    //        Optional<User> user = userService.getUserWithAuthorities();
    //        if (user.isPresent()) {
    //            int currentYear = Math.subtractExact(
    //                GregorianCalendar.getInstance().get(GregorianCalendar.YEAR) % 2000,
    //                Integer.parseInt(user.get().getEmail().substring(0, 2))
    //            );
    //            int a = 1, b = 2;
    //            int month = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
    //            if (currentYear == 0) {
    //                a = 1;
    //                b = 1;
    //            } else if (currentYear == 1) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 1;
    //                    b = 2;
    //                } else {
    //                    a = 2;
    //                    b = 3;
    //                }
    //            } else if (currentYear == 2) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 3;
    //                    b = 4;
    //                } else {
    //                    a = 4;
    //                    b = 5;
    //                }
    //            } else if (currentYear == 3) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 5;
    //                    b = 6;
    //                } else {
    //                    a = 6;
    //                    b = 7;
    //                }
    //            } else if (currentYear == 4) {
    //                if (month <= GregorianCalendar.JUNE) {
    //                    a = 7;
    //                    b = 8;
    //                }
    //            }
    //            List<CourseDTO> courseDTOList = new ArrayList<>();
    //            courseRepository
    //                .findAllBySemester(a, b)
    //                .stream()
    //                .forEach(course -> {
    //                    courseDTOList.add(courseMapper.toDto(course));
    //                });
    //            return courseDTOList;
    //        }
    //        return null;
    //    }

    @Override
    public List<CourseDTO> getEnrolledCourses() throws Exception {
        Optional<User> user = userService.getUserWithAuthorities();
        List<Course> courses;
        List<CourseDTO> courseDTOList = new ArrayList<>();
        CourseDTO courseDTO;
        if (user.isPresent()) {
            courses = courseRepository.findCourseByEnrolledUsersListsContaining(user.get());
            for (Course course : courses) {
                courseDTO = new CourseDTO(courseMapper.toDto(course));
                courseDTO.setEnrolled(true);
                courseDTOList.add(courseDTO);
            }
            return courseDTOList;
        } else {
            throw new Exception("User not found");
        }
    }

    /**
     * CUSTOM
     * */
    @Override
    public List<CourseDTO> getByCategoryId(Long id) throws Exception {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            List<CourseDTO> list = new ArrayList<>();
            List<Course> courses = courseRepository.findByCategoryId(id);
            CourseDTO courseDTO;
            for (Course course : courses) {
                courseDTO = new CourseDTO(courseMapper.toDto(course));
                if (course.getEnrolledUsersLists().contains(user.get())) {
//                    System.out.println("HAHAHAHAHAHAHA  --> " + course.getEnrolledUsersLists());
                    courseDTO.setEnrolled(true);
                } else {
//                    System.out.println("HAHAHAHAHAHAHA2  --> " + course.getEnrolledUsersLists());
                    courseDTO.setEnrolled(false);
                }
//                courseDTO.setMinStudents(course.getMinStudents() + getStudentEnrolledCountByCourse(course.getId()).getBody());
                courseDTO.setMinStudents(course.getMinStudents() + course.getEnrolledUsersLists().size());
                list.add(courseDTO);
            }
            return list;
        } else {
            throw new Exception("User ot found");
        }
    }

    @Override
    public ResponseEntity<Map<String,Integer>> getStudentEnrolledCountByCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Integer count = course.get().getEnrolledUsersLists().size();
            HashMap<String,Integer> body = new HashMap<>();
            body.put("studentCount",count);
            return ResponseEntity.ok(body);
        } else {
            log.error("Course not found");
            return ResponseEntity.noContent().build();
        }
    }

    //    @Override
    //    public ResponseEntity enrollInCourse(Long courseId) {
    //        AtomicBoolean flag = new AtomicBoolean(false);
    //        try {
    //            System.out.println("Inside Try Block");
    //            System.out.println("courseId is --> " + courseId);
    //            //            System.out.println("courseId is --> "+Long.valueOf(courseId));
    //
    //            Optional<Course> demoCourse = courseRepository.findById(courseId);
    //            Set<User> alreadyEnrolledUsers = demoCourse.get().getEnrolledUsersLists();
    //            if (
    //                userService.getUserWithAuthorities().isPresent() &&
    //                !alreadyEnrolledUsers.contains(userService.getUserWithAuthorities().get())
    //            ) {
    //                System.out.println("Inside If Part");
    //                alreadyEnrolledUsers.add(userService.getUserWithAuthorities().get());
    //                System.out.println(alreadyEnrolledUsers);
    //                demoCourse.get().setEnrolledUsersLists(alreadyEnrolledUsers);
    //                flag.set(true);
    //                courseRepository.save(demoCourse.orElse(null));
    //            }
    //            if (flag.get()) return ResponseEntity.accepted().build(); else {
    //                return ResponseEntity.badRequest().body("Already enrolled");
    //            }
    //            //            courseRepository
    //            //                .findById(courseId)
    //            //                .map(
    //            //                    existingCourse -> {
    //            //                        System.out.println("Inside Try Block 2");
    //            //                        Set<User> alreadyEnrolledUsers = existingCourse.getEnrolledUsersLists();
    //            //                        if (
    //            //                            userService.getUserWithAuthorities().isPresent() &&
    //            //                                !alreadyEnrolledUsers.contains(userService.getUserWithAuthorities().get())
    //            //                        ) {
    //            //                            System.out.println("Inside If Part");
    //            //                            alreadyEnrolledUsers.add(userService.getUserWithAuthorities().get());
    //            //                            System.out.println(alreadyEnrolledUsers);
    //            //                            existingCourse.setEnrolledUsersLists(alreadyEnrolledUsers);
    //            //                            flag.set(true);
    //            //                        }
    //            //                        return existingCourse;
    //            //                    }
    //            //                )
    //            //                .map(courseRepository::save);
    //            //            if (flag.get()) return ResponseEntity.accepted().build(); else {
    //            //                return ResponseEntity.badRequest().body("Already enrolled");
    //            //            }
    //        } catch (Exception e) {
    //            return ResponseEntity.status(500).build();
    //        }
    //    }

    @Override
    public ResponseEntity enrollInCourse(String courseId) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Map<String, String> body = new HashMap<>();
//        System.out.println("In enrollInCourse function");
        body.put("Success", "Data Entered Successfully");
        try {
            courseRepository
                .findById(Long.valueOf(courseId))
                .map(existingCourse -> {
                    Set<User> alreadyEnrolledUsers = existingCourse.getEnrolledUsersLists();
                    if (
                        userService.getUserWithAuthorities().isPresent() &&
                        !alreadyEnrolledUsers.contains(userService.getUserWithAuthorities().get())
                    ) {
//                        System.out.println("I am in if part");
                        alreadyEnrolledUsers.add(userService.getUserWithAuthorities().get());
                        flag.set(true);
                    }
                    return existingCourse;
                })
                .map(courseRepository::save);
            if (flag.get()) return ResponseEntity.ok().body(body); else {
//                System.out.println("Inside else part of enrolledCourses function");
                HashMap<String,String> errorbody = new HashMap<>();
                body.put("status","Already enrolled");
                return ResponseEntity.badRequest().body(errorbody);
            }
        } catch (Exception e) {
//            System.out.println("Error in enrolledCourses is --> "+ e);
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> getOverview() {
        Map<String, String> map = new HashMap<>();

        Integer data = courseRepository.findAll().size();
        map.put("totalCourses", data.toString());

        data = courseRepository.findTotalEnrollment();
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            data += course.getMinStudents();
        }
        map.put("totalEnrollments", data.toString());

        data = userService.getTotalUsersByAuthority(AuthoritiesConstants.FACULTY);
        map.put("totalInstructors", data.toString());

        return ResponseEntity.ok().body(map);
    }

    @Override
    public ResponseEntity<Set<User>> getEnrolledUsersByCourseId(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Set<User> users = course.get().getEnrolledUsersLists();
            return ResponseEntity.ok().body(users);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            String authority = user.get().getAuthorities().toString();
//            System.out.println("HAHAHAH");
            if (authority.contains(AuthoritiesConstants.ADMIN)) {
                return courseRepository.findAll();
            } else if (authority.contains(AuthoritiesConstants.FACULTY)) {
                return courseRepository.findCourseByUserEquals(user.get());
            } else if (authority.contains(AuthoritiesConstants.STUDENT)) {
                //                return courseRepository.findCourseByEnrolledUsersListsContaining(user.get(), pageable);
                return courseRepository.findAllByIsApproved(true);
            } else {
                return null;
            }
        } else {
            return courseRepository.findAllByIsApproved(true);
        }
    }


    @Override
    public Course approveCourse(Long courseId) {
        log.debug("Request to approve CourseId : {}", courseId);

        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            String authority = user.get().getAuthorities().toString();
            if (authority.contains("ROLE_ADMIN") || authority.contains("ROLE_REVIEWER")) {
                Optional<Course> course = courseRepository.findById(courseId);
                if (course.isPresent()) {
                    course.get().setIsApproved(true);
//                    CourseReviewStatus crs = course.get().getCourseReviewStatus();
//                    crs.setStatus(true);
//                    crs.setStatusUpdatedOn(LocalDate.now());
//                    courseReviewStatusRepository.save(crs);
//                    course.get().setCourseReviewStatus(crs);
                    course.get().setCourseApprovalDate(LocalDate.now());
//                    System.out.println("Course Object is ---> "+course.get());
                    mailService.sendCourseApprovalMail(course.get());
                    return courseRepository.save(course.get());
                } else {
                    log.warn("Course not present");
                    return null;
                }
            } else {
                log.warn("You are not authorized");
                return null;
            }
        } else {
            log.warn("User not present");
            return null;
        }
    }


    @Override
    public Course disApproveCourse(Long courseId) {
        log.debug("Request to revoke-approval CourseId : {}", courseId);

        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            String authority = user.get().getAuthorities().toString();
            if (authority.contains("ROLE_ADMIN") || authority.contains("ROLE_REVIEWER")) {
                Optional<Course> course = courseRepository.findById(courseId);
                if (course.isPresent()) {
                    course.get().setIsApproved(false);
//                    CourseReviewStatus crs = course.get().getCourseReviewStatus();
//                    crs.setStatus(true);
//                    crs.setStatusUpdatedOn(LocalDate.now());
//                    courseReviewStatusRepository.save(crs);
//                    course.get().setCourseReviewStatus(crs);
//                    course.get().setCourseApprovalDate(LocalDate.now());
                    course.get().setCourseUpdatedOn(LocalDate.now());
//                    System.out.println("Course Object is ---> "+course.get());
                    mailService.sendCourseDisApprovalMail(course.get());
                    return courseRepository.save(course.get());
                } else {
                    log.warn("Course not present");
                    return null;
                }
            } else {
                log.warn("You are not authorized");
                return null;
            }
        } else {
            log.warn("User not present");
            return null;
        }
    }

    @Override
    public User assignReviewer(Long userId) {
        log.debug("Request to assign reviewer  : {}", userId);

        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            String authority = user.get().getAuthorities().toString();
            if (authority.contains("ROLE_ADMIN") || authority.contains("ROLE_REVIEWER")) {
                Optional<User> reviewer = userRepository.findById(userId);
                if (reviewer.isPresent()) {
                    Set<Authority> authorities = new HashSet<>();
                    authorityRepository.findById(AuthoritiesConstants.REVIEWER).ifPresent(authorities::add);
                    authorityRepository.findById(AuthoritiesConstants.FACULTY).ifPresent(authorities::add);
//                    System.out.println("Authorities are ---> "+authorities);
                    reviewer.get().setAuthorities(authorities);
//                    course.get().setIsApproved(true);
//                    CourseReviewStatus crs = course.get().getCourseReviewStatus();
//                    crs.setStatus(true);
//                    crs.setStatusUpdatedOn(LocalDate.now());
//                    courseReviewStatusRepository.save(crs);
//                    course.get().setCourseReviewStatus(crs);
//                    course.get().setCourseApprovalDate(LocalDate.now());
//                    System.out.println("Course Object is ---> "+course.get());
//                    mailService.sendCourseApprovalMail(course.get());
                    return userRepository.save(reviewer.get());
                } else {
                    log.warn("Reviewer not present");
                    return null;
                }
            } else {
                log.warn("You are not authorized");
                return null;
            }
        } else {
            log.warn("User not present");
            return null;
        }
    }

    @Override
    public User removeReviewer(Long userId) {
        log.debug("Request to remove reviewer  : {}", userId);

        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            String authority = user.get().getAuthorities().toString();
            if (authority.contains("ROLE_ADMIN") || authority.contains("ROLE_REVIEWER")) {
                Optional<User> reviewer = userRepository.findById(userId);
                if (reviewer.isPresent()) {
                    Set<Authority> authorities = new HashSet<>();
//                    authorityRepository.findById(AuthoritiesConstants.REVIEWER).ifPresent(authorities::add);
                    authorityRepository.findById(AuthoritiesConstants.FACULTY).ifPresent(authorities::add);
//                    System.out.println("Authorities are ---> "+authorities);
                    reviewer.get().setAuthorities(authorities);
//                    course.get().setIsApproved(true);
//                    CourseReviewStatus crs = course.get().getCourseReviewStatus();
//                    crs.setStatus(true);
//                    crs.setStatusUpdatedOn(LocalDate.now());
//                    courseReviewStatusRepository.save(crs);
//                    course.get().setCourseReviewStatus(crs);
//                    course.get().setCourseApprovalDate(LocalDate.now());
//                    System.out.println("Course Object is ---> "+course.get());
//                    mailService.sendCourseApprovalMail(course.get());
                    return userRepository.save(reviewer.get());
                } else {
                    log.warn("Reviewer not present");
                    return null;
                }
            } else {
                log.warn("You are not authorized");
                return null;
            }
        } else {
            log.warn("User not present");
            return null;
        }
    }

}

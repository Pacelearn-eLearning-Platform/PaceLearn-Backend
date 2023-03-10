package com.charusat.pacelearn.web.rest;

import com.charusat.pacelearn.domain.AllCategories;
import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.CourseCategory;
import com.charusat.pacelearn.repository.CourseCategoryRepository;
import com.charusat.pacelearn.service.CourseCategoryQueryService;
import com.charusat.pacelearn.service.CourseCategoryService;
import com.charusat.pacelearn.service.criteria.CourseCategoryCriteria;
import com.charusat.pacelearn.service.dto.CourseCategoryDTO;
import com.charusat.pacelearn.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing {@link com.charusat.pacelearn.domain.CourseCategory}.
 */
@RestController
@RequestMapping("/api")
public class CourseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CourseCategoryResource.class);

    private static final String ENTITY_NAME = "courseCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseCategoryService courseCategoryService;

    private final CourseCategoryRepository courseCategoryRepository;

    private final CourseCategoryQueryService courseCategoryQueryService;

    public CourseCategoryResource(
        CourseCategoryService courseCategoryService,
        CourseCategoryRepository courseCategoryRepository,
        CourseCategoryQueryService courseCategoryQueryService
    ) {
        this.courseCategoryService = courseCategoryService;
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryQueryService = courseCategoryQueryService;
    }

    /**
     * {@code POST  /course-categories} : Create a new courseCategory.
     *
     * @param courseCategoryDTO the courseCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseCategoryDTO, or with status {@code 400 (Bad Request)} if the courseCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-categories")
    public ResponseEntity<CourseCategoryDTO> createCourseCategory(@Valid @RequestBody CourseCategoryDTO courseCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save CourseCategory : {}", courseCategoryDTO);
        if (courseCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseCategoryDTO result = courseCategoryService.save(courseCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/course-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-categories/:id} : Updates an existing courseCategory.
     *
     * @param id the id of the courseCategoryDTO to save.
     * @param courseCategoryDTO the courseCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the courseCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-categories/{id}")
    public ResponseEntity<CourseCategoryDTO> updateCourseCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseCategoryDTO courseCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CourseCategory : {}, {}", id, courseCategoryDTO);
        if (courseCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseCategoryDTO result = courseCategoryService.save(courseCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /course-categories/:id} : Partial updates given fields of an existing courseCategory, field will ignore if it is null
     *
     * @param id the id of the courseCategoryDTO to save.
     * @param courseCategoryDTO the courseCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the courseCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/course-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseCategoryDTO> partialUpdateCourseCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseCategoryDTO courseCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CourseCategory partially : {}, {}", id, courseCategoryDTO);
        if (courseCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseCategoryDTO> result = courseCategoryService.partialUpdate(courseCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-categories} : get all the courseCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseCategories in body.
     */
    @GetMapping("/course-categories")
    public ResponseEntity<List<CourseCategoryDTO>> getAllCourseCategories(
        CourseCategoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseCategories by criteria: {}", criteria);
        Page<CourseCategoryDTO> page = courseCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-categories} : get all the courseCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseCategories in body.
     */
    @GetMapping("/open-course-categories")
    public ResponseEntity<Map<String,List<CourseCategoryDTO>>> getAllOpenCourseCategories(
            CourseCategoryCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CourseCategories by criteria: {}", criteria);
        Page<CourseCategoryDTO> page = courseCategoryQueryService.findByCriteria(criteria, pageable);
        HashMap<String,List<CourseCategoryDTO>> map = new HashMap<>();
        map.put("categories",page.getContent());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(map);
    }

    /**
     * {@code GET  /course-categories/count} : count all the courseCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-categories/count")
    public ResponseEntity<Long> countCourseCategories(CourseCategoryCriteria criteria) {
        log.debug("REST request to count CourseCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-categories/:id} : get the "id" courseCategory.
     *
     * @param id the id of the courseCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-categories/{id}")
    public ResponseEntity<CourseCategoryDTO> getCourseCategory(@PathVariable Long id) {
        log.debug("REST request to get CourseCategory : {}", id);
        Optional<CourseCategoryDTO> courseCategoryDTO = courseCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseCategoryDTO);
    }

    /**
     * {@code DELETE  /course-categories/:id} : delete the "id" courseCategory.
     *
     * @param id the id of the courseCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-categories/{id}")
    public ResponseEntity<Void> deleteCourseCategory(@PathVariable Long id) {
        log.debug("REST request to delete CourseCategory : {}", id);
        courseCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // To Get Parent/Main Categories of courses

    @GetMapping("/course-category/parent-categories")
    public ResponseEntity<Map<String,List<CourseCategory>>> getParentCourseCategories() {
        log.debug("REST request to get course category by isParent");
        List<CourseCategory> list = courseCategoryService.listParentCategory();
        HashMap<String,List<CourseCategory>> body = new HashMap<>();
        body.put("parentCategories",list);
        return ResponseEntity.ok().body(body);
    }

    // To get Sub Categories by Parent Category

    @GetMapping("/course-category/sub-categories/{id}")
    public ResponseEntity<Map<String,List<CourseCategory>>> getSubCourseCategories(@PathVariable Long id) {
        log.debug("REST request to get course category by parentId");
//        return ResponseEntity.ok().body(courseCategoryService.listByParentId(id));
        HashMap<String,List<CourseCategory>> body = new HashMap<>();
        body.put("subcategory",courseCategoryService.listByParentId(id));
        return ResponseEntity.ok().body(body);
    }

    // to get count of courses of parent category
    @GetMapping("/course-category/{parentId}/sub-category/get-course-count")
    public ResponseEntity<Map<Long, Integer>> getCourseCountBySubCategory(@PathVariable Long parentId) {
        return courseCategoryService.getCourseCountBySubCategory(parentId);
    }

    @GetMapping("/course-category/get-course-count")
    public ResponseEntity<Map<Long, Integer>> getCourseCountByParentCategory() {
        return courseCategoryService.getCourseCountByParentCategory();
    }

    @GetMapping("/course-category/sub-categories/courses")
    public ResponseEntity<Map<String, List<Course>>> getCoursesBySubCategories() {
        log.debug("REST request to get courses by sub categories");
        return ResponseEntity.ok().body(courseCategoryService.getCoursesBySubCategories());
    }

    @GetMapping("/course-category/parent-categories/sub-categories")
    public ResponseEntity<Map<String,ArrayList<AllCategories>>> getCourseSubCategoriesByParentCategories() {
        log.info("GET request to get map of course parent categories and course sub categories");
        Map<String,ArrayList<AllCategories>> body = new HashMap<>();
        body.put("categories",courseCategoryService.getCategoriesWithItsSubcategories());
        return ResponseEntity.ok().body(body);
    }
}

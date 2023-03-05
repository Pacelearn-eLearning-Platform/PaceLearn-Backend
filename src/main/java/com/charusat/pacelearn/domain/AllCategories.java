package com.charusat.pacelearn.domain;

import java.util.List;

public class AllCategories {
         Long id ;
         String courseCategoryTitle ;
        String  logo ;

        String description;
        Boolean isParent;
        Integer parentId;
        List<CourseCategory> subCategories;

    public AllCategories(Long id, String courseCategoryTitle, String logo, String description, Boolean isParent, Integer parentId, List<CourseCategory> subCategories) {
        this.id = id;
        this.courseCategoryTitle = courseCategoryTitle;
        this.logo = logo;
        this.description = description;
        this.isParent = isParent;
        this.parentId = parentId;
        this.subCategories = subCategories;
    }

    public AllCategories() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCategoryTitle() {
        return courseCategoryTitle;
    }

    public void setCourseCategoryTitle(String courseCategoryTitle) {
        this.courseCategoryTitle = courseCategoryTitle;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<CourseCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CourseCategory> subCategories) {
        this.subCategories = subCategories;
    }
}

package com.charusat.pacelearn.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.charusat.pacelearn.domain.CourseLevel} entity.
 */
public class CourseLevelDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(min = 10, max = 50)
    private String title;

    @Size(min = 10, max = 400)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseLevelDTO)) {
            return false;
        }

        CourseLevelDTO courseLevelDTO = (CourseLevelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseLevelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseLevelDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

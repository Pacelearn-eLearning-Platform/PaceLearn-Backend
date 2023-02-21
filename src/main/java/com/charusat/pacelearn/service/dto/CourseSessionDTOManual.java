package com.charusat.pacelearn.service.dto;

import java.io.Serializable;

public class CourseSessionDTOManual implements Serializable {

    String sessionTitle;
    String sessionDescription;
    String sessionVideo;
    String sessionResource;
    Long sectionId;
    Long sessionDuration;
    Boolean isPreview;
    Boolean isDraft;
    String quizLink;

    //CourseSection courseSection;


    public Long getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionVideo() {
        return sessionVideo;
    }

    public void setSessionVideo(String sessionVideo) {
        this.sessionVideo = sessionVideo;
    }

    public String getSessionResource() {
        return sessionResource;
    }

    public void setSessionResource(String sessionResource) {
        this.sessionResource = sessionResource;
    }

    public Boolean getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Boolean preview) {
        isPreview = preview;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean draft) {
        isDraft = draft;
    }

    public String getQuizLink() {
        return quizLink;
    }

    public void setQuizLink(String quizLink) {
        this.quizLink = quizLink;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}

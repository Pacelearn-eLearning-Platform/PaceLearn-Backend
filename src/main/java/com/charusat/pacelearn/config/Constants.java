package com.charusat.pacelearn.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String YOUTUBE_API_KEY = "AIzaSyAT_xty2Pny6w75z5IDDo0J25fANx1ZjKw";

    public static final String DRAFTING;
    public static final String APPROVAL_PENDING;
    public static final String REVIEWER_ASSIGNED;
    public static final String APPROVED;
    public static final String REJECTED;

    static {
        DRAFTING = "drafting";
        APPROVAL_PENDING = " approval pending";
        REVIEWER_ASSIGNED = " reviewer assigned";
        APPROVED = " course approved";
        REJECTED = " course rejected";
    }

    private Constants() {}
}

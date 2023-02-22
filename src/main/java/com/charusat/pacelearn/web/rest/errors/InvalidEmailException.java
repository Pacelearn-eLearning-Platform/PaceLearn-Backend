package com.charusat.pacelearn.web.rest.errors;

public class InvalidEmailException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InvalidEmailException() {
        super(ErrorConstants.INVALID_EMAIL_USED, "Please register by using your CHARUSAT email id only", "userManagement", "invalid email");
    }
}

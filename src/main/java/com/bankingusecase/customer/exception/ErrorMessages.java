package com.bankingusecase.customer.exception;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    NO_RECORD_AVAILABLE("No record available");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

package com.bankingusecase.bank.exception;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    TRANSACTION_AMOUNT("Please enter amount"),
    NO_RECORD_AVAILABLE("No record available"),
    TRANSACTION_FAILED("PLease check the account number and balance");

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

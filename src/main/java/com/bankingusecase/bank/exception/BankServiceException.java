package com.bankingusecase.bank.exception;

public class BankServiceException extends RuntimeException{

    public BankServiceException(String message)
    {
        super(message);
    }
}

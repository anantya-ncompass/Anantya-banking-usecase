package com.bankingusecase.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private Date timestamp;
    private String message;


    public ErrorMessage(Date timestamp, String message)
    {
        this.timestamp = timestamp;
        this.message = message;
    }

}

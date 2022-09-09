package com.bankingusecase.account.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountDto {
    private String accountNumber;
    private String customerId;
    private String bankCode;
    private String accountType;
    private double balance;
}

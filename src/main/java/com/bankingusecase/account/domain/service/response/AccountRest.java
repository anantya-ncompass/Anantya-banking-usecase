package com.bankingusecase.account.domain.service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRest {
    private String accountNumber;
    private String customerId;
    private String bankCode;
    private String accountType;
    private double balance;
}

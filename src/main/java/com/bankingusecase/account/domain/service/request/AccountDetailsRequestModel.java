package com.bankingusecase.account.domain.service.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountDetailsRequestModel {

    @NotNull(message = "customerId cannot be null")
    private String customerId;

    @NotNull(message = "bankCode cannot be null")
    private String bankCode;

    @NotNull(message = "accountType cannot be null")
    private String accountType;
}



package com.bankingusecase.bank.domain.service.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BankDetailsRequestModel {

    @NotNull(message = "bankName cannot be null")
    private String bankName;

    @NotNull(message = "address cannot be null")
    private String address;
}

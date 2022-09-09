package com.bankingusecase.account.domain.service.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AccountTransactionRequestModel {

    @NotNull(message= "please enter amount")
    private double amount;
}

package com.bankingusecase.account.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AccountTransactionDto {

    @NotNull(message = "please enter amount")
    private double amount;
}
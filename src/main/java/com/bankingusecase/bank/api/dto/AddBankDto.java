package com.bankingusecase.bank.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBankDto {
    private String code;
    private String bankName;
    private String address;
}

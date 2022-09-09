package com.bankingusecase.account.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailsDto {
    private String customerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String bankCode;
    private String bankName;
    private String bankAddress;
    private String accountNumber;
    private String accountType;
    private double balance;
}

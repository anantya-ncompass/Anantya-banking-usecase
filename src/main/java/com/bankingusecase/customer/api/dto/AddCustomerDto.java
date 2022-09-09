package com.bankingusecase.customer.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCustomerDto {

    private String id;
    private String name;
    private String address;
    private String phoneNumber;

}
package com.bankingusecase.customer.domain.service.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerDetailsRequestModel {

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "address cannot be null")
    private String address;

    @NotNull(message = "phoneNumber cannot be null")
    private String phoneNumber;
}

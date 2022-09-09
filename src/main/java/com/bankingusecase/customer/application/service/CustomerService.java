package com.bankingusecase.customer.application.service;


import com.bankingusecase.customer.api.dto.AddCustomerDto;
import com.bankingusecase.customer.api.dto.UpdateCustomerDto;
import com.bankingusecase.customer.domain.model.Customer;

import java.util.List;


public interface CustomerService {
    AddCustomerDto addCustomer(AddCustomerDto customer);
    List<Customer> getAllCustomers();
    AddCustomerDto getCustomer(String id) throws Exception;
    UpdateCustomerDto updateCustomer(String id, UpdateCustomerDto customer) throws Exception;
    void deleteCustomer(String id) throws Exception;
}


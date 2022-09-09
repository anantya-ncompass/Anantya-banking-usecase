package com.bankingusecase.customer.infrastructure.repository;

import com.bankingusecase.customer.domain.model.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer addCustomer(Customer customer) throws Exception;

    List<Customer> getAllCustomers();

    Customer getCustomer(String id) throws Exception;

    Customer updateCustomer(Customer customer);

    void deleteCustomer(String id) throws Exception;

}

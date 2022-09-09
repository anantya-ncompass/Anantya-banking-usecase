package com.bankingusecase.customer.application.service;


import com.bankingusecase.customer.api.dto.AddCustomerDto;
import com.bankingusecase.customer.api.dto.UpdateCustomerDto;
import com.bankingusecase.customer.domain.model.Customer;
import com.bankingusecase.customer.exception.ErrorMessages;
import com.bankingusecase.customer.infrastructure.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    com.bankingusecase.customer.application.service.CustomerUtils customerUtils;

    @Override
    public AddCustomerDto addCustomer(AddCustomerDto customer) {

        try {
            Customer customerEntity = new Customer();
            BeanUtils.copyProperties(customer, customerEntity);

            String publicCustomerId = customerUtils.generateCustomerId(6);
            customerEntity.setId("ID" + publicCustomerId);

            Customer storedUserDetails = customerRepository.addCustomer(customerEntity);

            AddCustomerDto returnedValue = new AddCustomerDto();
            BeanUtils.copyProperties(storedUserDetails, returnedValue);

            return returnedValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public AddCustomerDto getCustomer(String id) {

        try {
            AddCustomerDto returnValue = new AddCustomerDto();
            Customer customerEntity = customerRepository.getCustomer(id);


            BeanUtils.copyProperties(customerEntity, returnValue);

            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public UpdateCustomerDto updateCustomer(String id, UpdateCustomerDto user) {

        try {
            UpdateCustomerDto returnValue = new UpdateCustomerDto();
            Customer customerEntity = customerRepository.getCustomer(id);

            customerEntity.setName(user.getName());
            customerEntity.setPhoneNumber(user.getPhoneNumber());
            customerEntity.setAddress(user.getAddress());

            Customer updatedUserDetails = customerRepository.updateCustomer(customerEntity);
            BeanUtils.copyProperties(updatedUserDetails, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Customer> getAllCustomers() {
        try {
            return this.customerRepository.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCustomer(String id) throws Exception {

        try {
            customerRepository.deleteCustomer(id);
        }
        catch (Exception e) {
            throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }

    }
}

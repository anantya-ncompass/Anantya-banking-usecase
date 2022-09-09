package com.bankingusecase.customer.api;

import com.bankingusecase.customer.api.dto.AddCustomerDto;
import com.bankingusecase.customer.api.dto.UpdateCustomerDto;
import com.bankingusecase.customer.application.service.CustomerServiceImpl;
import com.bankingusecase.customer.domain.model.Customer;
import com.bankingusecase.customer.domain.service.request.CustomerDetailsRequestModel;
import com.bankingusecase.customer.domain.service.response.CustomerRest;
import com.bankingusecase.customer.domain.service.response.OperationStatusModel;
import com.bankingusecase.customer.domain.service.response.RequestOperationName;
import com.bankingusecase.customer.domain.service.response.RequestOperationStatus;
import com.bankingusecase.customer.exception.CustomerServiceException;
import com.bankingusecase.customer.exception.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @GetMapping(path = "/{id}")
    public CustomerRest getCustomer(@PathVariable String id) throws Exception {


        CustomerRest returnValue = new CustomerRest();

        AddCustomerDto customerDto = customerService.getCustomer(id);
        if (customerDto == null) throw new CustomerServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        BeanUtils.copyProperties(customerDto, returnValue);
        return returnValue;


    }

    @GetMapping
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers == null)
            throw new CustomerServiceException(ErrorMessages.NO_RECORD_AVAILABLE.getErrorMessage());
        return customers;
    }

    @PostMapping
    public CustomerRest addCustomer(@Valid @RequestBody CustomerDetailsRequestModel customerDetails) throws Exception {


        CustomerRest returnValue = new CustomerRest();

        if (customerDetails.getName().isEmpty())
            throw new CustomerServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (customerDetails.getAddress().isEmpty())
            throw new CustomerServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (customerDetails.getPhoneNumber().isEmpty())
            throw new CustomerServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        AddCustomerDto customerDto = new AddCustomerDto();
        BeanUtils.copyProperties(customerDetails, customerDto);

        AddCustomerDto createdUser = customerService.addCustomer(customerDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;

    }

    @PutMapping(path = "/{id}")
    public CustomerRest updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerDetailsRequestModel customerDetails) throws Exception {

        CustomerRest returnValue = new CustomerRest();

        UpdateCustomerDto customerDto = new UpdateCustomerDto();

        BeanUtils.copyProperties(customerDetails, customerDto);
        UpdateCustomerDto updatedUser = customerService.updateCustomer(id, customerDto);
        if (updatedUser == null) throw new CustomerServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;

    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteCustomer(@PathVariable String id) throws Exception {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        customerService.deleteCustomer(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;

    }

}

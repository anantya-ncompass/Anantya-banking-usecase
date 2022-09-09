package com.bankingusecase.bank.api;

import com.bankingusecase.account.api.dto.AccountTransactionDto;
import com.bankingusecase.account.api.dto.AddAccountDto;
import com.bankingusecase.account.api.dto.CustomerDetailsDto;
import com.bankingusecase.account.api.dto.UpdateAccountDto;
import com.bankingusecase.account.application.service.AccountServiceImpl;
import com.bankingusecase.account.domain.service.request.AccountDetailsRequestModel;
import com.bankingusecase.account.domain.service.request.AccountTransactionRequestModel;
import com.bankingusecase.account.domain.service.response.AccountRest;
import com.bankingusecase.bank.api.dto.AddBankDto;
import com.bankingusecase.bank.api.dto.UpdateBankDto;
import com.bankingusecase.bank.application.service.BankServiceImpl;
import com.bankingusecase.bank.domain.model.Bank;
import com.bankingusecase.bank.domain.service.request.BankDetailsRequestModel;
import com.bankingusecase.bank.domain.service.response.BankRest;
import com.bankingusecase.bank.domain.service.response.OperationStatusModel;
import com.bankingusecase.bank.domain.service.response.RequestOperationName;
import com.bankingusecase.bank.domain.service.response.RequestOperationStatus;
import com.bankingusecase.bank.exception.BankServiceException;
import com.bankingusecase.bank.exception.ErrorMessages;
import com.bankingusecase.customer.application.service.CustomerServiceImpl;
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
@RequestMapping("bank")
public class BankController {

    @Autowired
    BankServiceImpl bankService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    CustomerServiceImpl customerService;

    @GetMapping(path = "/{id}")
    public BankRest getBank(@PathVariable String id) throws Exception {

        BankRest returnValue = new BankRest();

        AddBankDto bankDto = bankService.getBank(id);
        if (bankDto == null) throw new BankServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(bankDto, returnValue);

        return returnValue;
    }

    @GetMapping
    public List<Bank> getAllBank() throws Exception {
        List<Bank> bank = bankService.getAllBank();
        if (bank == null) throw new BankServiceException(ErrorMessages.NO_RECORD_AVAILABLE.getErrorMessage());
        return bank;
    }

    @PostMapping
    public BankRest addBank(@Valid @RequestBody BankDetailsRequestModel bankDetails) throws Exception {
        BankRest returnValue = new BankRest();

        if (bankDetails.getBankName().isEmpty())
            throw new BankServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (bankDetails.getAddress().isEmpty())
            throw new BankServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());


        AddBankDto bankDto = new AddBankDto();
        BeanUtils.copyProperties(bankDetails, bankDto);

        AddBankDto createdUser = bankService.addBank(bankDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }


    @PutMapping(path = "/{id}")
    public BankRest updateBank(@PathVariable String id, @Valid @RequestBody BankDetailsRequestModel bankDetails) throws Exception {

        BankRest returnValue = new BankRest();

        UpdateBankDto bankDto = new UpdateBankDto();
        BeanUtils.copyProperties(bankDetails, bankDto);

        UpdateBankDto updatedBank = bankService.updateBank(id, bankDto);
        if (updatedBank == null) throw new BankServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(updatedBank, returnValue);
        return returnValue;
    }


    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteBank(@PathVariable String id) throws Exception {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        bankService.deleteBank(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;


    }

    @PostMapping(path = "/account")
    public AccountRest addAccount(@Valid @RequestBody AccountDetailsRequestModel accountDetails) throws Exception {
        AccountRest returnValue = new AccountRest();

        if (accountDetails.getCustomerId().isEmpty())
            throw new BankServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (accountDetails.getBankCode().isEmpty())
            throw new BankServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (accountDetails.getAccountType().isEmpty())
            throw new BankServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());


        AddAccountDto accountDto = new AddAccountDto();

        BeanUtils.copyProperties(accountDetails, accountDto);

        AddAccountDto createdAccount = accountService.addAccount(accountDto);
        BeanUtils.copyProperties(createdAccount, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/credit/{id}")
    public AccountRest credit(@PathVariable String id, @Valid @RequestBody AccountTransactionRequestModel amount) throws Exception {
        AccountRest returnValue = new AccountRest();

        if (amount.getAmount() <= 0) throw new BankServiceException(ErrorMessages.TRANSACTION_AMOUNT.getErrorMessage());

        AccountTransactionDto accountDto = new AccountTransactionDto();
        BeanUtils.copyProperties(amount, accountDto);

        UpdateAccountDto updatedAccount = accountService.credit(id, accountDto);
        if (updatedAccount == null) throw new BankServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        BeanUtils.copyProperties(updatedAccount, returnValue);
        return returnValue;
    }

    @PutMapping(path = "/debit/{id}")
    public AccountRest debit(@PathVariable String id, @Valid @RequestBody AccountTransactionRequestModel amount) throws Exception {
        AccountRest returnValue = new AccountRest();

        if (amount.getAmount() <= 0) throw new BankServiceException(ErrorMessages.TRANSACTION_AMOUNT.getErrorMessage());

        AccountTransactionDto accountDto = new AccountTransactionDto();
        BeanUtils.copyProperties(amount, accountDto);

        UpdateAccountDto updatedAccount = accountService.debit(id, accountDto);
        if (updatedAccount == null) throw new BankServiceException(ErrorMessages.TRANSACTION_FAILED.getErrorMessage());
        BeanUtils.copyProperties(updatedAccount, returnValue);
        return returnValue;
    }


    @GetMapping(path = "/details")
    public List<CustomerDetailsDto> getCustomerDetails() throws Exception {
        List<CustomerDetailsDto> details = accountService.getAllCustomerDetails();
        if (details == null) throw new BankServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        return details;
    }

    @GetMapping(path = "/details/{id}")
    public CustomerDetailsDto getCustomerDetails(@PathVariable String id) throws Exception {

        CustomerDetailsDto returnValue = new CustomerDetailsDto();

        CustomerDetailsDto customerDetails = accountService.getCustomerDetails(id);
        if (customerDetails == null) throw new BankServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(customerDetails, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/account/{id}")
    public OperationStatusModel deleteAccount(@PathVariable String id) throws Exception {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        accountService.deleteAccount(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}

package com.bankingusecase.account.api;

import com.bankingusecase.account.api.dto.AddAccountDto;
import com.bankingusecase.account.api.dto.UpdateAccountDto;
import com.bankingusecase.account.application.service.AccountServiceImpl;
import com.bankingusecase.account.domain.model.Account;
import com.bankingusecase.account.domain.service.request.AccountDetailsRequestModel;
import com.bankingusecase.account.domain.service.request.AccountTransactionRequestModel;
import com.bankingusecase.account.domain.service.response.AccountRest;
import com.bankingusecase.account.domain.service.response.OperationStatusModel;
import com.bankingusecase.account.domain.service.response.RequestOperationName;
import com.bankingusecase.account.domain.service.response.RequestOperationStatus;
import com.bankingusecase.account.exception.AccountServiceException;
import com.bankingusecase.account.exception.ErrorMessages;
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
@RequestMapping("account")
public class AccountController {

    @Autowired
    AccountServiceImpl accountService;

    @GetMapping(path = "/{id}")
    public AccountRest getAccount(@PathVariable String id) throws Exception {
        AccountRest returnValue = new AccountRest();

        AddAccountDto accountDto = accountService.getAccount(id);
        if (accountDto == null) throw new AccountServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(accountDto, returnValue);

        return returnValue;
    }

    @GetMapping
    public List<Account> getAllAccounts() throws Exception {
        List<Account> accounts = accountService.getAllAccount();
        if (accounts == null) throw new AccountServiceException(ErrorMessages.NO_RECORD_AVAILABLE.getErrorMessage());
        return accounts;
    }

    @PostMapping
    public AccountRest addAccount(@Valid @RequestBody AccountDetailsRequestModel accountDetails) throws Exception {
        AccountRest returnValue = new AccountRest();

        if (accountDetails.getCustomerId().isEmpty())
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (accountDetails.getBankCode().isEmpty())
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if (accountDetails.getAccountType().isEmpty())
            throw new AccountServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());


        AddAccountDto accountDto = new AddAccountDto();
        BeanUtils.copyProperties(accountDetails, accountDto);

        AddAccountDto createdAccount = accountService.addAccount(accountDto);
        BeanUtils.copyProperties(createdAccount, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public AccountRest updateAccount(@PathVariable String id, @Valid @RequestBody AccountTransactionRequestModel accountDetails) throws Exception {
        AccountRest returnValue = new AccountRest();

        UpdateAccountDto accountDto = new UpdateAccountDto();
        BeanUtils.copyProperties(accountDetails, accountDto);

        UpdateAccountDto updatedAccount = accountService.updateAccount(id, accountDto);
        if (updatedAccount == null) throw new AccountServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        BeanUtils.copyProperties(updatedAccount, returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteAccount(@PathVariable String id) throws Exception {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        accountService.deleteAccount(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}

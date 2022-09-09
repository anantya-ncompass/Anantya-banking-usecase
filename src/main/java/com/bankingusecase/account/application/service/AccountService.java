package com.bankingusecase.account.application.service;


import com.bankingusecase.account.api.dto.AccountTransactionDto;
import com.bankingusecase.account.api.dto.AddAccountDto;
import com.bankingusecase.account.api.dto.CustomerDetailsDto;
import com.bankingusecase.account.api.dto.UpdateAccountDto;
import com.bankingusecase.account.domain.model.Account;

import java.util.List;


public interface AccountService {
    AddAccountDto addAccount(AddAccountDto account) throws Exception;
    List<Account> getAllAccount();
    AddAccountDto getAccount(String id);
    UpdateAccountDto updateAccount(String id, UpdateAccountDto account);
    void deleteAccount(String id) throws Exception;
    UpdateAccountDto credit (String id, AccountTransactionDto account);
    UpdateAccountDto debit (String id, AccountTransactionDto account) throws Exception;
    List<CustomerDetailsDto> getAllCustomerDetails();
    CustomerDetailsDto getCustomerDetails(String id);

}


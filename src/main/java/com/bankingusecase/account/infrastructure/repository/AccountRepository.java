package com.bankingusecase.account.infrastructure.repository;

import com.bankingusecase.account.api.dto.CustomerDetailsDto;
import com.bankingusecase.account.domain.model.Account;

import java.util.List;

public interface AccountRepository {

    Account addAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccount(String id);

    Account updateAccount(Account account);

    void deleteAccount(String id) throws Exception;

    List<CustomerDetailsDto> getAllCustomerDetails();
    CustomerDetailsDto getCustomerDetails(String id);


}

package com.bankingusecase.account.application.service;


import com.bankingusecase.account.api.dto.AccountTransactionDto;
import com.bankingusecase.account.api.dto.AddAccountDto;
import com.bankingusecase.account.api.dto.CustomerDetailsDto;
import com.bankingusecase.account.api.dto.UpdateAccountDto;
import com.bankingusecase.account.domain.model.Account;
import com.bankingusecase.account.exception.AccountServiceException;
import com.bankingusecase.account.exception.ErrorMessages;
import com.bankingusecase.account.infrastructure.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountUtils accountUtils;

    @Override
    public AddAccountDto addAccount(AddAccountDto account) throws Exception {
        Account accountEntity = new Account();

        BeanUtils.copyProperties(account, accountEntity);

        String publicAccountNumber = accountUtils.generateAccountNumber(10);
        accountEntity.setAccountNumber("ACC" + publicAccountNumber);

        if (account.getAccountType().equals("SAVINGS_ACCOUNT")) {
            accountEntity.setAccountType("SAVINGS_ACCOUNT");
            accountEntity.setBalance(1000);
        } else if (account.getAccountType().equals("CURRENT_ACCOUNT")) {
            accountEntity.setAccountType("CURRENT_ACCOUNT");
            accountEntity.setBalance(0);
        } else {
            throw new AccountServiceException(ErrorMessages.COULD_NOT_CREATE_RECORD.getErrorMessage());
        }

        Account storedUserDetails = accountRepository.addAccount(accountEntity);

        AddAccountDto returnedValue = new AddAccountDto();
        BeanUtils.copyProperties(storedUserDetails, returnedValue);

        return returnedValue;
    }

    @Override
    public AddAccountDto getAccount(String id) {

        try {
            AddAccountDto returnValue = new AddAccountDto();
            Account accountEntity = accountRepository.getAccount(id);

            BeanUtils.copyProperties(accountEntity, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public UpdateAccountDto updateAccount(String id, UpdateAccountDto account) {

        try {
            UpdateAccountDto returnValue = new UpdateAccountDto();
            Account accountEntity = accountRepository.getAccount(id);

            accountEntity.setBalance(account.getBalance());


            Account updatedUserDetails = accountRepository.updateAccount(accountEntity);
            BeanUtils.copyProperties(updatedUserDetails, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Account> getAllAccount() {

        try {
            return this.accountRepository.getAllAccounts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAccount(String id) throws Exception {
       accountRepository.deleteAccount(id);
    }

    @Override
    public UpdateAccountDto credit(String id, AccountTransactionDto account) {

        try {
            UpdateAccountDto returnValue = new UpdateAccountDto();
            Account accountEntity = accountRepository.getAccount(id);

            double amount = accountEntity.getBalance() + account.getAmount();
            accountEntity.setBalance(amount);


            Account updatedUserDetails = accountRepository.updateAccount(accountEntity);
            BeanUtils.copyProperties(updatedUserDetails, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public UpdateAccountDto debit(String id, AccountTransactionDto account) throws Exception {

        try {
            UpdateAccountDto returnValue = new UpdateAccountDto();

            Account accountEntity = accountRepository.getAccount(id);
            double amount = accountEntity.getBalance() - account.getAmount();

            if (amount < 0) {
                throw new AccountServiceException(ErrorMessages.NOT_SUFFICIENT.getErrorMessage());
            } else {
                accountEntity.setBalance(amount);

            }
            Account updatedUserDetails = accountRepository.updateAccount(accountEntity);

            BeanUtils.copyProperties(updatedUserDetails, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<CustomerDetailsDto> getAllCustomerDetails() {
        return this.accountRepository.getAllCustomerDetails();
    }

    @Override
    public CustomerDetailsDto getCustomerDetails(String id) {
        try {
            CustomerDetailsDto returnValue = new CustomerDetailsDto();
            CustomerDetailsDto customerDetails = accountRepository.getCustomerDetails(id);

            BeanUtils.copyProperties(customerDetails, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

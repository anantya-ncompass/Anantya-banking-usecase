package com.bankingusecase.bank.infrastructure.repository;

import com.bankingusecase.bank.domain.model.Bank;

import java.util.List;

public interface BankRepository {

    Bank addBank(Bank bank);

    List<Bank> getAllBanks();

    Bank getBank(String id);

    Bank updateBank(Bank bank);

    void deleteBank(String id) throws Exception;

//    List<Bank> getAllCustomerDetails();


}

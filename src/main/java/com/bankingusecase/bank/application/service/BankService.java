package com.bankingusecase.bank.application.service;


import com.bankingusecase.bank.api.dto.AddBankDto;
import com.bankingusecase.bank.api.dto.UpdateBankDto;
import com.bankingusecase.bank.domain.model.Bank;

import java.util.List;


public interface BankService {
    AddBankDto addBank(AddBankDto bank);
    List<Bank> getAllBank();
    AddBankDto getBank(String id);
    UpdateBankDto updateBank(String id, UpdateBankDto bank);
    void deleteBank(String id) throws Exception;
}


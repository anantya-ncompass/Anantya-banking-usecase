package com.bankingusecase.bank.application.service;


import com.bankingusecase.bank.api.dto.AddBankDto;
import com.bankingusecase.bank.api.dto.UpdateBankDto;
import com.bankingusecase.bank.domain.model.Bank;
import com.bankingusecase.bank.exception.ErrorMessages;
import com.bankingusecase.bank.infrastructure.repository.BankRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BankUtils bankUtils;

    @Override
    public AddBankDto addBank(AddBankDto bank) {

        try {
            Bank bankEntity = new Bank();
            BeanUtils.copyProperties(bank, bankEntity);

            String publicCustomerId = bankUtils.generateBankId(6);
            bankEntity.setCode(bank.getBankName() + publicCustomerId);

            Bank storedUserDetails = bankRepository.addBank(bankEntity);

            AddBankDto returnedValue = new AddBankDto();
            BeanUtils.copyProperties(storedUserDetails, returnedValue);

            return returnedValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AddBankDto getBank(String id) {

        try {
            AddBankDto returnValue = new AddBankDto();
            Bank bankEntity = bankRepository.getBank(id);

            BeanUtils.copyProperties(bankEntity, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bank> getAllBank() {

        try {
            return this.bankRepository.getAllBanks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UpdateBankDto updateBank(String id, UpdateBankDto bank) {

        try {
            UpdateBankDto returnValue = new UpdateBankDto();
            Bank bankEntity = bankRepository.getBank(id);

            bankEntity.setBankName(bank.getBankName());
            bankEntity.setAddress(bank.getAddress());

            Bank updatedUserDetails = bankRepository.updateBank(bankEntity);
            BeanUtils.copyProperties(updatedUserDetails, returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteBank(String id) throws Exception {
        try {
            bankRepository.deleteBank(id);
        }
        catch (Exception e) {
            throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }

    }

}

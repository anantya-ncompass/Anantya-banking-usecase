package com.bankingusecase.bank.infrastructure.repository;

import com.bankingusecase.bank.exception.ErrorMessages;
import com.bankingusecase.bank.domain.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class BankRepositoryImpl implements BankRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String ADD_BANK = "insert into bank ( code, bank_name, address) values(?, ?, ?)";

    private static final String GET_ALL_BANKS = "select code, bank_name, address from bank";

    private static final String GET_BANK = "select code, bank_name, address from bank where code = ?";

    private static final String UPDATE_BANK = "update bank set bank_name = ?, address = ? where code = ?";

    private static final String DELETE_BANK = "delete from bank where code = ?";

    static class BankRowMapper implements RowMapper<Bank> {
        @Override
        public Bank mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bank bank = new Bank();
            bank.setCode(rs.getString("code"));
            bank.setBankName(rs.getString("bank_name"));
            bank.setAddress(rs.getString("address"));
            return bank;
        }
    }

    @Override
    public Bank addBank(Bank bank) {
        this.jdbcTemplate.update(ADD_BANK,
                bank.getCode(),
                bank.getBankName(),
                bank.getAddress());
        return bank;
    }

    @Override
    public List<Bank> getAllBanks() {
        return jdbcTemplate.query(GET_ALL_BANKS,
                new BankRowMapper());
    }

    @Override
    public Bank getBank(String id) {
        return jdbcTemplate.queryForObject(GET_BANK,
                new Object[]{id},
                new BeanPropertyRowMapper<Bank>(Bank.class));
    }

    @Override
    public Bank updateBank(Bank bank) {
        this.jdbcTemplate.update(UPDATE_BANK,
                bank.getBankName(),
                bank.getAddress(),
                bank.getCode());
        return bank;
    }

    @Override
    public void deleteBank(String id) throws Exception {
        try {
            int bank = jdbcTemplate.update(DELETE_BANK, id);
            if (bank != 1) throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
        catch (Exception e) {
            throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }


    }

}

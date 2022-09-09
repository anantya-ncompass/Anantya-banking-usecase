package com.bankingusecase.account.infrastructure.repository;

import com.bankingusecase.account.api.dto.CustomerDetailsDto;
import com.bankingusecase.account.domain.model.Account;
import com.bankingusecase.account.exception.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String ADD_ACCOUNT = "insert into account (account_number, customer_id, bank_code, " +
            "account_type, balance) values(?, ?, ?, ?, ?)";

    private static final String GET_ALL_ACCOUNTS = "select account_number, customer_id, bank_code, account_type," +
            " balance from account";

    private static final String GET_ACCOUNT = "select account_number, customer_id, bank_code, account_type," +
            " balance from account where account_number = ?";

    private static final String UPDATE_ACCOUNT = "update account set balance = ? where account_number = ?";

    private static final String DELETE_ACCOUNT = "delete from account where account_number = ?";

    private static final String GET_ALL_CUSTOMER_DETAILS = "select a.customer_id, c.name, c.address, c.phone_number," +
            " a.bank_code, b.bank_name, b.address as bank_address," +
            " a.account_number, a.account_type, a.balance from customers as c" +
            " inner join account as a on (c.id=a.customer_id)" +
            " inner join bank as b on (a.bank_code=b.code)";

    private static final String GET_CUSTOMER_DETAILS = "select a.customer_id, c.name, c.address, c.phone_number," +
            " a.bank_code, b.bank_name, b.address as bank_address," +
            " a.account_number, a.account_type, a.balance from customers as c" +
            " inner join account as a on (c.id=a.customer_id)" +
            " inner join bank as b on (a.bank_code=b.code) where a.customer_id = ?";


    static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setAccountNumber(rs.getString("account_number"));
            account.setCustomerId(rs.getString("customer_id"));
            account.setBankCode(rs.getString("bank_code"));
            account.setAccountType((rs.getString("account_type")));
            account.setBalance(rs.getDouble("balance"));
            return account;
        }
    }

    static class CustomerDetailsMapper implements RowMapper<CustomerDetailsDto> {

        @Override
        public CustomerDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
            customerDetailsDto.setCustomerId(rs.getString("customer_id"));
            customerDetailsDto.setName(rs.getString("name"));
            customerDetailsDto.setAddress(rs.getString("address"));
            customerDetailsDto.setPhoneNumber(rs.getString("phone_number"));
            customerDetailsDto.setBankCode(rs.getString("bank_code"));
            customerDetailsDto.setBankName(rs.getString("bank_name"));
            customerDetailsDto.setBankAddress(rs.getString("bank_address"));
            customerDetailsDto.setAccountNumber(rs.getString("account_number"));
            customerDetailsDto.setAccountType((rs.getString("account_type")));
            customerDetailsDto.setBalance(rs.getDouble("balance"));
            return customerDetailsDto;
        }
    }

    @Override
    public Account addAccount(Account account) {
        this.jdbcTemplate.update(ADD_ACCOUNT,
                account.getAccountNumber(),
                account.getCustomerId(),
                account.getBankCode(),
                account.getAccountType(),
                account.getBalance());
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return jdbcTemplate.query(GET_ALL_ACCOUNTS,
                new AccountRowMapper());
    }

    @Override
    public Account getAccount(String id) {
        return jdbcTemplate.queryForObject(GET_ACCOUNT,
                new Object[]{id},
                new BeanPropertyRowMapper<Account>(Account.class));
    }

    @Override
    public Account updateAccount(Account account) {
        this.jdbcTemplate.update(UPDATE_ACCOUNT,
                account.getBalance(),
                account.getAccountNumber());
        return account;
    }

    @Override
    public void deleteAccount(String id) throws Exception {
        int bank = jdbcTemplate.update(DELETE_ACCOUNT,
                id);
        if (bank != 1) throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());

    }

    @Override
    public List<CustomerDetailsDto> getAllCustomerDetails() {
        return jdbcTemplate.query(GET_ALL_CUSTOMER_DETAILS, new CustomerDetailsMapper());
    }

    @Override
    public CustomerDetailsDto getCustomerDetails(String id) {
        return jdbcTemplate.queryForObject(GET_CUSTOMER_DETAILS,
                new Object[]{id},
                new BeanPropertyRowMapper<CustomerDetailsDto>(CustomerDetailsDto.class));
    }
}

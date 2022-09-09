package com.bankingusecase.customer.infrastructure.repository;

import com.bankingusecase.customer.exception.ErrorMessages;
import com.bankingusecase.customer.domain.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String ADD_CUSTOMER = "insert into customers ( id, name, address, phone_number)" +
            "values(?, ?, ?, ?)";

    private static final String GET_ALL_CUSTOMERS = "select id, name, address, phone_number from customers";

    private static final String GET_CUSTOMER = "select id, name, address, phone_number from customers where id = ?";

    private static final String UPDATE_CUSTOMER = "update customers set name = ?," +
            "address = ?, phone_number = ? where id = ?";

    private static final String DELETE_CUSTOMER = "delete from customers where id = ?";


    static class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
            customer.setName(rs.getString("name"));
            customer.setAddress(rs.getString("address"));
            customer.setPhoneNumber(rs.getString("phone_number"));
            return customer;
        }
    }

    @Override
    public Customer addCustomer(Customer customer) throws Exception {
        this.jdbcTemplate.update(ADD_CUSTOMER,
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhoneNumber());

        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query(GET_ALL_CUSTOMERS,
                new CustomerRowMapper());
    }

    @Override
    public Customer getCustomer(String id) throws Exception {
        Customer customer = this.jdbcTemplate.queryForObject(GET_CUSTOMER,
                new Object[]{id},
                new BeanPropertyRowMapper<Customer>(Customer.class));
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        this.jdbcTemplate.update(UPDATE_CUSTOMER,
                customer.getName(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getId());
        return customer;
    }

    @Override
    public void deleteCustomer(String id) throws Exception {

        try {
            int customer = jdbcTemplate.update(DELETE_CUSTOMER,
                    id);
            if (customer != 1) throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
        catch (Exception e) {
            throw new Exception(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }


    }
}

package org.prgrms.kdt.jdbcRepository;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final String SELECT_BY_NAME_SQL = "select * from customers where name = :name";
    private final String SELECT_BY_EMAIL_SQL = "select * from customers where email = :email";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String UPDATE_BY_ID_SQL = "update customers set name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_ID_SQL = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";

    private HashMap<String, Object> toParamMap(CustomerEntity customer) {
        return new HashMap<>() {
            {
                put("customerId", customer.getCustomerId().toString().getBytes());
                put("name", customer.getName());
                put("email", customer.getEmail());
                put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
                put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            }
        };
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static RowMapper<CustomerEntity> customerEntityRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new CustomerEntity(customerId, customerName, email, lastLoginAt, createdAt);
    };

    @Override
    public CustomerEntity insert(CustomerEntity customer) {
        var update = jdbcTemplate.update(INSERT_SQL,
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was Inserted");
        }
        return customer;
    }

    @Override
    public CustomerEntity update(CustomerEntity customer) {
        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was Updated");
        }
        return customer;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerEntityRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId) {
        var customerIdMap = Collections.singletonMap("customerId", customerId.toString().getBytes());
        jdbcTemplate.update("delete from customers where customer_id = UUID_TO_BIN(:customerId)", customerIdMap);
    }

    @Override
    public Optional<CustomerEntity> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerEntityRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerEntity> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_NAME_SQL,
                    Collections.singletonMap("name", name),
                    customerEntityRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                    Collections.singletonMap("email", email),
                    customerEntityRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }
}

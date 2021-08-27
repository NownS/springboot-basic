package org.prgrms.kdt.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.kdt.model.Customer;
import org.prgrms.kdt.model.CustomerType;
import org.prgrms.kdt.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class MemCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();

    @Value("classpath:${kdt.file.black-list.path}")
    private Resource resource;

    @Override
    public Map<UUID, Customer> findAllBlackListCustomer() {
        var blacklists = FileUtils.readCSV(resource);
        blacklists.stream()
            .map(x -> new Customer(UUID.fromString(x[0]), x[1], CustomerType.BLACKLIST))
            .forEach(obj -> storage.put(obj.getCustomerId(), obj));
        return storage;
    }


}
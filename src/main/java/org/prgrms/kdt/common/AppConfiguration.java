package org.prgrms.kdt.common;

import org.prgrms.kdt.domain.Order;
import org.prgrms.kdt.domain.OrderRepository;
import org.prgrms.kdt.domain.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.strategy.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
package org.prgrms.kdt.voucher.application;

import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.repository.MemoryRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherType.*;

public class VoucherService {

    private final MemoryRepository temporaryRepository;
    private final VoucherRepository voucherRepository;
    private final OrderRepository orderRepository;

    public VoucherService(MemoryRepository temporaryRepository, VoucherRepository voucherRepository, OrderRepository orderRepository) {
        this.temporaryRepository = temporaryRepository;
        this.voucherRepository = voucherRepository;
        this.orderRepository = orderRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for" + voucherId));
    }

    public Voucher addVoucher(Voucher voucher) {
        return temporaryRepository.addVoucher(voucher);
    }

    public List<Voucher> allVoucher() {
        return temporaryRepository.findByAllVouchers();
    }

    public void useVoucher(Voucher voucher) {
    }

    public VoucherType choiceVoucher(String type) {
        return findByVoucherType(type);
    }

    public Voucher createVoucher(VoucherType type, String value) {
        if (type == FIXED) {
            return new PercentDiscountVoucher(UUID.randomUUID(), parseLong(value));
        }
        return new FixedAmountVoucher(UUID.randomUUID(), parseLong(value));
    }

    private long parseLong(String value) {
        return Long.parseLong(value);
    }


}
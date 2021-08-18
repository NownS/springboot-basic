package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherRepositoryImpl implements VoucherRepository{
    List<Voucher> voucherList;

    public VoucherRepositoryImpl(){
        voucherList = new ArrayList<>();
    }

    @Override
    public Optional<Voucher> findId(UUID voucherId) {
        for(Voucher voucher : voucherList){
            if(voucher.getVoucherId() == voucherId){
                return Optional.of(voucher);
            }
        }

        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        voucherList.add(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherList;
    }
}

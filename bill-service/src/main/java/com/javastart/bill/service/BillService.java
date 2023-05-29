package com.javastart.bill.service;

import com.javastart.bill.entity.Bill;
import com.javastart.bill.exception.BillNotFoundException;
import com.javastart.bill.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    public Long createBill(Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        return billRepository.save(Bill.builder().accountId(accountId).amount(amount).
                isDefault(isDefault).overdraftEnabled(overdraftEnabled).creationDate(OffsetDateTime.now()).build())
                .getBillId();
    }
    
    public Bill getBillById(Long billId) {
        return billRepository.findById(billId).orElseThrow(() -> new BillNotFoundException(String.format("Bill with such id %d doesn't exist")));
    }
    
    public Bill updateBill(Long billId, Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        Bill changingBill = getBillById(billId);
        changingBill.setAccountId(accountId);
        changingBill.setAmount(amount);
        changingBill.setIsDefault(isDefault);
        changingBill.setOverdraftEnabled(overdraftEnabled);
        return billRepository.save(changingBill);
    }

    public Bill deleteBill(Long billId) {
        Bill deletingBill = getBillById(billId);
        billRepository.deleteById(billId);
        return deletingBill;
    }

    public List<Bill> getBillsByAccountId(Long accountId) {
        return billRepository.getBillsByAccountId(accountId);
    }
}

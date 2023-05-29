package com.javastart.bill.controller.dto;

import com.javastart.bill.entity.Bill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class BillResponseDTO {

    private Long billId;
    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private OffsetDateTime creationDate;
    private Boolean overdraftEnabled;

    public BillResponseDTO(Bill bill) {
        billId = bill.getBillId();
        accountId = bill.getAccountId();
        amount =bill.getAmount();
        isDefault = bill.getIsDefault();
        overdraftEnabled = bill.getOverdraftEnabled();
        creationDate = bill.getCreationDate();
    }
}

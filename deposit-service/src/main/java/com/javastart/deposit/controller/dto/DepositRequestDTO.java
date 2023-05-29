package com.javastart.deposit.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class DepositRequestDTO {
    private Long accountId;
    private Long billId;
    private BigDecimal amount;
}

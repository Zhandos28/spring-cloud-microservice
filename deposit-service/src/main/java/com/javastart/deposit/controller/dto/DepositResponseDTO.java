package com.javastart.deposit.controller.dto;

import com.javastart.deposit.entity.Deposit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponseDTO {
    private String email;
    private BigDecimal amount;

    public DepositResponseDTO(Deposit deposit) {
        email = deposit.getEmail();
        amount = deposit.getAmount();
    }
}

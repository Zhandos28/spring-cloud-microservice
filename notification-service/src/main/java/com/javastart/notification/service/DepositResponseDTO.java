package com.javastart.notification.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DepositResponseDTO {
    private String email;
    private BigDecimal amount;
}

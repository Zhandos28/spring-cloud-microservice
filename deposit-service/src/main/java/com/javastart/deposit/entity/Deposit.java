package com.javastart.deposit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "deposits")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long depositId;

    private BigDecimal amount;

    private Long billId;

    private OffsetDateTime creationDate;
    private String email;

    public Deposit(Long billId, BigDecimal amount, String email, OffsetDateTime creationDate) {
        this.billId = billId;
        this.amount = amount;
        this.email = email;
        this.creationDate = creationDate;
    }
}

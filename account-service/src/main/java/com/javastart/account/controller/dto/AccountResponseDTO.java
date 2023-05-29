package com.javastart.account.controller.dto;

import com.javastart.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccountResponseDTO {

    private Long accountId;
    private String name;
    private String email;
    private String phone;
    private OffsetDateTime creationDate;

    private List<Long> bills;

    public AccountResponseDTO(Account account) {
        accountId = account.getAccountId();
        name = account.getPhone();
        email = account.getEmail();
        phone = account.getPhone();
        creationDate = account.getCreationDate();
        bills = account.getBills();
    }
}

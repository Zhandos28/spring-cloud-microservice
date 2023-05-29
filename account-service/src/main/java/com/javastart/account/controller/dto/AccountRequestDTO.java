package com.javastart.account.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {
    private String name;
    private String email;
    private String phone;
    private List<Long> bills;
}

package com.javastart.account.service;

import com.javastart.account.entity.Account;
import com.javastart.account.exception.AccountNotFoundException;
import com.javastart.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(String.format("Account with such id %d doesn't exist", accountId)));
    }

    public Long createAccount(String name, String email, String phone, List<Long> bills) {
        return accountRepository.save(Account.builder().name(name).phone(phone).email(email).creationDate(OffsetDateTime.now()).bills(bills).build()).getAccountId();
    }
    
    public Account updateAccount(Long accountId, String name, String email, String phone, List<Long> bills) {
        Account changingAccount = getAccountById(accountId);
        changingAccount.setName(name);
        changingAccount.setPhone(phone);
        changingAccount.setEmail(email);
        changingAccount.setBills(bills);
        return accountRepository.save(changingAccount);
    }

    public Account deleteAccount(Long accountId) {
        Account account = getAccountById(accountId);
        accountRepository.deleteById(accountId);
        return account;
    }
}

package com.example.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account newAccount) {
        return accountRepository.save(newAccount);
    }

    public void login(String username, String password) throws AuthenticationException {
        accountRepository.findByUsernameAndPassword(username, password);
    }
}

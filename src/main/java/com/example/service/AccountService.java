package com.example.service;

import java.util.Optional;

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

    public Account login(String username, String password) throws AuthenticationException {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public boolean isUsernameTaken(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean existsById(int id) {
        return accountRepository.existsById(id);
    }

    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }
}

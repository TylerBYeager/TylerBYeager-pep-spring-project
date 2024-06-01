package com.example.repository;
import com.example.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository  extends JpaRepository<Account, Integer>{

    Account findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsById(int id);

}

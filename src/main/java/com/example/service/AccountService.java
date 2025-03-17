// package com.example.service;
import com.*;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// import com.Message;
// import com.example.repository.AccountRepository;

import com.example.entity.Account;
// import com.example.entity.Message;
// import com.example.repository.AccountRepository;


@Component
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    // Create New Account
    public Account persistAccount(Account account) {
        return accountRepository.save(account);
    }

    // Retrieve A List Of All Existing Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Retrieve A Specific Account Based On Its accountId
    public Account getExistingAccountById(int id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }
    }

    // Delete A Specific Account Based On Its accountId
    public int deleteExistingMessage(int Id) {
        try {
            accountRepository.deleteById(Id);
            return 1;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


}

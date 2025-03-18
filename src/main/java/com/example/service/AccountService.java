package com.example.service;
// import com.*;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.example.entity.Account;


@Component
public class AccountService {

    @Autowired
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

    // Retrieve A Specific Account Based On Its Username
    public Account getExistingAccountByUsername(Account account) {
        String usernameBeingChecked = account.getUsername();
        Optional<Account> optionalAccount = accountRepository.findByUsername(usernameBeingChecked);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }
    }  
    
    // Check if account is present and username and passwords match
    public Boolean isValidAccount(Account account) {
        String usernameBeingChecked = account.getUsername();
        String passwrodBeingChecked = account.getPassword();
        try {
            Optional<Account> optionalAccount = accountRepository.findByUsername(usernameBeingChecked);
            if (optionalAccount.isPresent()) {
                String currentPassword = optionalAccount.get().getPassword();
                if (currentPassword == passwrodBeingChecked) {
                    return true;
                }
            } else {
                return false;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Delete A Specific Account Based On Its accountId
    public int deleteExistingAccount(int Id) {
        try {
            accountRepository.deleteById(Id);
            return 1;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


}

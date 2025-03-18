package com.example.service;
// import com.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
@Transactional
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
        String username = account.getUsername();
        // Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        // if (optionalAccount.isPresent()) {
        //     Account accountBeingReturned = optionalAccount.get();
        //     return accountBeingReturned;
        // } else {
        //     return null;
        // }
        Account optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount != null) {
            return optionalAccount;
        } else {
            return null;
        }
    }  
    
    // Check if account is present and username and passwords match
    public Boolean isValidAccount(Account account) {
        String username = account.getUsername();
        String passwrodBeingChecked = account.getPassword();
        try {
            Account optionalAccount = accountRepository.findByUsername(username);
            if (optionalAccount != null) {
                    String currentPassword = optionalAccount.getPassword();
                    if (currentPassword == passwrodBeingChecked) {
                        return true;
            }} else {
                return false;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }  
    
    // Check if account is present and username and passwords match
    public Boolean isValidAccountById(int accountId) {
        try {
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                        return true;
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

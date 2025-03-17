package com.example.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;


@Component
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    // Create New Account
    public Message persistMessage(Account account) {
        return accountRepository.save(account);
    }

    // Retrieve A List Of All Existing Account
    public List<Message> getAllMessages() {
        return accountRepository.findAll();
    }

    // Retrieve A Specific Account Based On Its accountId
    public List<Message> getExistingMessageById(int id) {
        Optional<Message> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }
    }

    // Delete A Specific Account Based On Its accountId
    public int deleteExistingMessage(int Id) {
        return accountRepository.deleteById(Id);
    }


}

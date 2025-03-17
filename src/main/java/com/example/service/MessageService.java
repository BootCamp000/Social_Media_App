package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Create New Message
    public Message persistMessage(Message message) {
        return messageRepository.save(message);
    }

    // Retrieve A List Of All Existing Messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Retrieve A Specific Message Based On Its MessageId
    // ** Retrieve A Specific Message For A User Based On Its MessageId
    public Message getExistingMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    // Retrieve All Messages Based On AccountId
    // ** Retrieve A Specific Message For A User Based On Its MessageId
    public List<Message> getAllMessagesByAccountId(int id) {
        List<Message> messagesToBeReturned = messageRepository.findMessagesByAccountId(id);
        if (!messagesToBeReturned.isEmpty()) {
            return messagesToBeReturned;
        } else {
            return null;
        }
    }

    // Delete A Specific Message Based On Its MessageId
    public int deleteExistingMessage(int Id) {
        try {
            messageRepository.deleteById(Id);
            return 1;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // Update A Specific Message
    public int updateExistingMessage(int Id, Message message) {
        try {
            Message messageToBeUpdated = messageRepository.findById(Id).get();
            messageToBeUpdated.setMessageText(message.getMessageText());
            messageRepository.save(messageToBeUpdated);
            return 1;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


}

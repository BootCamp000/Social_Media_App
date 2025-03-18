package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import com.example.entity.Account;
// import com.example.repository.AccountRepository;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Create New Message
    public Message persistMessage(Message message) {      
        String currentMessageText = message.getMessageText();
        // Check : Message --> messageText is not blank               
        if ((currentMessageText != null) && (currentMessageText != "") && (currentMessageText != " ")) {
            // Sucessful Message Creation Constraints --> Check : Message --> messageText is <= 255 char 
            if (currentMessageText.length() <= 255) {
                return messageRepository.save(message);
            }
        }
        return null;
    }

    // Retrieve A List Of All Existing Messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Retrieve A Specific Message Based On Its MessageId
    // ** Retrieve A Specific Message For A User Based On Its MessageId
    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    // Retrieve All Messages Based On AccountId
    // ** Retrieve A Specific Message For A User Based On Its MessageId
    public List<Message> getAllMessagesByAccountId(int postedBy) {
        List<Message> messagesToBeReturned = messageRepository.findMessagesByAccountId(postedBy);
        if (!messagesToBeReturned.isEmpty()) {
            return messagesToBeReturned;
        } else {
            return null;
        }
    }

    // Delete A Specific Message Based On Its MessageId
    public String deleteExistingMessage(int Id) {
        try {
            if(messageRepository.findById(Id).isPresent()){
                messageRepository.deleteById(Id);
                return "1";
            } 
            // else {
            //     return "0";
            // }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
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

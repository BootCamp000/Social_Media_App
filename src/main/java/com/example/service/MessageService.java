package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.ArrayList;
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

    // Retrieve A Specific Message For A User Based On Its MessageId
    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    // Retrieve All Messages For A User 
    public List<Message> getAllMessagesByAccountId(int postedBy) {
        List<Message> messagesToBeReturned = messageRepository.findAll();
        List<Message> filteredMessagesToBeReturned = new ArrayList<>();
        for(int i=0; i<messagesToBeReturned.size(); i++) {
            if(messagesToBeReturned.get(i).getPostedBy() == postedBy) {
                filteredMessagesToBeReturned.add(messagesToBeReturned.get(i));
            }
        }
       
        return filteredMessagesToBeReturned;
    }

    // Delete A Specific Message Based On Its MessageId
    public String deleteExistingMessage(int Id) {
        try {
            if(messageRepository.findById(Id).isPresent()){
                messageRepository.deleteById(Id);
                return "1";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Update A Specific Message
    public String updateExistingMessage(int Id, Message message) {
        try {
            if((message.getMessageText().length() < 255) && (message.getMessageText().length() > 0)) {
                Optional<Message> existingMessage = messageRepository.findById(Id);
                if(existingMessage.isPresent()) {
                    Message messageToBeUpdated = existingMessage.get();
                    messageToBeUpdated.setMessageText(message.getMessageText());
                    messageRepository.save(messageToBeUpdated);
                    return "1";
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    
}

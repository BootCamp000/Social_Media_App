// package com.example.service;
package com;
// import com.example.entity.Account;
// import com.example.entity.Message;
// import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(Message messageRepository) {
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
    public List<Message> getExistingMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    // Delete A Specific Message Based On Its MessageId
    public int deleteExistingMessage(int Id) {
        return messageRepository.deleteById(Id);
    }

    // Update A Specific Message
    public int updateExistingMessage(Message message) {
        int messageid = message.getExistingMessageById();
        // Message messageToBeUpdated = messageRepository.findById(messageid);
        Message messageToBeUpdated = messageRepository.findById(messageid).get();
        messageToBeUpdated.setMessageText(message.getMessageText());
        return messageRepository.update(message);
    }


}

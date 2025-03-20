package com.example.controller;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping("/register")
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) {

        // (User Story 1) : API can process new user registration

        String username = account.getUsername();
        username.replace(" ", "");

        String password = account.getPassword();
        password.replace(" ", "");
        // Check : If Account already exists

        try {
            Account isValidAccount = accountService.isValidAccount(account);
            if (isValidAccount != null){

                // If : Registration Unsucessful --> Cause : Duplicate Username --> Then : Response Status --> 409 (conflict)

                return ResponseEntity.status(409).build();

            } else { 

                // Check : Sucessful Registration
                // Sucessful Account Creation Constraints --> Check : If Username --> Is Not null
                // Sucessful Account Creation Constraints --> Check : If Username --> Is Not Blank

                if ((username != null) && (username != "") && (username != " ")) {

                    // Sucessful Account Creation Constraints --> Check : If Password --> --> Is >= 4 char

                    if ((password != null) && (password.length() >= 4)) {

                        // If : Registration Sucessful --> Then : New Account Persisted To Database
                        Account newlyCreatedAcct = accountService.persistAccount(account);
    
                        // If : Registration Sucessful --> Then : Response Status --> 200 (default)
                        // If : Registration Sucessful --> Then : Response Body --> json of Account including AccountId
                        return ResponseEntity.ok(newlyCreatedAcct);
                    }
                } else {

                    return ResponseEntity.status(409).build();

                }
            }
        } catch(Exception ex){

            ex.printStackTrace();

        }

        return ResponseEntity.status(409).build();
    }


    @PostMapping("/login")
    public ResponseEntity<Account> getExistingAccountEntity(@RequestBody Account account) {

        // (User Story 2) : API can process  user logins

        try {

            // Check : Sucessful Login   
            // Sucessful Login Constraints --> Check : If Account exists --> Username & Password match real account on database

            Account isValidAccount = accountService.isValidAccount(account);

            if (isValidAccount != null){

                // If : Login Sucessful --> Then : Response Status --> 200 (OK) --> default
                // If : Login Sucessful --> Then : Response Body --> json of Account including AccountId

                return ResponseEntity.ok(isValidAccount);

            } else { 

                // If : Login Unsucessful --> Then : Response Status --> 401 (unauthorized)
                return ResponseEntity.status(401).build();

            }
        } catch(Exception ex){

            ex.printStackTrace();

        } 

        return ResponseEntity.status(401).build();
    }
    
    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        
        // (User Story 3) : API can process creation of new messages

        // Sucessful Message Creation Constraints --> Check : Message --> postedBy refers to a REAL, EXISTING User
        try {

            String currentMessageText = message.getMessageText();

            // Check : Message --> messageText is not blank            

            if ((currentMessageText != null) && (currentMessageText != "") && (currentMessageText != " ")) {

                // Sucessful Message Creation Constraints --> Check : Message --> messageText is <= 255 char 

                if (currentMessageText.length() <= 255) {

                    Integer currentAccountId = message.getPostedBy();
                    Account postingUser = accountService.getExistingAccountById(currentAccountId);
                    Account isValidAccount = accountService.isValidAccount(postingUser);

                    if (isValidAccount != null){

                        // If : Message Creation Sucessful --> Then : New Message Persisted To Database

                        Message newlyCreatedMsg = messageService.persistMessage(message);

                        // If : Message Creation Sucessful --> Then : Response Status --> 200 --> default
                        // If : Message Creation Sucessful --> Then : Response Body --> json of message including messagetId

                        return ResponseEntity.ok(newlyCreatedMsg);

                    } else { 
                        
                        return ResponseEntity.status(400).build();

                    }
                }

                return ResponseEntity.badRequest().build();

            } else {
                
                return ResponseEntity.badRequest().build();

            }
        } catch(Exception ex){

            ex.printStackTrace();

        }

        return ResponseEntity.badRequest().build();
    }    

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retrieveAllMessage() {

        // (User Story 4) : API can retrieve all messages

        // Check : If There Are Messages To Display Or Is It An Empty List

        List<Message> messagesBeingReturned = messageService.getAllMessages();

        // If : Message Retrieval Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Retrieval Sucessful --> Then : Response Body --> JSON of list containing ALL messgaes retrieved from the database
        // If : Message Retrieval Sucessful --> No Messages To Display --> Then : Response Body --> Displays an empty list

        return ResponseEntity.ok(messagesBeingReturned);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getExistingMessageById(@PathVariable int messageId) {

        // (User Story 5) : API can retrieve a message by its id

        // Check : If There A Message Exists using a messageId

        Message messageBeingReturned = messageService.getMessageById(messageId);

        // If : Message Retrieval Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Retrieval Sucessful --> Then : Response Body --> JSON of messgae identified by messageId
        // If : Message Retrieval Sucessful --> No Such Messages --> Then : Response Body --> Empty

        return ResponseEntity.status(200).body(messageBeingReturned);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getExistingMessage(@PathVariable int accountId) {
        
        // (User Story 8) : API can retrieve all messages written by a particular user

        // Check : If Message(s) Can Be Retrieved Using An accoundId

        try {

                List<Message> messagesBeingReturned = messageService.getAllMessagesByAccountId(accountId);

                // If : Message Retrieval Sucessful --> Then : Response Status --> 200 (OK) --> default
                // If : Message Retrieval Sucessful --> Then : Response Body --> JSON of list containing ALL messgaes posted by a particular user retrieved from the database

                return ResponseEntity.ok(messagesBeingReturned);

        } catch(Exception ex){

            ex.printStackTrace();

        } 

        // If : Message Retrieval Sucessful --> No Messages To Display --> Then : Response Body --> Empty   

        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteExistingMessage(@PathVariable int messageId) {

        // (User Story 6) : API can delete a message identified by a messageId
        
        String wasSucessful = messageService.deleteExistingMessage(messageId);

        return ResponseEntity.ok(wasSucessful);

    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateExistingMessage(@PathVariable int messageId, @RequestBody Message message) {

        // (User Story 7) : API can update a message identified by a messageId

        String updatedMessage = messageService.updateExistingMessage(messageId, message);

        if(updatedMessage == null) {

            return ResponseEntity.badRequest().build();

        } else {

            return ResponseEntity.ok(updatedMessage);

        }
    }
}

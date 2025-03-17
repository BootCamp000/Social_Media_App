// package com.example.controller;
// import com.example.entity.Account;
// import com.example.entity.Message;
// import com.example.service.AccountService;
// import com.example.service.MessageService;
package com;

import org.aspectj.weaver.ast.And;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.Message;
// import com.example.entity.Account;
// import com.example.service.MessageService;

import org.springframework.web.bind.annotation.RequestParam;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @PostMapping("/register")
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) {
        // (User Story 1) : API can process new user registration
        String username = account.getUsername();
        username.replace(" ", "");

        String password = account.getPassword();
        password.replace(" ", "");
        // Check : If Account already exists

        // Check : Sucessful Registration
        // Sucessful Account Creation Constraints --> Check : If Username --> Is Not null
        // Sucessful Account Creation Constraints --> Check : If Username --> Is Not Blank
        if ((username != null) && (username != "") && (username != " ")) {
            // Sucessful Account Creation Constraints --> Check : If Password --> --> Is >= 4 char
            if ((password != null) && (password.length() >= 4)) {
                // If : Registration Sucessful --> Then : New Account Persisted To Database
                // If : Registration Sucessful --> Then : Response Status --> 200 (default)
                // If : Registration Sucessful --> Then : Response Body --> json of Account including AccountId
                return ResponseEntity.status(200).body(new Account(account);
                // return ResponseEntity.status(200).body(new AccountService(account);
            }
        } else { 
            // If : Registration Unsucessful --> Cause : Duplicate Username --> Then : Response Status --> 409 (conflict)
            // If : Registration Unsucessful --> Cause : Other Reason --> Then : Response Status --> 400 (Client Error)
            return ResponseEntity.status(400).body("Client Error");
        }
        return ResponseEntity.status(400).body("Client Error");
    }

    @GetMapping(value= "login", params = "account")
    public ResponseEntity<Account> getExistingAccountEntity(@RequestParam Account account) {
        // (User Story 2) : API can process  user logins


        // Check : Sucessful Login   
        // Sucessful Login Constraints --> Check : If Account exists -->Username & Password match real account on database
        // If : Login Sucessful --> Then : New Account Persisted To Database
        // If : Login Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Login Sucessful --> Then : Response Body --> json of Account including AccountId

        return ResponseEntity.status(200).body(account.getAccountId(), account.getUsername(), account.getPassword());

        // If : Login Unsucessful --> Then : Response Status --> 401 (unauthorized)
        // return ResponseEntity.status(401).body("unauthorized");
    }
    
    @PostMapping(value = "messages", params = {"account","message"})
    public ResponseEntity<Message> createNewMessage(@RequestBody Account account, @RequestBody Message message) {
        // (User Story 3) : API can process creation of new messages


        // Check : Message --> messageText is not blank 
        // Sucessful Message Creation Constraints --> Check : Message --> messageText is <= 255 char
        // Sucessful Message Creation Constraints --> Check : Message --> postedBy refers to a REAL, EXISTING User
        // If : Message Creation Sucessful --> Then : New Message Persisted To Database
        // If : Message Creation Sucessful --> Then : Response Status --> 200 --> default
        // If : Message Creation Sucessful --> Then : Response Body --> json of message including messagetId
        MessageService.persistMessage(message);
        return ResponseEntity.status(200).body("OK");

        // If : Message Creation Unsucessful --> Then : Response Status --> 400 (Client Error)       
        // return ResponseEntity.status(400).body("Client Error");


    }    

    @PostMapping(value = "messages")
    public ResponseEntity<Message> retrieveAllMessage() {
        // (User Story 4) : API can retrieve all messages

        // Check : If There Are Messages To Display Or Is It An Empty List
        // If : Message Retrieval Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Retrieval Sucessful --> Then : Response Body --> JSON of list containing ALL messgaes retrieved from the database
        // If : Message Retrieval Sucessful --> No Messages To Display --> Then : Response Body --> Displays an empty list
        MessageService.getAllMessages();
        return ResponseEntity.status(200).body("OK");

    }

    @GetMapping(value = "messages", params = "messageId")
    public ResponseEntity<Message> getExistingMessageById(RequestBody int messageId) {
        // (User Story 5) : API can retrieve a message by its id

        // Check : If messageId already exists
        // Check : If There A Message Exists using a messageId
        // If : Message Retrieval Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Retrieval Sucessful --> Then : Response Body --> JSON of messgae identified by messageId
        // If : Message Retrieval Sucessful --> No Such Messages --> Then : Response Body --> Empty
        MessageService.getExistingMessageById(messageId);
        return ResponseEntity.status(200).body("OK");


    }

    @GetMapping(value = "messages", params = "accountId")
    public ResponseEntity<Message> getExistingMessage(@RequestBody Account account) {
        // (User Story 8) : API can retrieve all messages written by a particular user

        // Check : If messageId already exists
        // Check : If A Message Can Be Retrieved Using An accoundId
        // If : Message Retrieval Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Retrieval Sucessful --> Then : Response Body --> JSON of list containing ALL messgaes posted by a particular user retrieved from the database
        // If : Message Retrieval Sucessful --> No Messages To Display --> Then : Response Body --> Empty
        // Need To Implement : MessageService.getExistingMessageById(account.getAccountId());
        return ResponseEntity.status(200).body("OK");
    }

    @DeleteMapping(value = "messages", params = "messageId")
    public ResponseEntity<Message> deleteExistingMessage(RequestBody int messageId) {
        // (User Story 6) : API can delete a message identified by a messageId

        // Check : If messageId already exists
        // Check : If Message Exists in database using messageId
        // If : Message Exists --> Then : Update messageText (message_text?) In Database
        // If : Message Deletion Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Deletion Sucessful --> Then : Response Body --> Contains The Number Of Rows Updated (1)
        // MessageService.deleteExistingMessage(messageId);

        return ResponseEntity.status(200).body(MessageService.deleteExistingMessage(messageId));

        // If : Message Deletion Unsucessful --> Cause : Message Did Not Exist --> Then : Response Status --> 200 (OK) --> default
        // If : Message Deletion Unsucessful --> Cause : Message Did Not Exist --> Then : Response Body --> Empty
        // If : Message Deletion Unsucessful --> Cause : Other Reason --> Then : Response Status --> 200 (OK) --> default
        // If : Message Deletion Unsucessful --> Cause : Other Reason --> Then : Response Body --> Empty) 
        // return ResponseEntity.status(200).body("");
    }

    @PatchMapping(value = "messages", params = "messageId")
    public ResponseEntity<Message> updateExistingMessage(RequestBody int messageId) {
        // (User Story 7) : API can update a message identified by a messageId

        // Check : If messageId already exists
        // Sucessful Message Updation Constraints --> Check : If New Message --> new messageText --> Is Not Blank
        // Sucessful Message Updation Constraints --> Check : If New Message --> new messageText --> Is <=255 char
        // Check : If There A Message Exists using a messageId
        // Sucessful Message Updation Constraints : Username & Password match real account on database
        // If : Message Updation Sucessful --> Then : Should've updated messageText in the databse
        // If : Message Updation Sucessful --> Then : Response Status --> 200 (OK) --> default
        // If : Message Updation Sucessful --> Then : Response Body --> Contains The Number Of Rows Updated (1)
        // If : Message Updation Sucessful --> Then : Response Body --> json of Account including AccountId
        // MessageService.updateExistingMessage(messageId);

        return ResponseEntity.status(200).body(MessageService.updateExistingMessage(messageId));

        // If : Message Updation Unsucessful --> Cause : Any Reason --> Then : Response Status --> 400 (Client Error)
        // return ResponseEntity.status(400).body("Client Error");
    }




}

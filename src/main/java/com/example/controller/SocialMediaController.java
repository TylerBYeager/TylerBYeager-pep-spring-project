package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        if(account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }
        if (accountService.isUsernameTaken(account.getUsername())){
            return ResponseEntity.status(409).body(null);
        } 
        Account addedAccount = accountService.register(account);
        return ResponseEntity.status(200).body(addedAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException{
        Account verifiedLogin = accountService.login(account.getUsername(), account.getPassword());
        if(verifiedLogin != null) {
            return ResponseEntity.ok(verifiedLogin);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if(message.getMessageText().isBlank() || message.getMessageText().length() >255 || !accountService.existsById(message.getPostedBy())) {
            return ResponseEntity.status(400).build();
        }
            Message addedMessage = messageService.createNewMessage(message);
            return ResponseEntity.status(200).body(addedMessage);
        
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable int messageId) {
        if(messageService.getMessageById(messageId).isPresent()) {
            return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
        } else {
            return ResponseEntity.status(200).body(null);
        }
        
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        int rowsAffected = messageService.deleteMessageById(messageId);
        if(rowsAffected == 1 ) {
            return ResponseEntity.ok(rowsAffected);
        } else {
            return ResponseEntity.ok().build();
        }
    }
    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        // String text = request.get("messageText");

        int rowsAffected = messageService.updateMessageById(messageId, message.getMessageText());

        if(rowsAffected == 1) {
            return ResponseEntity.ok(rowsAffected);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
    
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> findMessagesByUser(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByUser(accountId);
        return ResponseEntity.ok(messages);
        
    }


}
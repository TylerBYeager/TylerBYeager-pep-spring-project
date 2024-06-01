package com.example.service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createNewMessage(Message newMessage) {
        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    public int deleteMessageById(int id) {
        if(messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

    public int updateMessageById(int messageid, String text) {
        if(text == null || text.isBlank() || text.length() >255) {
            return 0;
        }

        Optional<Message> message = messageRepository.findById(messageid);
        if(message.isPresent()) {
            Message updatedMessage = message.get();
            updatedMessage.setMessageText(text);
            messageRepository.saveAndFlush(updatedMessage);
            return 1;
        } else {
            return 0;
        }

        //alternate method of use
        // return messageRepository.findById(messageid).map(message -> {
        //     message.setMessageText(text);
        //     messageRepository.saveAndFlush(message);
        //     return 1;
        // }).orElse(0);
    }

}

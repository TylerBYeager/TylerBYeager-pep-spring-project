package com.example.service;
import java.util.List;
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

    public void createNewMessage(Message newMessage) {
        messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages;

    }

}

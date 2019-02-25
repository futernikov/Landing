package com.example.Landing.controller;

import com.example.Landing.domain.Message;
import com.example.Landing.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list(){
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFormDB,
            @RequestBody Message message)
    {
        BeanUtils.copyProperties(message, messageFormDB, "id");
        return messageRepo.save(messageFormDB);
    }

    @DeleteMapping("{id}")
        public void delete(@PathVariable("id") Message message){
        messageRepo.delete(message);
    }
}

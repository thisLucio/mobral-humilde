package com.solinftec.equipment.equipmentchat.controllers;

import com.solinftec.equipment.equipmentchat.dtos.MessageDto;
import com.solinftec.equipment.equipmentchat.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> list(
            @RequestParam long userId,
            @RequestParam long equipmentId) {
        return ResponseEntity.ok(messageService.getMessages(userId, equipmentId));
    }
}

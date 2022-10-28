package com.solinftec.equipment.equipmentchat.services;

import com.solinftec.equipment.equipmentchat.domain.Message;
import com.solinftec.equipment.equipmentchat.dtos.MessageDto;
import com.solinftec.equipment.equipmentchat.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getMessages(long userId, long equipmentId) {
        List<Message> messages = messageRepository
                .findByUserIdAndEquipmentIdOrderByTimestampAsc(userId, equipmentId);

        return messages.stream().map(item ->
            new MessageDto(
                    item.getUserId(),
                    item.getEquipmentId(),
                    item.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")),
                    item.getContent(),
                    item.getSender()
            )
        ).toList();
    }

    public void save(MessageDto messageDto) {
        Message message = new Message();
        message.setUserId(messageDto.getUserId());
        message.setEquipmentId(messageDto.getEquipmentId());
        message.setSender(messageDto.getSender());
        message.setContent(messageDto.getContent());
        message.setTimestamp(ZonedDateTime.now(ZoneId.of("GMT")));

        messageRepository.save(message);
    }
}

package com.solinftec.equipment.equipmentchat.repositories;

import com.solinftec.equipment.equipmentchat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserIdAndEquipmentIdOrderByTimestampAsc(Long userId, Long equipmentId);
}

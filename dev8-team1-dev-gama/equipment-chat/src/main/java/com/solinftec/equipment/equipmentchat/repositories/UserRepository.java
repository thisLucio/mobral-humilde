package com.solinftec.equipment.equipmentchat.repositories;

import com.solinftec.equipment.equipmentchat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

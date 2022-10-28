package com.solinftec.equipment.equipmentchat.services;

import com.solinftec.equipment.equipmentchat.domain.User;
import com.solinftec.equipment.equipmentchat.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> list() {
        return userRepository.findAll();
    }
}

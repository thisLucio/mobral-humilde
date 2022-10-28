package com.solinftec.dev8api.repository;

import com.solinftec.dev8api.entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    ApiUser findByUsername(String username);
}


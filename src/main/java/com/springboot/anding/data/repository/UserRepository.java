package com.springboot.anding.data.repository;

import com.springboot.anding.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
        User getByAccount(String account);
}

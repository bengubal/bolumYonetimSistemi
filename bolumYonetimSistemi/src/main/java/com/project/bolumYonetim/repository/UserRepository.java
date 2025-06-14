package com.project.bolumYonetim.repository;

import com.project.bolumYonetim.model.User;
import com.project.bolumYonetim.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
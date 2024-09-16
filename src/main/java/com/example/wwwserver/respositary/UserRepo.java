package com.example.wwwserver.respositary;

import com.example.wwwserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID> { }

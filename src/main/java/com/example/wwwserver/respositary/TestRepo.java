package com.example.wwwserver.respositary;

import com.example.wwwserver.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestRepo extends JpaRepository<TestEntity, UUID> {
}

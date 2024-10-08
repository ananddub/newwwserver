package com.example.wwwserver.respositary;

import com.example.wwwserver.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
}

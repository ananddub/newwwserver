package com.example.wwwserver.respositary;

import com.example.wwwserver.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepo extends JpaRepository<ProductEntity, UUID> {
}

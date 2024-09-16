package com.example.wwwserver.respositary;

import com.example.wwwserver.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepo extends JpaRepository<AddressEntity, UUID> {
}

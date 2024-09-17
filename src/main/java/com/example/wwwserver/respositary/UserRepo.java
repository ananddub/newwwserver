package com.example.wwwserver.respositary;

import com.example.wwwserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.phone = :phone")
    List<UserEntity> findByPhoneAndEmail(@Param("phone") String phone, @Param("email") String email);

    @Query("SELECT u FROM UserEntity u WHERE u.phone = :phone AND u.password=:pass")
    List<UserEntity> findByPhoneAndPassword(@Param("phone") String phone,@Param("pass") String pass);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.password=:pass")
    List<UserEntity> findByEmailAndPassword(@Param("email") String email,@Param("pass") String pass);
}

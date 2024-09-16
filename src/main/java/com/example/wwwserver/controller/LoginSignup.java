package com.example.wwwserver.controller;
import com.example.wwwserver.entity.UserEntity;
import com.example.wwwserver.modal.signup.SiginupModal;
import com.example.wwwserver.modal.signup.SignupModalReturn;
import com.example.wwwserver.respositary.UserRepo;
import com.example.wwwserver.securtiy.JwtUtil;
import com.example.wwwserver.util.PasswordGenrator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Validated
public class LoginSignup {
    @Autowired
    UserRepo userRepo ;
    @PostMapping("/signup")
    public Object signup(@Valid  @RequestBody SiginupModal signupValue) {

        SignupModalReturn signupModalReturn = new SignupModalReturn();
        try{
            Map<String,Object> map = new ObjectMapper().convertValue(signupValue, Map.class);
            String tokenpassword = new PasswordGenrator().generatePassword(30);
            map.remove("password");

            String refreshtoken = JwtUtil.generateToken(map,tokenpassword);
            String accesstoken = JwtUtil.generateToken(map,tokenpassword);
            signupModalReturn.setAccesstoken(accesstoken);
            signupModalReturn.setRefreshtoken(refreshtoken);

            UserEntity userEntity = new UserEntity();
            userEntity.setImage(signupValue.getImage());
            userEntity.setName(signupValue.getName());
            userEntity.setPhone(signupValue.getPhone());
            userEntity.setEmail(signupValue.getEmail());
            userEntity.setPassword(signupValue.getPassword());
            userEntity.setRole(signupValue.getRole());
            userEntity.setTokenpass(tokenpassword);
            userEntity.setLat(signupValue.getLat());
            userEntity.setLongi(signupValue.getLongi());

            UserEntity value =  userRepo.save(userEntity);
            signupModalReturn.setUsername(value.getId().toString());
            return value;
//            return signupModalReturn;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return signupModalReturn;
    }

    @PostMapping("/login")
    public Map<String, Object> test(@RequestBody SignupModalReturn body) {
        Map<String, Object> map = new HashMap<>();
        String accesstoken = body.getAccesstoken();
        String refreshtoken = body.getRefreshtoken();
        String username = body.getUsername();
        Optional<UserEntity> uentitiy = userRepo.findById(UUID.fromString(username));
        if(uentitiy.isPresent()) {
            String tokenpassword = uentitiy.get().getTokenpass();
            boolean tokenvalidation = JwtUtil.validateToken(accesstoken,tokenpassword);
            return JwtUtil.extractObject(accesstoken,tokenpassword);
        }
        return map;
    }

    @GetMapping
    public String stringValueTest() {
        return "Home Routes";
    }
}

package com.example.wwwserver.controller;
import com.example.wwwserver.entity.UserEntity;
import com.example.wwwserver.modal.signup.SiginupModal;
import com.example.wwwserver.modal.signup.SignupModalReturn;
import com.example.wwwserver.respositary.UserRepo;
import com.example.wwwserver.securtiy.JwtUtil;
import com.example.wwwserver.util.PasswordGenrator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
            if(!userRepo.findByPhoneAndEmail(signupValue.getPhone(),signupValue.getEmail()).isEmpty()) {
                return "User alreay exist";
            }
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
            signupModalReturn.setId(value.getId().toString());
            return signupModalReturn;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return signupModalReturn;
    }
@PostMapping("/login")
public Object login(@RequestBody Map<String ,String> body){
        String username = body.get("username");
        String password = body.get("password");
        String types = body.get("type");
            if(types.equals("phone")){
                List<UserEntity> temp = userRepo.findByPhoneAndPassword(username,password);
                if(temp.isEmpty())return "user does not exist";
                Map<String,Object> userMap = new ObjectMapper().convertValue(temp.get(0),Map.class);
                userMap.remove("tokenpass");
                userMap.remove("password");
                Map<String,Object> tempMap = new HashMap<>(userMap);
                userMap.put("accesstoken",tempMap);
                userMap.put("refreshtoken",tempMap);
                return userMap;
            }
            else{
                List<UserEntity> temp = userRepo.findByEmailAndPassword(username,password);
                if(temp.isEmpty())return "user does not exist";
                Map<String,Object> userMap = new ObjectMapper().convertValue(temp.get(0),Map.class);
                userMap.remove("tokenpass");
                userMap.remove("password");
                Map<String,Object> tempMap = new HashMap<>(userMap);
                userMap.put("accesstoken",tempMap);
                userMap.put("refreshtoken",tempMap);
                return userMap;
            }




}
    @PostMapping("/logintoken")
    public Object logintoken(@RequestBody SignupModalReturn body) {
        Map<String, Object> map = new HashMap<>();
        String accesstoken = body.getAccesstoken();
        String refreshtoken = body.getRefreshtoken();
        String id = body.getId();
        String username = body.getUsername();
        String password = body.getPassword();
        String types = body.getType();
        if(types!=null){}
        else{
        Optional<UserEntity> uentitiy = userRepo.findById(UUID.fromString(id));
        if(uentitiy.isPresent()) {
            String tokenpassword = uentitiy.get().getTokenpass();
            boolean tokenvalidation = JwtUtil.validateToken(accesstoken, tokenpassword);
            Map<String, Object> nmap = JwtUtil.extractObject(accesstoken, tokenpassword);
            nmap.remove("tokenpass");
            return nmap;
        }
        }
        return null;
    }
    @PostMapping("/refreshtoken")
    public Object refreshtoken(@RequestBody SignupModalReturn body){
        Map<String, Object> map = new HashMap<>();
        String accesstoken = body.getAccesstoken();
        String refreshtoken = body.getRefreshtoken();
        String id = body.getId();
        Optional<UserEntity> uentitiy = userRepo.findById(UUID.fromString(id));
        if(uentitiy.isPresent()) {
            String tokenpassword = uentitiy.get().getTokenpass();
            boolean tokenvalidation = JwtUtil.validateToken(refreshtoken,tokenpassword);
            Map<String,Object> nmap =  JwtUtil.extractObject(refreshtoken,tokenpassword);
            nmap.remove("tokenpass");
            map.put("id",id);
            map.put("accesstoken",JwtUtil.generateToken(nmap,tokenpassword));
            map.put("refreshtoken",refreshtoken);
            return nmap;
        }
        return null;
    }
    @GetMapping
    public String stringValueTest() {
        return "Home Routes";
    }
}

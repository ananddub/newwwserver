package com.example.wwwserver.modal.signup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupModalReturn {
    private String id;
    private String username;
    private String password;
    private String type;
    private String accesstoken;
    private String refreshtoken;
}

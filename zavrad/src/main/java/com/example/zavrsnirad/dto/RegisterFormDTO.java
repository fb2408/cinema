package com.example.zavrsnirad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormDTO {
    private Integer userId;
    private String username;
    private String userFirstName;
    private String userLastname;
    private String adress;
    private Integer cityId;
    private String userMail;
    private String password;
    private boolean confirmed;
}

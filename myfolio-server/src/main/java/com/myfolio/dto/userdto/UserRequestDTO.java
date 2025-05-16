package com.myfolio.dto.userdto;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
    private String profileImage;
    private String bio;
}

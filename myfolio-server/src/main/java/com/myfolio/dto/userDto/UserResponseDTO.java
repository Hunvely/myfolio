package com.myfolio.dto.userDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String name;
    private String profileImage;
    private String bio;
    private LocalDateTime createdAt;
}

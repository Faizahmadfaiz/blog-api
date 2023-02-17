package com.scaler.blogapi.users.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    Integer id;
    String email;
    String username;
    String bio;
    String image;
}

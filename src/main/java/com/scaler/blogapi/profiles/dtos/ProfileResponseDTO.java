package com.scaler.blogapi.profiles.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDTO {
    Integer id;
    String email;
    String username;
    String bio;
    String image;
}

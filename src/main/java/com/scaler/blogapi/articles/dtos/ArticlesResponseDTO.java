package com.scaler.blogapi.articles.dtos;

import com.scaler.blogapi.users.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticlesResponseDTO {
    String slug;
    String title;
    String subTitle;
    String body;
    UserEntity author;
    List<UserEntity> likedBy;
}

package com.scaler.blogapi.articles.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateArticleDTO {
    String title;
    String subTitle;
    String body;
}

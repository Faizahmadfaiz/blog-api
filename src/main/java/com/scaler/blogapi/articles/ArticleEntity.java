package com.scaler.blogapi.articles;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity(name="articles")
public class ArticleEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 150)
    String slug;

    @Column(nullable = false, length = 200)
    String title;

    String subTitle;

    @Column(nullable = false, length = 8000)
    String body;

    // String tagList; TODO: Implement this

    @ManyToOne
    UserEntity author;

    @ManyToMany
            @JoinTable(
                    name = "article_likes",
                    joinColumns = @JoinColumn(name = "article_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id")
            )
    List<UserEntity> likedBy;
}

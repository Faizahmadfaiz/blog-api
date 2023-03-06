package com.scaler.blogapi.articles;

import com.scaler.blogapi.users.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ArticlesRepository extends JpaRepository<ArticleEntity, Integer> {
    ArticleEntity findBySlug(String slug);
    List<ArticleEntity> findAllByAuthor(UserEntity userEntity, Pageable pageable);
}

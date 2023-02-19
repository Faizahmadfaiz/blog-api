package com.scaler.blogapi.articles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticlesRepository extends JpaRepository<ArticleEntity, Integer> {
    ArticleEntity findBySlug(String slug);
}

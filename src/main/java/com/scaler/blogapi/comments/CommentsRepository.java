package com.scaler.blogapi.comments;

import com.scaler.blogapi.articles.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByArticle(ArticleEntity article);
}

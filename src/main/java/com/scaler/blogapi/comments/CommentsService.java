package com.scaler.blogapi.comments;

import com.scaler.blogapi.articles.ArticlesService;
import com.scaler.blogapi.users.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final ArticlesService articlesService;
    private final UsersService usersService;

    public CommentsService(
            CommentsRepository commentsRepository,
            ArticlesService articlesService,
            UsersService usersService
    ) {
        this.commentsRepository = commentsRepository;
        this.articlesService = articlesService;
        this.usersService = usersService;
    }

    public CommentEntity createComment(CommentEntity commentEntity, Integer userId, String articleSlug) {
        var article = articlesService.getArticleBySlug(articleSlug);
        var user = usersService.getUserById(userId);
        commentEntity.setAuthor(user);
        commentEntity.setArticle(article);
        var savedComment = commentsRepository.save(commentEntity);
        return savedComment;
    }


    public List<CommentEntity> getComments(String articleSlug) {
        var article = articlesService.getArticleBySlug(articleSlug);
        var articleComments = commentsRepository.findAllByArticle(article);
        return articleComments;
    }
}

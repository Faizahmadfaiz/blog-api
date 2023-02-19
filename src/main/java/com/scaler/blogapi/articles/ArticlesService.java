package com.scaler.blogapi.articles;

import com.scaler.blogapi.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class ArticlesService {
    final ArticlesRepository articlesRepository;
    final ModelMapper modelMapper;
    final UsersService usersService;

    public ArticlesService(ArticlesRepository articlesRepository, ModelMapper modelMapper, UsersService usersService) {
        this.articlesRepository = articlesRepository;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
    }

    public ArticleEntity createArticle(ArticleEntity articleEntity, Integer authorId) {
        var author = usersService.getUserById(authorId);
        articleEntity.setAuthor(author);
        var savedArticle = articlesRepository.save(articleEntity);
        return savedArticle;
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article = articlesRepository.findBySlug(slug);
        return article;
    }
}

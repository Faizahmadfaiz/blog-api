package com.scaler.blogapi.articles;

import com.scaler.blogapi.articles.dtos.ArticlesResponseDTO;
import com.scaler.blogapi.articles.dtos.CreateArticleDTO;
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

    public ArticlesResponseDTO createArticle(CreateArticleDTO createArticleDTO, Integer authorId) {
        var newArticleEntity = modelMapper.map(createArticleDTO, ArticleEntity.class);
        var author = usersService.getUserById(authorId);
        newArticleEntity.setAuthor(author);
        var slug = this.getArticleSlug(newArticleEntity.getTitle());
        newArticleEntity.setSlug(slug);
        var savedArticle = articlesRepository.save(newArticleEntity);
        var articleResponseDTO = modelMapper.map(savedArticle, ArticlesResponseDTO.class);
        return articleResponseDTO;
    }

    private String getArticleSlug(String title) {
        var splittedString = title.toLowerCase().split(" ");
        return String.join("-", splittedString);
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article = articlesRepository.findBySlug(slug);
        return article;
    }
}

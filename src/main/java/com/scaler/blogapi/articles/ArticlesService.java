package com.scaler.blogapi.articles;

import com.scaler.blogapi.articles.dtos.ArticlesResponseDTO;
import com.scaler.blogapi.articles.dtos.CreateArticleDTO;
import com.scaler.blogapi.users.UsersRepository;
import com.scaler.blogapi.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticlesService {
    final ArticlesRepository articlesRepository;
    final ModelMapper modelMapper;
    final UsersService usersService;
    final UsersRepository usersRepository;

    public ArticlesService(ArticlesRepository articlesRepository, ModelMapper modelMapper, UsersService usersService, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
        this.usersRepository = usersRepository;
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

    public List<ArticlesResponseDTO> getArticlesByAuthor(String username, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        var userEntity = usersRepository.findByUsername(username);
       var articles = articlesRepository.findAllByAuthor(userEntity, paging);
       return articles.stream().map(articleEntity -> modelMapper.map(articleEntity, ArticlesResponseDTO.class)).toList();
    }

    public List<ArticlesResponseDTO> getArticles(Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        var articles = articlesRepository.findAll(paging);
        return articles.stream().map(articleEntity -> modelMapper.map(articleEntity, ArticlesResponseDTO.class)).toList();
    }
}

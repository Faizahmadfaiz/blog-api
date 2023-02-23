package com.scaler.blogapi.articles;

import com.scaler.blogapi.users.UsersRepository;
import com.scaler.blogapi.users.UsersService;
import com.scaler.blogapi.users.dtos.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ArticlesServiceTests {
    @Autowired private ArticlesRepository articlesRepository;
    @Autowired private UsersRepository usersRepository;
    private ArticlesService articlesService;
    private UsersService usersService;

    private UsersService getUsersService() {
        if (usersService == null) {
            var modelMapper = new ModelMapper();
            var passwordEncoder = new BCryptPasswordEncoder();
            usersService =  new UsersService(usersRepository, modelMapper, passwordEncoder) ;
        }
        return usersService;
    }
    public ArticlesService getArticlesService() {
        if (articlesService == null) {
            var modelMapper = new ModelMapper();
            articlesService = new ArticlesService(articlesRepository, modelMapper, getUsersService());
        }
        return articlesService;
    }

    @Test
    public void testCreateArticle() {
        var articleEntity = new ArticleEntity();
        articleEntity.setSlug("abc");
        articleEntity.setTitle("Title");
        articleEntity.setBody("body");

        var newUserDTO = new CreateUserDTO();
        newUserDTO.setEmail("faiz.ahmad@email.com");
        newUserDTO.setPassword("password");
        newUserDTO.setUsername("faiz123");
        var savedUser = getUsersService().createUser(newUserDTO);
        var savedArticle = getArticlesService().createArticle(articleEntity, savedUser.getId());
        assertNotNull(savedArticle);
    }

    @Test
    public void testGetArticleBySlug() {
        var articleEntity = new ArticleEntity();
        articleEntity.setSlug("abc");
        articleEntity.setTitle("Title");
        articleEntity.setBody("body");

        var newUserDTO = new CreateUserDTO();
        newUserDTO.setEmail("faiz.ahmad@email.com");
        newUserDTO.setPassword("password");
        newUserDTO.setUsername("faiz123");
        var savedUser = getUsersService().createUser(newUserDTO);
        var savedArticle = getArticlesService().createArticle(articleEntity, savedUser.getId());

        var articleBySlug = getArticlesService().getArticleBySlug("abc");

        assertNotNull(articleBySlug);
        assertEquals(savedArticle.getSlug(), articleBySlug.getSlug());
    }
}

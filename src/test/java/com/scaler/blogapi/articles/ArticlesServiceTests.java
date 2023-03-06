package com.scaler.blogapi.articles;

import com.scaler.blogapi.articles.dtos.CreateArticleDTO;
import com.scaler.blogapi.security.authtokens.AuthTokenRepository;
import com.scaler.blogapi.security.authtokens.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
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
    @Autowired private AuthTokenRepository authTokenRepository;
    private ArticlesService articlesService;
    private UsersService usersService;

    private UsersService getUsersService() {
        if (usersService == null) {
            var modelMapper = new ModelMapper();
            var passwordEncoder = new BCryptPasswordEncoder();
            var jwtService = new JWTService();
            var authTokenService = new AuthTokenService(authTokenRepository);
            usersService =  new UsersService(usersRepository, modelMapper, passwordEncoder, jwtService, authTokenService ) ;
        }
        return usersService;
    }
    public ArticlesService getArticlesService() {
        if (articlesService == null) {
            var modelMapper = new ModelMapper();
            articlesService = new ArticlesService(articlesRepository, modelMapper, getUsersService(), usersRepository);
        }
        return articlesService;
    }

    @Test
    public void testCreateArticle() {
        var createArticleDTO = new CreateArticleDTO();
        createArticleDTO.setTitle("Title");
        createArticleDTO.setBody("body");

        var newUserDTO = new CreateUserDTO();
        newUserDTO.setEmail("faiz.ahmad@email.com");
        newUserDTO.setPassword("password");
        newUserDTO.setUsername("faiz123");
        var savedUser = getUsersService().createUser(newUserDTO);
        var savedArticle = getArticlesService().createArticle(createArticleDTO, savedUser.getId());
        assertNotNull(savedArticle);
    }

    @Test
    public void testGetArticleBySlug() {
        var createArticleDTO = new CreateArticleDTO();
        createArticleDTO.setTitle("My Title");
        createArticleDTO.setBody("body");

        var newUserDTO = new CreateUserDTO();
        newUserDTO.setEmail("faiz.ahmad@email.com");
        newUserDTO.setPassword("password");
        newUserDTO.setUsername("faiz123");
        var savedUser = getUsersService().createUser(newUserDTO);
        var savedArticle = getArticlesService().createArticle(createArticleDTO, savedUser.getId());

        var articleBySlug = getArticlesService().getArticleBySlug("my-title");

        assertNotNull(articleBySlug);
        assertEquals(savedArticle.getSlug(), articleBySlug.getSlug());
    }
}

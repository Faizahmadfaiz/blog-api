

package com.scaler.blogapi.articles;

import com.scaler.blogapi.articles.dtos.ArticlesResponseDTO;
import com.scaler.blogapi.articles.dtos.CreateArticleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private ArticlesService articlesService;

    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping("")
    public String getArticles() {
        return "Articles";
    }

    @GetMapping("/private")
    public String getPrivateArticles(@AuthenticationPrincipal Integer userId) {
        return "Private Articles fetched for = " + userId;
    }

    @PostMapping("")
    public ResponseEntity<ArticlesResponseDTO> createArticle(
            @AuthenticationPrincipal Integer authorId,
            @RequestBody CreateArticleDTO createArticleDTO
    ) {
        var savedArticle = this.articlesService.createArticle(createArticleDTO, authorId);
        return ResponseEntity.created(URI.create("/articles/" + savedArticle.getSlug()))
                .body(savedArticle);

    }
}
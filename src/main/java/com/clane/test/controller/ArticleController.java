package com.clane.test.controller;

import com.clane.test.dto.request.ArticleRequest;
import com.clane.test.dto.response.ArticleResponse;
import com.clane.test.service.ArticleService;
import com.clane.test.util.MessageUtil;
import com.clane.test.util.RequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleResponse> getArticles() {
        RequestUtil.getLoggedInUser();
        return articleService.getArticles();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ArticleResponse submitArticle(@Valid @RequestBody ArticleRequest request) {
        return articleService.submitArticle(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ArticleResponse editArticle(@PathVariable("id") Long articleId, @Valid @RequestBody ArticleRequest request) {
        return articleService.editArticle(request, articleId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteArticle(articleId);
        return new ResponseEntity<>(MessageUtil.msg("article_delete_success"), HttpStatus.OK);
    }
}
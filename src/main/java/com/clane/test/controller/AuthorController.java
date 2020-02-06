package com.clane.test.controller;

import com.clane.test.dto.response.ArticleResponse;
import com.clane.test.dto.response.AuthorResponse;
import com.clane.test.service.ArticleService;
import com.clane.test.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ArticleService articleService;

    public AuthorController(AuthorService authorService, ArticleService articleService) {
        this.authorService = authorService;
        this.articleService = articleService;
    }

    @GetMapping
    public List<AuthorResponse> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping("/articles")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public List<ArticleResponse> getAuthorArticles() {
        return articleService.getAuthorArticles();
    }
}
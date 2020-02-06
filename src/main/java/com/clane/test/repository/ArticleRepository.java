package com.clane.test.repository;

import com.clane.test.dto.response.ArticleResponse;
import com.clane.test.model.Article;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select new com.clane.test.dto.response.ArticleResponse(a.title, a.content, a.author.id, a.id, a.createdAt, a.updatedAt) from Article a where a.author.id = ?1")
    List<ArticleResponse> findAuthorArticles(Long authorId);
}
package com.clane.test.service;

import com.clane.test.dto.request.ArticleRequest;
import com.clane.test.dto.response.ArticleResponse;
import com.clane.test.exception.AppException;
import com.clane.test.model.Article;
import com.clane.test.repository.ArticleRepository;
import com.clane.test.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final AuthorService authorService;

    private final ModelMapper modelMapper;

    public ArticleService(ArticleRepository articleRepository,
                          AuthorService authorService,
                          ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    /**
     * <p>Note: This method returns all articles</p>
     *
     * @return List of article
     */
    public List<ArticleResponse> getArticles() {
        return articleRepository.findAll()
                .stream()
                .map(article -> new ArticleResponse(
                        article.getTitle(),
                        article.getContent(),
                        article.getAuthor().getId(),
                        article.getId(),
                        article.getCreatedAt(),
                        article.getUpdatedAt()
                        )
                )
                .collect(Collectors.toList());
    }

    /**
     * <p>Note: This method convert ArticleRequest to Article before persisting to storage</p>
     *
     * @param request article object to be created
     * @return newly created article
     */
    public ArticleResponse submitArticle(ArticleRequest request) {
        Article article = modelMapper.map(request, Article.class);
        Long authorId = RequestUtil.getLoggedInUser();
        article.setAuthor(authorService.getAuthor(authorId));
        article = articleRepository.save(article);
        return ArticleResponse.builder()
                .id(article.getId())
                .authorId(article.getAuthor().getId())
                .content(article.getContent())
                .title(article.getTitle())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    /**
     * <p>Note: This method allows an article to be edited only by its author</p>
     *
     * @param request to be edited
     * @param articleId identifier of article to be edited
     * @return edited article
     */
    public ArticleResponse editArticle(ArticleRequest request, Long articleId) {
        Article article = getArticle(articleId);

        Long loggedInUser = RequestUtil.getLoggedInUser();
        Long articleOwner = article.getAuthor().getId();

        if (!loggedInUser.equals(articleOwner)) {
            throw new AppException("article_edit_not_allowed", HttpStatus.FORBIDDEN);
        }

        modelMapper.map(request, article);
        ArticleResponse response = modelMapper.map(articleRepository.save(article), ArticleResponse.class);
        response.setAuthorId(article.getAuthor().getId());

        return response;
    }

    /**
     * <p>Note: Note: This method allows an article to be deleted only by its author </p>
     *
     * @param articleId identifier of article to be edited
     */
    public void deleteArticle(Long articleId) {
        Article article = getArticle(articleId);

        Long loggedInUser = RequestUtil.getLoggedInUser();
        Long articleOwner = article.getAuthor().getId();

        if (!loggedInUser.equals(articleOwner)) {
            throw new AppException("article_delete_not_allowed", HttpStatus.FORBIDDEN);
        }

        articleRepository.delete(article);
    }

    /**
     * <p>Note: This method retrieve an article, if article does not exist then throws an AppException</p>
     *
     * This method is only accessible within this class
     *
     * @param articleId identifier of article to be retrieved
     * @return article object
     */
    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(()
                -> new AppException("article_not_found", String.valueOf(articleId), HttpStatus.NOT_FOUND));
    }
}

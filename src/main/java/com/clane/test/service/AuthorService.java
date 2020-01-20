package com.clane.test.service;

import com.clane.test.dto.request.SignUpRequest;
import com.clane.test.dto.response.AuthorResponse;
import com.clane.test.exception.AppException;
import com.clane.test.model.Author;
import com.clane.test.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository,
                         PasswordEncoder passwordEncoder,
                         ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public List<AuthorResponse> getAuthors() {
        List<AuthorResponse> authors = new ArrayList<>();
        authorRepository.findAll().forEach((Author author) -> authors.add(
                        AuthorResponse.builder()
                                .name(author.getName())
                                .email(author.getEmail())
                                .bio(author.getBio())
                                .id(author.getId())
                                .createdAt(author.getCreatedAt())
                                .updatedAt(author.getUpdatedAt())
                                .build()
                                ));
        return authors;
    }

    Author getAuthor(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(()
                -> new AppException("author_not_found", String.valueOf(authorId), HttpStatus.NOT_FOUND));
    }

    public AuthorResponse saveAuthor(SignUpRequest request) {
        Author author = modelMapper.map(request, Author.class);
        author.setPassword(passwordEncoder.encode(request.getPassword()));
        author = authorRepository.save(author);
        return modelMapper.map(author, AuthorResponse.class);
    }

    public Author getAuthorByUsername(String username) {
        return authorRepository.getAuthorByEmail(username).orElse(null);
    }
}
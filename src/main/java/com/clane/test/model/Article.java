package com.clane.test.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "articles")
@ToString
public class Article extends BaseModel {

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String content;

    @NotNull
    @ManyToOne
    @JsonManagedReference
    private Author author;
}

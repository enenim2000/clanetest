package com.clane.test.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "authors")
@ToString
public class Author extends BaseModel {

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(unique = true, length = 100)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String bio;
}

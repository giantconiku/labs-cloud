package com.giant.springbootrestapipostgresql.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birth_year")
    private int birthYear;

    @Column(name="biography")
    private String biography;

    @Column(name="email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "isbn", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "value")
    private List<String> isbns;
}

package com.giant.springbootrestapipostgresql.repository;

import com.giant.springbootrestapipostgresql.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByFirstName(String firstName);
}
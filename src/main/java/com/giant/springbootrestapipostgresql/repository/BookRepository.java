package com.giant.springbootrestapipostgresql.repository;

import com.giant.springbootrestapipostgresql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
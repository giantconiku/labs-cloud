package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Author> add(@RequestBody Author author) {

        Author addedAuthor = authorService.add(author);
        return new ResponseEntity<>(addedAuthor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAll() {

        Collection<Author> authors = authorService.getAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Long id) {

        Author author = authorService.getById(id);

        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"firstName"})
    public ResponseEntity<Collection<Author>> getByFirstName(@RequestParam(value = "firstName") String firstName) {

        Collection<Author> authors = authorService.getByFirstName(firstName);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateById(@PathVariable("id") long id, @RequestBody Author author) {

        Author updatedAuthor = authorService.updateById(id, author);

        if (updatedAuthor != null) {
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {

        authorService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

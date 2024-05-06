package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.service.AuthorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private static final Logger logger = LogManager.getLogger(AuthorRestController.class);

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Author> add(@RequestBody Author author) {

        logger.info("Author Controller: Adding author: {}", author);
        Author addedAuthor = authorService.add(author);
        logger.info("Author Controller: Author added successfully");
        logger.debug("Author Controller: Added author: {}", addedAuthor);
        logger.info("Author Controller: I am a Post Request and I am done");
        return new ResponseEntity<>(addedAuthor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAll() {

        logger.info("Author Controller: Retrieving all authors");
        Collection<Author> authors = authorService.getAll();
        logger.debug("Author Controller: Retrieved authors: {}", authors);
        logger.info("Author Controller: I am a Get Request and I am done");
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Long id) {

        logger.info("Author Controller: Retrieving author with ID: {}", id);
        Author author = authorService.getById(id);

        if (author != null) {
            logger.debug("Author Controller: Retrieved author: {}", author);
            logger.info("Author Controller: I am a Get Request and I am done");
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            logger.debug("Author Controller: Author with ID {} does not exist", id);
            logger.info("Author Controller: I am a Get Request and I am done");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"firstName"})
    public ResponseEntity<Collection<Author>> getByFirstName(@RequestParam(value = "firstName") String firstName) {

        logger.info("Author Controller: Finding author with first name: {}", firstName);
        Collection<Author> authors = authorService.getByFirstName(firstName);
        logger.debug("Author Controller: Found authors: {}", authors);
        logger.info("Author Controller: I am a Get Request and I am done");
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateById(@PathVariable("id") long id, @RequestBody Author author) {

        logger.info("Author Controller: Updating author with ID: {}", id);
        Author updatedAuthor = authorService.updateById(id, author);

        if (updatedAuthor != null) {
            logger.debug("Author Controller: Updated author: {}", updatedAuthor);
            logger.info("Author Controller: I am a Put Request and I am done");
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            logger.error("Author Controller: Author with ID {} not found", id);
            logger.info("Author Controller: I am a Put Request and I am done");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        logger.info("Author Controller: Deleting author with ID: {}", id);
        authorService.deleteById(id);
        logger.debug("Author Controller: Author with ID {} deleted successfully", id);
        logger.info("Author Controller: I am a Delete Request and I am done");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {

        logger.info("Author Controller: Deleting all authors");
        authorService.deleteAll();
        logger.debug("Author Controller: All authors deleted successfully");
        logger.info("Author Controller: I am a Delete Request and I am done");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
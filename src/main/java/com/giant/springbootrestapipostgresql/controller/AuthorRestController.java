package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorRepository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(params = {"firstName"})
    public ResponseEntity<Collection<Author>> findAuthorsByFirstName(@RequestParam(value = "firstName") String firstName) {
        return new ResponseEntity<>(authorRepository.findByFirstName(firstName), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {

        Optional<Author> currentAuthorOpt = authorRepository.findById(id);

        if (currentAuthorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Author currentAuthor = currentAuthorOpt.get();
        currentAuthor.setFirstName(author.getFirstName());
        currentAuthor.setLastName(author.getLastName());
        currentAuthor.setBirthYear(author.getBirthYear());
        currentAuthor.setBiography(author.getBiography());
        currentAuthor.setEmail(author.getEmail());
        currentAuthor.setIsbns(author.getIsbns());

        return new ResponseEntity<>(authorRepository.save(currentAuthor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }
}

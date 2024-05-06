package com.giant.springbootrestapipostgresql.service;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.repository.AuthorRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuthorService {

    private static final Logger logger = LogManager.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author add(Author author) {

        logger.info("Author Service: Adding author: {}", author);
        return authorRepository.save(author);
    }

    public Collection<Author> getAll() {

        logger.info("Author Service: Retrieving all authors");
        return authorRepository.findAll();
    }

    public Author getById(Long id) {

        logger.info("Author Service: Retrieving author with ID: {}", id);
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    public Collection<Author> getByFirstName(String firstName) {

        logger.info("Author Service: Finding author with first name: {}", firstName);
        return authorRepository.findByFirstName(firstName);
    }

    public Author updateById(Long id, Author author) {

        logger.info("Author Service: Getting author with ID: {}", id);
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {

            Author currentAuthor = optionalAuthor.get();
            currentAuthor.setFirstName(author.getFirstName());
            currentAuthor.setLastName(author.getLastName());
            currentAuthor.setBirthYear(author.getBirthYear());
            currentAuthor.setBiography(author.getBiography());
            currentAuthor.setEmail(author.getEmail());
            currentAuthor.setIsbns(author.getIsbns());

            logger.debug("Author Service: Saving updated author with ID: {} to database", id);
            return authorRepository.save(currentAuthor);

        } else {
            return null;
        }
    }

    public void deleteById(Long id) {

        logger.info("Author Service: Deleting author with ID: {}", id);
        authorRepository.deleteById(id);
    }

    public void deleteAll() {

        logger.info("Author Service: Deleting all authors");
        authorRepository.deleteAll();
    }
}

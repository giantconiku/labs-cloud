package com.giant.springbootrestapipostgresql.service;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author add(Author author) {
        return authorRepository.save(author);
    }

    public Collection<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {

        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    public Collection<Author> getByFirstName(String firstName) {
        return authorRepository.findByFirstName(firstName);
    }

    public Author updateById(Long id, Author author) {

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {

            Author currentAuthor = optionalAuthor.get();
            currentAuthor.setFirstName(author.getFirstName());
            currentAuthor.setLastName(author.getLastName());
            currentAuthor.setBirthYear(author.getBirthYear());
            currentAuthor.setBiography(author.getBiography());
            currentAuthor.setEmail(author.getEmail());
            currentAuthor.setIsbns(author.getIsbns());

            return authorRepository.save(currentAuthor);

        } else {
            return null;
        }
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public void deleteAll() {
        authorRepository.deleteAll();
    }
}

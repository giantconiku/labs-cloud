package com.giant.springbootrestapipostgresql.service;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book add(Book book) {
        return bookRepository.save(book);
    }

    public Collection<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {

        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Collection<Book> getByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book updateById(Long id, Book book) {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {

            Book currentBook = optionalBook.get();
            currentBook.setTitle(book.getTitle());
            currentBook.setDescription(book.getDescription());
            currentBook.setTags(book.getTags());

            return bookRepository.save(currentBook);

        } else {
            return null;
        }
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }
}

package com.giant.springbootrestapipostgresql.service;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.repository.BookRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book add(Book book) {

        logger.info("Book Service: Adding book: {}", book);
        return bookRepository.save(book);
    }

    public Collection<Book> getAll() {

        logger.info("Book Service: Retrieving all books");
        return bookRepository.findAll();
    }

    public Book getById(Long id) {

        logger.info("Book Service: Retrieving book with ID: {}", id);
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Collection<Book> getByTitle(String title) {

        logger.info("Book Service: Finding books with title: {}", title);
        return bookRepository.findByTitle(title);
    }

    public Book updateById(Long id, Book book) {

        logger.info("Book Service: Getting book with ID: {}", id);
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {

            Book currentBook = optionalBook.get();
            currentBook.setTitle(book.getTitle());
            currentBook.setDescription(book.getDescription());
            currentBook.setTags(book.getTags());

            logger.debug("Book Service: Saving updated book with ID: {} to database", id);
            return bookRepository.save(currentBook);

        } else {
            return null;
        }
    }

    public void deleteById(Long id) {

        logger.info("Book Service: Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
    }

    public void deleteAll() {

        logger.info("Book Service: Deleting all books");
        bookRepository.deleteAll();
    }
}

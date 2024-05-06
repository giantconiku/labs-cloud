package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.service.BookService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private static final Logger logger = LogManager.getLogger(BookRestController.class);

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book book) {

        logger.info("Book Controller: Adding book: {}", book);
        Book addedBook = bookService.add(book);
        logger.info("Book Controller: Book added successfully");
        logger.debug("Book Controller: Added book: {}", addedBook);
        logger.info("Book Controller: I am a Post Request and I am done");
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAll() {

        logger.info("Book Controller: Retrieving all books");
        Collection<Book> books = bookService.getAll();
        logger.debug("Book Controller: Retrieved books: {}", books);
        logger.info("Book Controller: I am a Get Request and I am done");
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {

        logger.info("Book Controller: Retrieving book with ID: {}", id);
        Book book = bookService.getById(id);

        if (book != null) {
            logger.debug("Book Controller: Retrieved book: {}", book);
            logger.info("Book Controller: I am a Get Request and I am done");
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            logger.debug("Book Controller: Book with ID {} does not exist", id);
            logger.info("Book Controller: I am a Get Request and I am done");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"title"})
    public ResponseEntity<Collection<Book>> getByTitle(@RequestParam(value = "title") String title) {

        logger.info("Book Controller: Finding books with title: {}", title);
        Collection<Book> books = bookService.getByTitle(title);
        logger.debug("Book Controller: Found books: {}", books);
        logger.info("Book Controller: I am a Get Request and I am done");
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateById(@PathVariable("id") long id, @RequestBody Book book) {

        logger.info("Book Controller: Updating book with ID: {}", id);
        Book updatedBook = bookService.updateById(id, book);

        if (updatedBook != null) {
            logger.debug("Book Controller: Updated book: {}", updatedBook);
            logger.info("Book Controller: I am a Put Request and I am done");
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            logger.error("Book Controller: Book with ID {} not found", id);
            logger.info("Book Controller: I am a Put Request and I am done");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        logger.info("Book Controller: Deleting book with ID: {}", id);
        bookService.deleteById(id);
        logger.debug("Book Controller: Book with ID {} deleted successfully", id);
        logger.info("Book Controller: I am a Delete Request and I am done");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {

        logger.info("Book Controller: Deleting all books");
        bookService.deleteAll();
        logger.debug("Book Controller: All books deleted successfully");
        logger.info("Book Controller: I am a Delete Request and I am done");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book book) {

        Book addedBook = bookService.add(book);
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAll() {

        Collection<Book> books = bookService.getAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {

        Book book = bookService.getById(id);

        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"title"})
    public ResponseEntity<Collection<Book>> getByTitle(@RequestParam(value = "title") String title) {

        Collection<Book> books = bookService.getByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateById(@PathVariable("id") long id, @RequestBody Book book) {

        Book updatedBook = bookService.updateById(id, book);

        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {

        bookService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
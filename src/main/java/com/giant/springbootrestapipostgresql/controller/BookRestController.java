package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookRepository bookRepository;

    @Autowired
    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return new ResponseEntity<Book>(bookRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(params = {"title"})
    public ResponseEntity<Collection<Book>> findBookByTitle(@RequestParam(value = "title") String title) {
        return new ResponseEntity<>(bookRepository.findByTitle(title), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {

        Optional<Book> currentBookOpt = bookRepository.findById(id);

        if (currentBookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book currentBook = currentBookOpt.get();
        currentBook.setName(book.getTitle());
        currentBook.setDescription(book.getDescription());
        currentBook.setTags(book.getTags());

        return new ResponseEntity<>(bookRepository.save(currentBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }
}
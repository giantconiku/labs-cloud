package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.service.BookService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookRestControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRestController underTestBookRestController;

    @Test
    public void canAddBook() {

        // Given
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Stranger");
        book.setDescription("Alber Camus 1942 novella");
        book.setTags(Arrays.asList("Sun", "Gun"));

        when(bookService.add(book)).thenReturn(book);

        // When
        ResponseEntity<Book> responseEntity = underTestBookRestController.add(book);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(book);

        // Verify service method was called
        verify(bookService, times(1)).add(book);
    }

    @Test
    public void canGetAllBooks() {

        // Given
        Book firstBook = new Book();
        firstBook.setId(1L);
        firstBook.setTitle("The Stranger");
        firstBook.setDescription("Alber Camus 1942 novella");
        firstBook.setTags(Arrays.asList("Sun", "Gun"));

        Book secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setTitle("The Metamorphosis");
        secondBook.setDescription("Franz Kafka's 1915 novella");
        secondBook.setTags(Arrays.asList("Salesman", "Big", "Insect"));

        when(bookService.getAll()).thenReturn(Arrays.asList(firstBook, secondBook));

        // When
        ResponseEntity<Collection<Book>> responseEntity = underTestBookRestController.getAll();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());

        // Verify service method was called
        verify(bookService, times(1)).getAll();
    }

    @Test
    public void canGetBookByGivenIdWhenBookExists() {

        // Given
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("The Stranger");
        book.setDescription("Alber Camus 1942 novella");
        book.setTags(Arrays.asList("Sun", "Gun"));

        when(bookService.getById(bookId)).thenReturn(book);

        // When
        ResponseEntity<Book> responseEntity = underTestBookRestController.getById(bookId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(book);

        verify(bookService, times(1)).getById(bookId);
    }

    @Test
    public void cantGetBookByGivenIdWhenBookDoesNotExist() {

        // Given
        Long bookId = 1L;

        when(bookService.getById(bookId)).thenReturn(null);

        // When
        ResponseEntity<Book> responseEntity = underTestBookRestController.getById(bookId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(bookService, times(1)).getById(bookId);
    }

    @Test
    public void canGetBooksByGivenTitle() {

        // Given
        String title = "The Stranger";

        Book firstBook = new Book();
        firstBook.setId(1L);
        firstBook.setTitle(title);
        firstBook.setDescription("Alber Camus 1942 novella");
        firstBook.setTags(Arrays.asList("Sun", "Gun"));

        Book secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setTitle(title);
        secondBook.setDescription("Another book with the same title");
        secondBook.setTags(Arrays.asList("Tag1", "Tag2"));

        Collection<Book> books = Arrays.asList(firstBook, secondBook);

        when(bookService.getByTitle(title)).thenReturn(books);

        // When
        ResponseEntity<Collection<Book>> responseEntity = underTestBookRestController.getByTitle(title);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(books);

        verify(bookService, times(1)).getByTitle(title);
    }

    @Test
    public void canUpdateBookByGivenIdWhenBookExists() {

        // Given
        long bookToBeUpdatedId = 1L;

        Book updatedBook = new Book();
        updatedBook.setId(bookToBeUpdatedId);
        updatedBook.setTitle("The known");
        updatedBook.setDescription("My 2024 novella");
        updatedBook.setTags(Arrays.asList("Moon", "Arm"));

        when(bookService.updateById(bookToBeUpdatedId, updatedBook)).thenReturn(updatedBook);

        // When
        ResponseEntity<Book> responseEntity = underTestBookRestController.updateById(bookToBeUpdatedId, updatedBook);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(updatedBook);

        verify(bookService, times(1)).updateById(bookToBeUpdatedId, updatedBook);
    }

    @Test
    public void cantUpdateBookByGivenIdWhenBookDoesNotExist() {

        // Given
        long bookToBeUpdatedId = 1L;

        Book updatedBook = new Book();
        updatedBook.setId(bookToBeUpdatedId);
        updatedBook.setTitle("The known");
        updatedBook.setDescription("My 2024 novella");
        updatedBook.setTags(Arrays.asList("Moon", "Arm"));

        when(bookService.updateById(bookToBeUpdatedId, updatedBook)).thenReturn(null);

        // When
        ResponseEntity<Book> responseEntity = underTestBookRestController.updateById(bookToBeUpdatedId, updatedBook);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(bookService, times(1)).updateById(bookToBeUpdatedId, updatedBook);
    }

    @Test
    public void canDeleteBookByGivenId() {

        // Given
        Long bookId = 1L;

        // When
        ResponseEntity<Void> responseEntity = underTestBookRestController.deleteById(bookId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(bookService, times(1)).deleteById(bookId);
    }

    @Test
    public void canDeleteAllBooks() {

        // When
        ResponseEntity<Void> responseEntity = underTestBookRestController.deleteAll();

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(bookService, times(1)).deleteAll();
    }
}
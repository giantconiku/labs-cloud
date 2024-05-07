package com.giant.springbootrestapipostgresql.service;

import com.giant.springbootrestapipostgresql.entity.Book;
import com.giant.springbootrestapipostgresql.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService underTestBookService;

    @Test
    public void canAddBook() {

        // Given
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Stranger");
        book.setDescription("Alber Camus 1942 novella");
        book.setTags(Arrays.asList("Sun", "Gun"));

        // When
        underTestBookService.add(book);

        // Then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    public void canGetAllBooks() {

        // When
        underTestBookService.getAll();

        // Then
        verify(bookRepository).findAll();
    }

    @Test
    public void canGetBookByGivenId() {

        // Given
        long bookId = 1L;

        // When
        underTestBookService.getById(bookId);

        // Then
        ArgumentCaptor<Long> bookIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(bookRepository).findById(bookIdArgumentCaptor.capture());
        Long capturedBookId = bookIdArgumentCaptor.getValue();

        assertThat(capturedBookId).isEqualTo(bookId);
    }

    @Test
    public void canGetBooksByGivenTitle() {

        // Given
        String booksTitle = "The Stranger";

        // When
        underTestBookService.getByTitle(booksTitle);

        // Then
        ArgumentCaptor<String> booksTitleArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(bookRepository).findByTitle(booksTitleArgumentCaptor.capture());
        String capturedBookTitle = booksTitleArgumentCaptor.getValue();

        assertThat(capturedBookTitle).isEqualTo(booksTitle);
    }

    @Test
    public void canUpdateBookByGivenIdWhenBookExists() {

        // Given
        Long bookToBeUpdatedId = 1L;

        Book existingBook = new Book();
        existingBook.setId(bookToBeUpdatedId);
        existingBook.setTitle("The Stranger");
        existingBook.setDescription("Alber Camus 1942 novella");
        existingBook.setTags(Arrays.asList("Sun", "Gun"));

        Book updatedBook = new Book();
        updatedBook.setId(bookToBeUpdatedId);
        updatedBook.setTitle("The known");
        updatedBook.setDescription("My 2024 novella");
        updatedBook.setTags(Arrays.asList("Moon", "Arm"));

        when(bookRepository.findById(bookToBeUpdatedId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // When
        Book returnedBook = underTestBookService.updateById(bookToBeUpdatedId, updatedBook);

        // Then
        assertThat(returnedBook).isNotNull();
        assertThat(returnedBook).isEqualTo(updatedBook);
        verify(bookRepository, times(1)).findById(bookToBeUpdatedId);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    public void cantUpdateBookByGivenIdWhenBookDoesNotExist() {

        // Given
        Long bookToBeUpdatedId = 1L;

        Book updatedBook = new Book();
        updatedBook.setId(bookToBeUpdatedId);
        updatedBook.setTitle("The known");
        updatedBook.setDescription("My 2024 novella");
        updatedBook.setTags(Arrays.asList("Moon", "Arm"));

        when(bookRepository.findById(bookToBeUpdatedId)).thenReturn(Optional.empty());

        // When
        Book returnedBook = underTestBookService.updateById(bookToBeUpdatedId, updatedBook);

        // Then
        assertThat(returnedBook).isNull();
        verify(bookRepository, times(1)).findById(bookToBeUpdatedId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void canDeleteBookByGivenId() {

        // Given
        long bookId = 1L;

        // When
        underTestBookService.deleteById(bookId);

        // Then
        ArgumentCaptor<Long> bookIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(bookRepository).deleteById(bookIdArgumentCaptor.capture());
        Long capturedBookId = bookIdArgumentCaptor.getValue();

        assertThat(capturedBookId).isEqualTo(bookId);
    }

    @Test
    public void canDeleteAllBooks() {

        // When
        underTestBookService.deleteAll();

        // Then
        verify(bookRepository).deleteAll();
    }
}

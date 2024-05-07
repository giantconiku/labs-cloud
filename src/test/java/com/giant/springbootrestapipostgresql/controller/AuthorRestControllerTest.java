package com.giant.springbootrestapipostgresql.controller;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.service.AuthorService;

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
public class AuthorRestControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorRestController underTestAuthorRestController;

    @Test
    public void canAddAuthor() {

        // Given
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Albert");
        author.setLastName("Camus");
        author.setBirthYear(1913);
        author.setBiography("French author");
        author.setEmail("albertcamus@email.com");
        author.setIsbns(Arrays.asList("ISBN 0-061-96436-0",
                                      "ISBN 0-068-91426-1"));

        when(authorService.add(author)).thenReturn(author);

        // When
        ResponseEntity<Author> responseEntity = underTestAuthorRestController.add(author);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(author);

        // Verify service method was called
        verify(authorService, times(1)).add(author);
    }

    @Test
    public void canGetAllAuthors() {

        // Given
        Author firstAuthor = new Author();
        firstAuthor.setId(1L);
        firstAuthor.setFirstName("Albert");
        firstAuthor.setLastName("Camus");
        firstAuthor.setBirthYear(1913);
        firstAuthor.setBiography("French author");
        firstAuthor.setEmail("albertcamus@email.com");
        firstAuthor.setIsbns(Arrays.asList("ISBN 0-061-96436-0",
                                           "ISBN 0-068-91426-1"));

        Author secondAuthor = new Author();
        secondAuthor.setId(2L);
        secondAuthor.setFirstName("Franz");
        secondAuthor.setLastName("Kafka");
        secondAuthor.setBirthYear(1883);
        secondAuthor.setBiography("Jewish novelist from Prague");
        secondAuthor.setEmail("franzkafka@email.com");
        secondAuthor.setIsbns(Arrays.asList("ISBN 0-061-96136-0",
                                            "ISBN 0-068-71422-1"));

        when(authorService.getAll()).thenReturn(Arrays.asList(firstAuthor, secondAuthor));

        // When
        ResponseEntity<Collection<Author>> responseEntity = underTestAuthorRestController.getAll();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());

        // Verify service method was called
        verify(authorService, times(1)).getAll();
    }

    @Test
    public void canGetAuthorByGivenIdWhenAuthorExists() {

        // Given
        Long authorId = 1L;

        Author author = new Author();
        author.setId(authorId);
        author.setFirstName("Albert");
        author.setLastName("Camus");
        author.setBirthYear(1913);
        author.setBiography("French author");
        author.setEmail("albertcamus@email.com");
        author.setIsbns(Arrays.asList("ISBN 0-061-96436-0",
                                      "ISBN 0-068-91426-1"));

        when(authorService.getById(authorId)).thenReturn(author);

        // When
        ResponseEntity<Author> responseEntity = underTestAuthorRestController.getById(authorId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(author);

        verify(authorService, times(1)).getById(authorId);
    }

    @Test
    public void cantGetAuthorByGivenIdWhenAuthorDoesNotExist() {

        // Given
        Long authorId = 1L;

        when(authorService.getById(authorId)).thenReturn(null);

        // When
        ResponseEntity<Author> responseEntity = underTestAuthorRestController.getById(authorId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(authorService, times(1)).getById(authorId);
    }

    @Test
    public void canGetAuthorsByGivenFirstName() {

        // Given
        String authorsFirstName = "Albert";

        Author firstAuthor = new Author();
        firstAuthor.setId(1L);
        firstAuthor.setFirstName("Albert");
        firstAuthor.setLastName("Camus");
        firstAuthor.setBirthYear(1913);
        firstAuthor.setBiography("French author");
        firstAuthor.setEmail("albertcamus@email.com");
        firstAuthor.setIsbns(Arrays.asList("ISBN 0-061-96436-0",
                                           "ISBN 0-068-91426-1"));

        Author secondAuthor = new Author();
        secondAuthor.setId(2L);
        secondAuthor.setFirstName("Franz");
        secondAuthor.setLastName("Kafka");
        secondAuthor.setBirthYear(1883);
        secondAuthor.setBiography("Jewish novelist from Prague");
        secondAuthor.setEmail("franzkafka@email.com");
        secondAuthor.setIsbns(Arrays.asList("ISBN 0-061-96136-0",
                                            "ISBN 0-068-71422-1"));

        Collection<Author> authors = Arrays.asList(firstAuthor, secondAuthor);

        when(authorService.getByFirstName(authorsFirstName)).thenReturn(authors);

        // When
        ResponseEntity<Collection<Author>> responseEntity = underTestAuthorRestController.getByFirstName(authorsFirstName);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(authors);

        verify(authorService, times(1)).getByFirstName(authorsFirstName);
    }

    @Test
    public void canUpdateAuthorByGivenIdWhenAuthorExists() {

        // Given
        long authorToBeUpdatedId = 1L;

        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorToBeUpdatedId);
        updatedAuthor.setFirstName("Franz");
        updatedAuthor.setLastName("Kafka");
        updatedAuthor.setBirthYear(1883);
        updatedAuthor.setBiography("Jewish novelist from Prague");
        updatedAuthor.setEmail("franzkafka@email.com");
        updatedAuthor.setIsbns(Arrays.asList("ISBN 0-061-96136-0",
                                             "ISBN 0-068-71422-1"));

        when(authorService.updateById(authorToBeUpdatedId, updatedAuthor)).thenReturn(updatedAuthor);

        // When
        ResponseEntity<Author> responseEntity = underTestAuthorRestController.updateById(authorToBeUpdatedId, updatedAuthor);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(updatedAuthor);

        verify(authorService, times(1)).updateById(authorToBeUpdatedId, updatedAuthor);
    }

    @Test
    public void cantUpdateAuthorByGivenIdWhenAuthorDoesNotExist() {

        // Given
        long authorToBeUpdatedId = 1L;

        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorToBeUpdatedId);
        updatedAuthor.setFirstName("Franz");
        updatedAuthor.setLastName("Kafka");
        updatedAuthor.setBirthYear(1883);
        updatedAuthor.setBiography("Jewish novelist from Prague");
        updatedAuthor.setEmail("franzkafka@email.com");
        updatedAuthor.setIsbns(Arrays.asList("ISBN 0-061-96136-0",
                                             "ISBN 0-068-71422-1"));

        when(authorService.updateById(authorToBeUpdatedId, updatedAuthor)).thenReturn(null);

        // When
        ResponseEntity<Author> responseEntity = underTestAuthorRestController.updateById(authorToBeUpdatedId, updatedAuthor);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(authorService, times(1)).updateById(authorToBeUpdatedId, updatedAuthor);
    }

    @Test
    public void canDeleteAuthorByGivenId() {

        // Given
        Long authorId = 1L;

        // When
        ResponseEntity<Void> responseEntity = underTestAuthorRestController.deleteById(authorId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authorService, times(1)).deleteById(authorId);
    }

    @Test
    public void canDeleteAllAuthors() {

        // When
        ResponseEntity<Void> responseEntity = underTestAuthorRestController.deleteAll();

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authorService, times(1)).deleteAll();
    }
}


package com.giant.springbootrestapipostgresql.service;

import com.giant.springbootrestapipostgresql.entity.Author;
import com.giant.springbootrestapipostgresql.repository.AuthorRepository;

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
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService underTestAuthorService;

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

        // When
        underTestAuthorService.add(author);

        // Then
        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);

        verify(authorRepository).save(authorArgumentCaptor.capture());
        Author capturedAuthor = authorArgumentCaptor.getValue();

        assertThat(capturedAuthor).isEqualTo(author);
    }

    @Test
    public void canGetAllAuthors() {

        // When
        underTestAuthorService.getAll();

        // Then
        verify(authorRepository).findAll();
    }

    @Test
    public void canGetAuthorByGivenId() {

        // Given
        long authorId = 1L;

        // When
        underTestAuthorService.getById(authorId);

        // Then
        ArgumentCaptor<Long> authorIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(authorRepository).findById(authorIdArgumentCaptor.capture());
        Long capturedAuthorId = authorIdArgumentCaptor.getValue();

        assertThat(capturedAuthorId).isEqualTo(authorId);
    }

    @Test
    public void canGetAuthorsByGivenFirstName() {

        // Given
        String authorsFirstName = "Albert";

        // When
        underTestAuthorService.getByFirstName(authorsFirstName);

        // Then
        ArgumentCaptor<String> authorsFirstNameArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(authorRepository).findByFirstName(authorsFirstNameArgumentCaptor.capture());
        String capturedAuthorsFirstName = authorsFirstNameArgumentCaptor.getValue();

        assertThat(capturedAuthorsFirstName).isEqualTo(authorsFirstName);
    }

    @Test
    public void canUpdateAuthorByGivenIdWhenAuthorExists() {

        // Given
        Long authorToBeUpdatedId = 1L;

        Author existingAuthor = new Author();
        existingAuthor.setId(authorToBeUpdatedId);
        existingAuthor.setFirstName("Albert");
        existingAuthor.setLastName("Camus");
        existingAuthor.setBirthYear(1913);
        existingAuthor.setBiography("French author");
        existingAuthor.setEmail("albertcamus@email.com");
        existingAuthor.setIsbns(Arrays.asList("ISBN 0-061-96436-0",
                                              "ISBN 0-068-91426-1"));

        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorToBeUpdatedId);
        updatedAuthor.setFirstName("Franz");
        updatedAuthor.setLastName("Kafka");
        updatedAuthor.setBirthYear(1883);
        updatedAuthor.setBiography("Jewish novelist from Prague");
        updatedAuthor.setEmail("franzkafka@email.com");
        updatedAuthor.setIsbns(Arrays.asList("ISBN 0-061-96136-0",
                                             "ISBN 0-068-71422-1"));

        when(authorRepository.findById(authorToBeUpdatedId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        // When
        Author returnedAuthor = underTestAuthorService.updateById(authorToBeUpdatedId, updatedAuthor);

        // Then
        assertThat(returnedAuthor).isNotNull();
        assertThat(returnedAuthor).isEqualTo(updatedAuthor);
        verify(authorRepository, times(1)).findById(authorToBeUpdatedId);
        verify(authorRepository, times(1)).save(existingAuthor);
    }

    @Test
    public void cantUpdateAuthorByGivenIdWhenAuthorDoesNotExist() {

        // Given
        Long authorToBeUpdatedId = 1L;

        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorToBeUpdatedId);
        updatedAuthor.setFirstName("Franz");
        updatedAuthor.setLastName("Kafka");
        updatedAuthor.setBirthYear(1883);
        updatedAuthor.setBiography("Jewish novelist from Prague");
        updatedAuthor.setEmail("franzkafka@email.com");
        updatedAuthor.setIsbns(Arrays.asList("ISBN 0-061-96136-0",
                                             "ISBN 0-068-71422-1"));

        when(authorRepository.findById(authorToBeUpdatedId)).thenReturn(Optional.empty());

        // When
        Author returnedAuthor = underTestAuthorService.updateById(authorToBeUpdatedId, updatedAuthor);

        // Then
        assertThat(returnedAuthor).isNull();
        verify(authorRepository, times(1)).findById(authorToBeUpdatedId);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    public void canDeleteAuthorByGivenId() {

        // Given
        long authorId = 1L;

        // When
        underTestAuthorService.deleteById(authorId);

        // Then
        ArgumentCaptor<Long> authorIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(authorRepository).deleteById(authorIdArgumentCaptor.capture());
        Long capturedAuthorId = authorIdArgumentCaptor.getValue();

        assertThat(capturedAuthorId).isEqualTo(authorId);
    }

    @Test
    public void canDeleteAllAuthors() {

        // When
        underTestAuthorService.deleteAll();

        // Then
        verify(authorRepository).deleteAll();
    }
}

package com.example.library.service;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static final Long BOOK_ID = 1L;
    private static final Long MISSING_ID = 0L;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setup() {
        book = new Book(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008);
        bookDTO = new BookDTO(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008);
    }

    @Test
    void getAllBooks_returnListOfBooks() {
        List<Book> books = Collections.singletonList(book);
        List<BookDTO> expected = Collections.singletonList(bookDTO);

        when(bookRepository.findAll()).thenReturn(books);
        when(mapper.toBookDTO(book)).thenReturn(bookDTO);

        List<BookDTO> actual = bookService.getAllBooks();

        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findAll();
        verify(mapper, times(1)).toBookDTO(any());
    }

    @Test
    void getAllBooks_returnListOfEmptyBooks() {
        List<Book> emptyList = Collections.emptyList();

        when(bookRepository.findAll()).thenReturn(emptyList);

        List<BookDTO> actual = bookService.getAllBooks();

        assertTrue(actual.isEmpty());
        verify(bookRepository, times(1)).findAll();
        verify(mapper, never()).toBookDTO(any());
    }

    @Test
    void getBookById_returnsBookWhenBookExists() {
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findById(BOOK_ID)).thenReturn(optionalBook);
        when(mapper.toBookDTO(any())).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.getBookById(BOOK_ID);

        assertTrue(result.isPresent());
        assertEquals(bookDTO, result.get());
        verify(bookRepository, times(1)).findById(any());
        verify(mapper, times(1)).toBookDTO(any());
    }

    @Test
    void getBookById_returnsEmptyOptionalWhenBookDoesNotExist() {
        Optional<Book> emptyOptionalBook = Optional.empty();

        when(bookRepository.findById(BOOK_ID)).thenReturn(emptyOptionalBook);

        Optional<BookDTO> result = bookService.getBookById(BOOK_ID);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(any());
        verify(mapper, times(0)).toBookDTO(any());
    }

    @Test
    void getBookById_withMissingId() {
        Optional<Book> emptyOptionalBook = Optional.empty();

        when(bookRepository.findById(MISSING_ID)).thenReturn(emptyOptionalBook);

        Optional<BookDTO> result = bookService.getBookById(MISSING_ID);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(any());
        verify(mapper, times(0)).toBookDTO(any());
    }

    @Test
    void createBook_savesAndReturnsBookDTO() {
        when(mapper.toBookEntity(bookDTO)).thenReturn(book);
        when(mapper.toBookDTO(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);

        BookDTO result = bookService.createBook(bookDTO);

        assertNotNull(result);
        assertEquals(bookDTO, result);
        verify(mapper, times(1)).toBookEntity(any());
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void updateBook_updatesAndReturnsUpdatedBookDTOWhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.toBookDTO(book)).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.updateBook(1L, bookDTO);

        assertTrue(result.isPresent());
        assertEquals(bookDTO, result.get());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
        verify(mapper, times(1)).toBookDTO(book);
    }

    @Test
    void updateBook_returnsEmptyOptionalWhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<BookDTO> result = bookService.updateBook(1L, bookDTO);

        assertTrue(result.isEmpty());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBookById_returnsTrueWhenBookExists() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        boolean result = bookService.deleteBookById(1L);

        assertTrue(result);
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBookById_ReturnsFalseWhenBookDoesNotExist() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        boolean result = bookService.deleteBookById(1L);

        assertFalse(result);
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(anyLong());
    }

}

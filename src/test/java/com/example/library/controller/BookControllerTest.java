package com.example.library.controller;

import com.example.library.dto.BookDTO;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    private static final String BASE_URL = "/api/v1/books";
    private static final String BOOK_ID = "1";
    private static final String BASE_URL_BOOK_ID = BASE_URL + "/" + BOOK_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008);
    }

    @Test
    void getBooks_returnsListOfBooks() throws Exception {
        List<BookDTO> books = Collections.singletonList(bookDTO);
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(bookDTO.getTitle())))
                .andExpect(jsonPath("$[0].author", is(bookDTO.getAuthor())));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBooks_returnsEmptyListWhenNoBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of());

        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_returnsBookWhenFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(bookDTO));

        mockMvc.perform(get(BASE_URL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(bookDTO.getTitle())))
                .andExpect(jsonPath("$.author", is(bookDTO.getAuthor())));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_returnsNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBook_createsAndReturnsBook() throws Exception {
        when(bookService.createBook(any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(bookDTO.getTitle())))
                .andExpect(jsonPath("$.author", is(bookDTO.getAuthor())));

        verify(bookService, times(1)).createBook(any(BookDTO.class));
    }

    @Test
    void updateBook_updatesAndReturnsBookWhenFound() throws Exception {
        when(bookService.updateBook(eq(1L), any(BookDTO.class))).thenReturn(Optional.of(bookDTO));

        mockMvc.perform(put(BASE_URL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(bookDTO.getTitle())))
                .andExpect(jsonPath("$.author", is(bookDTO.getAuthor())));

        verify(bookService, times(1)).updateBook(eq(1L), any(BookDTO.class));
    }

    @Test
    void updateBook_returnsNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.updateBook(eq(1L), any(BookDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put(BASE_URL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).updateBook(eq(1L), any(BookDTO.class));
    }

    @Test
    void deleteBookById_deletesAndReturnsNoContentWhenBookExists() throws Exception {
        when(bookService.deleteBookById(1L)).thenReturn(true);

        mockMvc.perform(delete(BASE_URL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBookById(1L);
    }

    @Test
    void deleteBookById_returnsNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.deleteBookById(1L)).thenReturn(false);

        mockMvc.perform(delete(BASE_URL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).deleteBookById(1L);
    }
}

package com.example.library.service;

import com.example.library.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> getAllBooks();
    Optional<BookDTO> getBookById(Long id);
    BookDTO createBook(BookDTO bookDTO);
    Optional<BookDTO> updateBook(Long id, BookDTO bookDTO);
    boolean deleteBookById(Long id);
}

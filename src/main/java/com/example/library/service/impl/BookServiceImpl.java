package com.example.library.service.impl;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(bookMapper::toBookDTO)
                .toList();
    }

    @Override
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookDTO);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.toBookEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookDTO(savedBook);
    }

    @Override
    public Optional<BookDTO> updateBook(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setAuthor(bookDTO.getAuthor());
                    existingBook.setIsbn(bookDTO.getIsbn());
                    existingBook.setTitle(bookDTO.getTitle());
                    existingBook.setPublicationYear(bookDTO.getPublicationYear());
                    Book updatedBook = bookRepository.save(existingBook);
                    return bookMapper.toBookDTO(updatedBook);
                });
    }

    @Override
    public boolean deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

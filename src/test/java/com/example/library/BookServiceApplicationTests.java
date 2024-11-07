package com.example.library;

import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookServiceApplicationTests {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private BookRepository bookRepository;

	@Test
	void contextLoads() {
		assertNotNull(bookService, "BookService should be loaded in the application context");
		assertNotNull(bookMapper, "BookMapper should be loaded in the application context");
		assertNotNull(bookRepository, "BookRepository should be loaded in the application context");
	}

}

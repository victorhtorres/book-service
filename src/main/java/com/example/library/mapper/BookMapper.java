package com.example.library.mapper;

import com.example.library.dto.BookDTO;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "id", target = "id")
    BookDTO toBookDTO(Book book);

    @Mapping(source = "id", target = "id")
    Book toBookEntity(BookDTO bookDTO);
}

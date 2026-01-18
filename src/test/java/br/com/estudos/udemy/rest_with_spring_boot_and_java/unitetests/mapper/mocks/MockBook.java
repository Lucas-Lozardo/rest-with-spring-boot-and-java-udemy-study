package br.com.estudos.udemy.rest_with_spring_boot_and_java.unitetests.mapper.mocks;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.BookDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 16; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(BigDecimal.valueOf(25D));
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(BigDecimal.valueOf(25D));
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

}
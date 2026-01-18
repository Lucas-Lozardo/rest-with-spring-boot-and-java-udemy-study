package br.com.estudos.udemy.rest_with_spring_boot_and_java.services;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.BookDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.RequiredObjectIsNullException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Book;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.repository.BookRepository;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.unitetests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;


    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repo;


    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findAll() {

        List<Book> list = input.mockEntityList();
        when(repo.findAll()).thenReturn(list);
        List<BookDTO> books = service.findAll();

        assertNotNull(books);
        assertEquals(16, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        //Teste mais específico do HATEOAS
        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertEquals("Author Test1", bookOne.getAuthor());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals(BigDecimal.valueOf(25D), bookOne.getPrice());
        assertEquals("Title Test1", bookOne.getTitle());


    var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getLinks());

        //Teste mais específico do HATEOAS
        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertEquals("Author Test4", bookFour.getAuthor());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals(BigDecimal.valueOf(25D), bookFour.getPrice());
        assertEquals("Title Test4", bookFour.getTitle());


    }

    @Test
    void findById() {

        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //Teste mais específico do HATEOAS
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("/api/book/v1/1")
                && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

            assertNotNull(result.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/book/v1")
                            && link.getType().equals("GET")
                    )
            );

        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(25D), result.getPrice());
        assertEquals("Title Test1", result.getTitle());

    }

    @Test
    void create() {

        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        //when(repo.save(book)).thenReturn(persisted);
        when(repo.save(any(Book.class))).thenReturn(persisted);


        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //Teste mais específico do HATEOAS
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(25D), result.getPrice());
        assertEquals("Title Test1", result.getTitle());

    }

//    @Test
//    void createV2() {
//        Book book = input.mockEntity(1);
//        Book persisted = book;
//        persisted.setId(1L);
//
//        BookDTOV2 dtov2 = input.mockDTOV2(1);
//
//        when(repo.save(book)).thenReturn(persisted);
//
//       var result = service.createV2(dtov2);
//
//        assertNotNull(result);
//        assertNotNull(result.getId());
//        assertNotNull(result.getLinks());
//
//        //Teste mais específico do HATEOAS
//        assertNotNull(result.getLinks().stream()
//                .anyMatch(link -> link.getRel().value().equals("self")
//                        && link.getHref().endsWith("/api/book/v2/1")
//                        && link.getType().equals("GET")
//                )
//        );
//
//        assertNotNull(result.getLinks().stream()
//                .anyMatch(link -> link.getRel().value().equals("create")
//                        && link.getHref().endsWith("/api/book/v2")
//                        && link.getType().equals("POST")
//                )
//        );
//
//        assertNotNull(result.getLinks().stream()
//                .anyMatch(link -> link.getRel().value().equals("update")
//                        && link.getHref().endsWith("/api/book/v2")
//                        && link.getType().equals("PUT")
//                )
//        );
//
//        assertNotNull(result.getLinks().stream()
//                .anyMatch(link -> link.getRel().value().equals("delete")
//                        && link.getHref().endsWith("/api/book/v2/1")
//                        && link.getType().equals("DELETE")
//                )
//        );
//
//        assertNotNull(result.getLinks().stream()
//                .anyMatch(link -> link.getRel().value().equals("findAll")
//                        && link.getHref().endsWith("/api/book/v2")
//                        && link.getType().equals("GET")
//                )
//        );
//
//        assertEquals("Address Test1", result.getAddress());
//        assertEquals("First Name Test1", result.getFirstName());
//        assertEquals("Last Name Test1", result.getLastName());
//        assertEquals("Female", result.getGender());
//        //assertEquals("1990/ 6 /15", result.getBirthDay().toString());
//
//    }


    @Test
    void testCreateWithNullBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
            service.create(null);
                });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repo.findById(1L)).thenReturn(Optional.of(book));
        when(repo.save(book)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //Teste mais específico do HATEOAS
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                )
        );

        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(25D), result.getPrice());
        assertEquals("Title Test1", result.getTitle());


    }

    @Test
    void testUpdateWithNullBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {

        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(book));

        service.delete(1L);
        verify(repo, times(1)).findById(anyLong());
        verify(repo, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repo);

    }
}
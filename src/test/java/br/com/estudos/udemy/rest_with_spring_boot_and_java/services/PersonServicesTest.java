package br.com.estudos.udemy.rest_with_spring_boot_and_java.services;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.repository.PersonRepository;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repo;


    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {

    }

    @Test
    void findById() {

        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //Teste mais específico do HATEOAS
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("/api/person/v1/1")
                && link.getType().equals("GET")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

            assertNotNull(result.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")
                    )
            );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void create() {

    }

    @Test
    void createV2() {
    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }
}
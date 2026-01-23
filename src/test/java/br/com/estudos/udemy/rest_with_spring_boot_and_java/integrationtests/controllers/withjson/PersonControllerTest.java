package br.com.estudos.udemy.rest_with_spring_boot_and_java.integrationtests.controllers.withjson;


import br.com.estudos.udemy.rest_with_spring_boot_and_java.config.TestConfigs;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.integrationtests.dto.PersonDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

//SWAGGER
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//PARA QUE OS TESTES SEJAM REALIZADOS EM ORDEM ESPECÍFICA, UTILIZANDO O RESULTADE DE UM EM OUTRO.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO personDTO;

    //PARA SALVAR O ESTADO DO OBJETO PARA SER UTILIZADO EM TODOS OS TESTES
    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        //PARA DESABILITAR FALHAS DE ATRIBUTOS DESCONHECIDOS DO JSON RETORNADO
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDTO = new PersonDTO();
    }


    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        //ESPECIFICAÇÃO NO CABEÇALHO (HEADER), O MESMO UTILIZADO NO POSTMAN
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        //ESPECIFICAÇÃO DO CONTENT-TYPE NO CABEÇALHO (HEADER) E CORPO DA REQUISIÇÃO (BODY)
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(personDTO)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                    .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallamn", createdPerson.getLastName());
        assertEquals("New York City - New York - USA", createdPerson.getAddress());
        assertEquals("M", createdPerson.getGender());

    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        //ESPECIFICAÇÃO NO CABEÇALHO (HEADER), O MESMO UTILIZADO NO POSTMAN
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ANOTHERHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        //ESPECIFICAÇÃO DO CONTENT-TYPE NO CABEÇALHO (HEADER) E CORPO DA REQUISIÇÃO (BODY)
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(personDTO)
                .when()
                    .post()
                .then()
                    .statusCode(403)
                .extract()
                    .body()
                    .asString();

        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();


        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallamn", createdPerson.getLastName());
        assertEquals("New York City - New York - USA", createdPerson.getAddress());
        assertEquals("M", createdPerson.getGender());
    }

    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ANOTHERHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();


        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @Test
    void update() throws JsonProcessingException {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }

    private void mockPerson() {
        personDTO.setFirstName("Richard");
        personDTO.setLastName("Stallamn");
        personDTO.setAddress("New York City - New York - USA");
        personDTO.setGender("Male");

    }
}
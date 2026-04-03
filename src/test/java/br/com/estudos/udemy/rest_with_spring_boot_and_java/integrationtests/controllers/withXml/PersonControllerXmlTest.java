package br.com.estudos.udemy.rest_with_spring_boot_and_java.integrationtests.controllers.withXml;


import br.com.estudos.udemy.rest_with_spring_boot_and_java.config.TestConfigs;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.integrationtests.dto.PersonDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

//SWAGGER
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//PARA QUE OS TESTES SEJAM REALIZADOS EM ORDEM ESPECÍFICA, UTILIZANDO O RESULTADE DE UM EM OUTRO.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXmlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static XmlMapper xmlMapper;

    private static PersonDTO personDTO;

    //PARA SALVAR O ESTADO DO OBJETO PARA SER UTILIZADO EM TODOS OS TESTES
    @BeforeAll
    static void setUp() {
        xmlMapper = new XmlMapper();
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        //PARA DESABILITAR FALHAS DE ATRIBUTOS DESCONHECIDOS DO JSON RETORNADO
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        personDTO = new PersonDTO();
    }


    @Test
    @Order(1)
    @Disabled("REASON: Still Under Development")
    void createTest() throws JsonProcessingException {
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
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)            ///Precisa do accept para ter como resposta o que foi definido.
                    .body(personDTO)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                    .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    } @Test
    @Order(2)
    @Disabled("REASON: Still Under Development")
    void updateTest() throws JsonProcessingException {
        personDTO.setLastName("Benedict Torvalds");

        //ESPECIFICAÇÃO DO CONTENT-TYPE NO CABEÇALHO (HEADER) E CORPO DA REQUISIÇÃO (BODY)
        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                    .body(personDTO)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                    .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(3)
    @Disabled("REASON: Still Under Development")
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                    .pathParam("id", personDTO.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(4)
    @Disabled("REASON: Still Under Development")
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                    .pathParam("id", personDTO.getId())
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    @Disabled("REASON: Still Under Development")
    void deleteTest() throws JsonProcessingException {

        given(specification)
                    .pathParam("id", personDTO.getId())
                .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }

    private void mockPerson() {
        personDTO.setFirstName("Linus");
        personDTO.setLastName("Torvalds");
        personDTO.setAddress("Helsinki - Finland");
        personDTO.setGender("Male");
        personDTO.setEnabled(true);

    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                    .accept(MediaType.APPLICATION_XML_VALUE)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        List<PersonDTO> people = xmlMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});

        PersonDTO personOne = people.get(0);
        personDTO = personOne;

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Ayrton", personOne.getFirstName());
        assertEquals("Senna", personOne.getLastName());
        assertEquals("São Paulo - Brasil", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertTrue(personOne.getEnabled());
    }
}
package br.com.estudos.udemy.rest_with_spring_boot_and_java.services;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers.PersonController;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.PersonDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v2.PersonDTOV2;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.RequiredObjectIsNullException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.ResourceNotFoundException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper.ObjectMapper;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper.custom.PersonMapper;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServices {


    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repo;

    //PersonDTOV2
    @Autowired
    private PersonMapper converter;


    public List<PersonDTO> findAll(){
        logger.info("Finding all people!");

        var persons = ObjectMapper.parseListObjects(repo.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);

        //OU
        //persons.forEach(p -> addHateoasLinks(p));

        return persons;
    }


    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");     //Aparece o log no Terminal.

        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);

        //ADD link HATEOAS, olhar final da classe. Import static.
        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person");
        var entity = ObjectMapper.parseObject(person, Person.class);
        var dto = ObjectMapper.parseObject(repo.save(entity), PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person){
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person V2");
        var entity = converter.convertDTOV2ToEntity(person);
        return converter.convertEntityToDTOV2(repo.save(entity));

    }

    public PersonDTO update(PersonDTO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person");
        Person entity = repo.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = ObjectMapper.parseObject(repo.save(entity), PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one person");
        Person personLocalized = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repo.delete(personLocalized);

    }

    //ADD link HATEOAS, IMPORT teve que ser manualmente
    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(PersonController.class)
                .slash(dto.getId())
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(PersonController.class)
                .withRel("findAll")
                .withType("GET"));

        dto.add(linkTo(PersonController.class)
                .withRel("create")
                .withType("POST"));

        dto.add(linkTo(PersonController.class)
                .slash(dto.getId())
                .withRel("update")
                .withType("PUT"));

        dto.add(linkTo(PersonController.class)
                .slash(dto.getId())
                .withRel("delete")
                .withType("DELETE"));
    }
}

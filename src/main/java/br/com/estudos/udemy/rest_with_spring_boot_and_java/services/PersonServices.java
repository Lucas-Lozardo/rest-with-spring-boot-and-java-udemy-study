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
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;


@Service
public class PersonServices {


    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repo;

    @Autowired
    private PagedResourcesAssembler<PersonDTO> assembler; //Para adicionar os links do HAL Paginação.

    //PersonDTOV2
    @Autowired
    private PersonMapper converter;


    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable){
        logger.info("Finding all people!");

        //Implementando o page
        var people = repo.findAll(pageable);

        var peopleWithLinks = people.map(person -> {
            var dto = ObjectMapper.parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        //Links do Pageable
        Link findAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                        .findAll(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())))
                        .withSelfRel();

        return assembler.toModel(peopleWithLinks, findAllLink);
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

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Disabling one person");
        repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repo.disablePerson(id);

        var entity = repo.findById(id).get();

        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
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
                .withRel("disable")
                .withType("PATCH"));

        dto.add(linkTo(PersonController.class)
                .slash(dto.getId())
                .withRel("delete")
                .withType("DELETE"));
    }
}

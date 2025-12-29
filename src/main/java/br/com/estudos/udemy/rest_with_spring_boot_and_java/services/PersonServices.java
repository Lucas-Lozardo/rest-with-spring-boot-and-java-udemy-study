package br.com.estudos.udemy.rest_with_spring_boot_and_java.services;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.PersonDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v2.PersonDTOV2;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.ResourceNotFoundException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper.ObjectMapper;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper.custom.PersonMapper;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repo;

    //PersonDTOV2
    @Autowired
    private PersonMapper converter;


    public List<PersonDTO> findAll(){
        logger.info("Finding all people!");

        return ObjectMapper.parseListObjects(repo.findAll(), PersonDTO.class);
    }


    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");     //Aparece o log no Terminal.

        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return ObjectMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one person");
        var entity = ObjectMapper.parseObject(person, Person.class);
        return ObjectMapper.parseObject(repo.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person){
        logger.info("Creating one person V2");
        var entity = converter.convertDTOV2ToEntity(person);
        return converter.convertEntityToDTOV2(repo.save(entity));
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one person");
        Person entity = repo.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return ObjectMapper.parseObject(repo.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one person");
        Person personLocalized = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repo.delete(personLocalized);
    }
}

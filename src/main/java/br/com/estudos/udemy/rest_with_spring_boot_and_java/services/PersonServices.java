package br.com.estudos.udemy.rest_with_spring_boot_and_java.services;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.ResourceNotFoundException;
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


    public List<Person> findAll(){
        logger.info("Finding all people!");

        return repo.findAll();
    }


    public Person findById(Long id){
        logger.info("Finding one Person!");     //Aparece o log no Terminal.

        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person){
        logger.info("Creating one person");
        return repo.save(person);
    }

    public Person update(Person person){
        logger.info("Updating one person");
        Person personLocalized = repo.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        personLocalized.setFirstName(person.getFirstName());
        personLocalized.setLastName(person.getLastName());
        personLocalized.setAddress(person.getAddress());
        personLocalized.setGender(person.getGender());
        return repo.save(personLocalized);
    }

    public void delete(Long id){
        logger.info("Deleting one person");
        Person personLocalized = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repo.delete(personLocalized);
    }
}

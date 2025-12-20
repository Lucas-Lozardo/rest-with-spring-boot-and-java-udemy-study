package br.com.estudos.udemy.rest_with_spring_boot_and_java;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id){
        logger.info("Finding one Person!");     //Aparece o log no Terminal.

        //MOCK DADOS DO BANCO DE DADOS!
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Lucas");
        person.setLastName("Lozardo");
        person.setAddress("SP");
        person.setGender("Male");

        return person;
    }
}

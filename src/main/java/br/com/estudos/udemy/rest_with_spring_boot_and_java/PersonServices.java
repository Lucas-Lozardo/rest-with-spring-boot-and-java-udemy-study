package br.com.estudos.udemy.rest_with_spring_boot_and_java;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll(){

        List<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < 8; i++){
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }



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

    private Person mockPerson(int i) {

        //MOCK DADOS DO BANCO DE DADOS!
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Some address in BR");
        person.setGender("Male");

        return person;
    }
}

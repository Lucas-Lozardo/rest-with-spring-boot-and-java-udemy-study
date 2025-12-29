package br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper.custom;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v2.PersonDTOV2;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTOV2(Person person){
        PersonDTOV2 dtoV2 = new PersonDTOV2();
        dtoV2.setFirstName(person.getFirstName());
        dtoV2.setLastName(person.getLastName());
        dtoV2.setBirthDay(new Date());
        dtoV2.setAddress(person.getAddress());
        dtoV2.setGender(person.getGender());

        return dtoV2;
    }

    public Person convertDTOV2ToEntity(PersonDTOV2 person){
        Person entity = new Person();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        //entity.setBirthDay(new Date());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return entity;
    }
}

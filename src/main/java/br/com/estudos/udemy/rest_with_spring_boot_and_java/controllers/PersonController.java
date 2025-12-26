package br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.services.PersonServices;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;
    //private PersonServices service = new PersonServices();


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
        return service.findAll();
    }

    //@GetMapping
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.GET == @GetMapping    MediaType do org.springframework.http.MediaType
    public Person findById(@PathVariable("id") Long id){
        return service.findById(id);
    }
    //@GetMapping
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.POST == @PostMapping    MediaType do org.springframework.http.MediaType
    public Person create(@RequestBody Person person){
        return service.create(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person){
        return service.update(person);
    }

    @DeleteMapping
        public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

package br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.PersonServices;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;
    //private PersonServices service = new PersonServices();

    //@GetMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.GET == @GetMapping    MediaType do org.springframework.http.MediaType
    public Person findById(@PathVariable("id") String id){
        return service.findById(id);
    }
    //@GetMapping
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.POST == @PostMapping    MediaType do org.springframework.http.MediaType
    public Person create(@RequestBody Person person){
        return service.create(person);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person){
        return service.update(person);
    }

    @RequestMapping(method = RequestMethod.DELETE)
        public void delete(@PathVariable String id){
        service.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
        return service.findAll();
    }
}

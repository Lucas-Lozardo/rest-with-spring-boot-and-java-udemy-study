package br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.PersonDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v2.PersonDTOV2;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.services.PersonServices;
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
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    //@GetMapping
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.GET == @GetMapping    MediaType do org.springframework.http.MediaType
    public PersonDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    //@GetMapping
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.POST == @PostMapping    MediaType do org.springframework.http.MediaType
    public PersonDTO create(@RequestBody PersonDTO person){
        return service.create(person);
    }

    //VERSION 2
    @PostMapping(name = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)    //method = RequestMethod.POST == @PostMapping    MediaType do org.springframework.http.MediaType
    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person){
        return service.createV2(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO update(@RequestBody PersonDTO person){
        return service.update(person);
    }

    @DeleteMapping
        public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

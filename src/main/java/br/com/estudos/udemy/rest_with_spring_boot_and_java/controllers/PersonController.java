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
@RequestMapping("api/person")
public class PersonController {

    @Autowired
    private PersonServices service;
    //private PersonServices service = new PersonServices();


    @GetMapping(value = "/v1/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    //@GetMapping
    @GetMapping(value = "/v1/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })    //method = RequestMethod.GET == @GetMapping    MediaType do org.springframework.http.MediaType
    public PersonDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    //@GetMapping
    @PostMapping(value = "/v1",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })    //method = RequestMethod.POST == @PostMapping    MediaType do org.springframework.http.MediaType
    public PersonDTO create(@RequestBody PersonDTO person){
        return service.create(person);
    }

    //VERSION 2
    @PostMapping(value = "/v2",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
    public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person){
        return service.createV2(person);
    }

    @PutMapping(value = "/v1",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE })
    public PersonDTO update(@RequestBody PersonDTO person){
        return service.update(person);
    }

    @DeleteMapping(value = "/v1")
        public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

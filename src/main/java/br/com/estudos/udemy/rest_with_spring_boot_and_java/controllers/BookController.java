package br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers.docs.BookControllerDocs;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.BookDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/v1")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookService service;


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override       /// para o implements
    public List<BookDTO> findAll(){
        return service.findAll();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public BookDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public BookDTO create(@RequestBody BookDTO dto){
        return service.create(dto);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public BookDTO update(@RequestBody BookDTO dto){
        return service.update(dto);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}

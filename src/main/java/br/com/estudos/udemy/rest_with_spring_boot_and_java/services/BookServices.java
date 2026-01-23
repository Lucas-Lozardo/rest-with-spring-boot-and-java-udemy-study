package br.com.estudos.udemy.rest_with_spring_boot_and_java.services;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers.BookController;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.BookDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.RequiredObjectIsNullException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.ResourceNotFoundException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper.ObjectMapper;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.model.Book;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class BookServices {

    private Logger logger = LoggerFactory.getLogger(BookServices.class.getName());

    @Autowired
    private BookRepository repo;


    public List<BookDTO> findAll(){

        logger.info("Finding all books!");

        var books = ObjectMapper.parseListObjects(repo.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);

        return books;
    }

    public BookDTO findById(Long id){

        logger.info("Finding one book");

        var entity = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));
        var dto = ObjectMapper.parseObject(entity, BookDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public BookDTO create(BookDTO bookDTO){

        if (bookDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book");

        var entity = ObjectMapper.parseObject(bookDTO, Book.class);
        var dto = ObjectMapper.parseObject(repo.save(entity), BookDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public BookDTO update(BookDTO bookDTO){

        if (bookDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book");

        Book entity = repo.findById(bookDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(bookDTO.getAuthor());
        entity.setLaunchDate(bookDTO.getLaunchDate());
        entity.setPrice(bookDTO.getPrice());
        entity.setTitle(bookDTO.getTitle());

        var dto = ObjectMapper.parseObject(repo.save(entity), BookDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one book");

        Book bookLocalized = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));
        repo.delete(bookLocalized);
    }

    //ADD link HATEOAS, IMPORT teve que ser manualmente
    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(BookController.class)
                .slash(dto.getId())
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(BookController.class)
                .withRel("findAll")
                .withType("GET"));

        dto.add(linkTo(BookController.class)
                .withRel("create")
                .withType("POST"));

        dto.add(linkTo(BookController.class)
                .slash(dto.getId())
                .withRel("update")
                .withType("PUT"));

        dto.add(linkTo(BookController.class)
                .slash(dto.getId())
                .withRel("delete")
                .withType("DELETE"));
    }
}

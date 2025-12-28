package br.com.estudos.udemy.rest_with_spring_boot_and_java.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    //Mapear objeto de origem para objeto de destino
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    //Um objeto, parse de DTO -> ENTITY e ENTITY -> DTO
    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    //Lista de objetos, parse de List<DTO> -> List<ENTITY> e List<ENTITY> -> List<DTO>
    public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination){

        List<D> destinationObjects = new ArrayList<D>();
        for (Object o : origin){
            destinationObjects.add(mapper.map(o, destination));
        }

        return destinationObjects;
    }
}

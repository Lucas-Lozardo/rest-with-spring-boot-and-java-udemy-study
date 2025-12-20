package br.com.estudos.udemy.rest_with_spring_boot_and_java.math;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.request.converters.NumberConverter;
import org.springframework.stereotype.Component;

@Component
public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo){
        return numberOne + numberTwo;
    }

    public Double substraction(Double numberOne, Double numberTwo){
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo){
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo){
        return numberOne / numberTwo;
    }

    public Double mean(Double numberOne, Double numberTwo){
        return numberOne + numberTwo / 2;
    }

    public Double squareRoot(Double number){
        return Math.sqrt(number);
    }
}

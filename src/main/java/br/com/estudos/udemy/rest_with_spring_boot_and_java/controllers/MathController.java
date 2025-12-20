package br.com.estudos.udemy.rest_with_spring_boot_and_java.controllers;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.UnsupportedMathOperationException;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.math.SimpleMath;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.request.converters.NumberConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    private SimpleMath calculator = new SimpleMath();


    //http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo){

        NumberConverter.checkIfMathOperationIsSupported(numberOne, numberTwo);

        return calculator.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    //http://localhost:8080/math/subtraction/3/5
    @RequestMapping("/substraction/{numberOne}/{numberTwo}")
    public Double substraction(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo){

        NumberConverter.checkIfMathOperationIsSupported(numberOne, numberTwo);

        return calculator.substraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));    }

    //http://localhost:8080/math/multiplication/3/5
    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo){

        NumberConverter.checkIfMathOperationIsSupported(numberOne, numberTwo);

        return calculator.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    //http://localhost:8080/math/division/3/5
    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo){

        NumberConverter.checkIfMathOperationIsSupported(numberOne, numberTwo);

        if (numberTwo.equals("0")){
            throw new UnsupportedMathOperationException("Please set a numeric value different for 0!");
        }
        return calculator.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));    }

    //http://localhost:8080/math/mean/3/5
    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo){

        NumberConverter.checkIfMathOperationIsSupported(numberOne, numberTwo);

        return calculator.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));    }

    //http://localhost:8080/math/squareRoot/81
    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(@PathVariable("number") String number){

        if (!NumberConverter.isNumeric(number)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }

        Double numberConverted = NumberConverter.convertToDouble(number);

        if (numberConverted < 0){
            throw new UnsupportedMathOperationException("It will not be allowed calculate the square root of negative number!");
        }

        return calculator.squareRoot(numberConverted);
    }
}

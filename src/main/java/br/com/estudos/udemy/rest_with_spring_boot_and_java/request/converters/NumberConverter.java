package br.com.estudos.udemy.rest_with_spring_boot_and_java.request.converters;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.exception.UnsupportedMathOperationException;
import org.springframework.stereotype.Component;

@Component
public class NumberConverter {

    public static void checkIfMathOperationIsSupported(String numberOne, String numberTwo){
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }
}

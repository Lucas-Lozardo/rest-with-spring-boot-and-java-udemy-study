package br.com.estudos.udemy.rest_with_spring_boot_and_java.exception;

import java.util.Date;

public record ExceptionResponse(Date timeStamp, String message, String details) {
}

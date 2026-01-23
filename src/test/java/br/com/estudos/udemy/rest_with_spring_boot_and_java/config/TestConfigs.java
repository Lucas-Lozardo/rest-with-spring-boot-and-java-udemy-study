package br.com.estudos.udemy.rest_with_spring_boot_and_java.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    //CRIADO AFIM DE EVITAR ERRO DE DIGITAÇÃO FUTURAMENTE DURANTE AS IMPLEMENTAÇÕES
    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";
    String ORIGIN_LOCALHOST = "http://localhost:8080";
    String ORIGIN_ANOTHERHOST = "http://localhost:1000";
}

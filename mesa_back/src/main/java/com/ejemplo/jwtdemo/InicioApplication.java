package com.ejemplo.jwtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync // Habilita @Async para envío de correos en segundo plano
public class InicioApplication {


    public static void main(String[] args) {

        SpringApplication.run(InicioApplication.class, args);


    }

}
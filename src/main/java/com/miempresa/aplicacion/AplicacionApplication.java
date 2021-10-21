package com.miempresa.aplicacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.miempresa.aplicacion.controladores"})
public class AplicacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AplicacionApplication.class, args);
    }

}

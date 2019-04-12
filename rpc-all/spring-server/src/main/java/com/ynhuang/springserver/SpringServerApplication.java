package com.ynhuang.springserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringServerApplication {//implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringServerApplication.class);
        app.run(args);
    }

}

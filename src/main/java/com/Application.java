package com;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import utilities.Utility;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        Utility.main(null);

        //SpringApplication.run(Application.class, args);
    }
}
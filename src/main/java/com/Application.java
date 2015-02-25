package com;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import utilities.Utility;

import com.RouteController;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {

        RouteController rc = new RouteController();
        rc.generateRoute(0.0, 0.0);

        //SpringApplication.run(Application.class, args);
    }
}
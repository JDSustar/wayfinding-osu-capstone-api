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
        Route r = rc.generateRoute();

        for(Node n : r.getRoute())
        {
            System.out.println(n.getCoordinate().getLatitude() + ", " + n.getCoordinate().getLongitude());
        }

        //SpringApplication.run(Application.class, args);
    }
}
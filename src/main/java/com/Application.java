package com;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import utilities.Utility;

import com.RouteController;

@ComponentScan
@EnableAutoConfiguration
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);

        System.out.println("***PLEASE WAIT WHILE API SERVER INITIALIZES...");

        System.out.print("***Loading Buildings...");

        BuildingController bcc = new BuildingController();
        bcc.buildings();

        System.out.println(" Done.");

        System.out.print("***Loading Segments...");

        SegmentController scc = new SegmentController();
        scc.segments();

        System.out.println(" Done.");

        System.out.println("***API SERVER READY***");
    }
}
package com;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;
import utilities.Utility;

import com.RouteController;

@ComponentScan
@EnableAutoConfiguration
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);

        StopWatch sw = new StopWatch();
        StopWatch totalSw = new StopWatch();
        sw.start();
        totalSw.start();

        System.out.println("***PLEASE WAIT WHILE API SERVER INITIALIZES...");

        System.out.print("***Loading Buildings...");

        BuildingController bcc = new BuildingController();
        bcc.buildings();

        sw.stop();
        System.out.println(" Done. Completed in " + sw.getTotalTimeSeconds() + " seconds.");
        sw = new StopWatch();
        sw.start();

        System.out.print("***Loading Tours...");

        TourController tcc = new TourController();
        tcc.tours();

        sw.stop();
        System.out.println(" Done. Completed in " + sw.getTotalTimeSeconds() + " seconds.");
        sw = new StopWatch();
        sw.start();

        System.out.print("***Loading Segments...");

        SegmentController scc = new SegmentController();
        scc.segments();

        sw.stop();
        System.out.println(" Done. Completed in " + sw.getTotalTimeSeconds() + " seconds.");

        totalSw.stop();

        System.out.println("***API SERVER READY***");
        System.out.println("***" + totalSw.getTotalTimeSeconds() + " SECONDS to start API Server.");
    }
}
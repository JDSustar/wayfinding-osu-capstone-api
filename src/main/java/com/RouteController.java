package com;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {



    @RequestMapping("/generateRoute")
     public void generateRoute(double spcx, double spcy){

        // convert to lat long

        // grab all locations and segments from db

        // Dijkstra's Algorithm



    }



}

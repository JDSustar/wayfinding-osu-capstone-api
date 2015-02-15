package com;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.sql.SQLException;
import java.util.*;

@RestController
public class RouteController {



    @RequestMapping("/generateRoute")
     public void generateRoute(double spcX, double spcY)
    {
        //Convert the SPC coordinates to lat/long.

        //Get all locations and segments from the database. These are the nodes and edges for the algorithm.

        //Create the graph

        //Use Dijkstra's algorithm to find the shortest path from given starting point and destination




    }



}

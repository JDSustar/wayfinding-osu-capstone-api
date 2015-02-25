package com;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class RouteController {

    @RequestMapping("/generateRoute")
    public void generateRoute(double spcX, double spcY) {

        LocationController lc = new LocationController();
        LocationCollection lcc = lc.locations();

//        for(Location l : lcc.getLocations()){
//            System.out.println(l.getId() + "|" + l.getName() + "|" + l.getLatitude() + "|" + l.getLongitude());
//        }

        SegmentController sc = new SegmentController();
        SegmentCollection scc = sc.segments(lcc);

//        for(Segment s : scc.getSegments()){
//            System.out.println(s.getId() + "|" + s.getNode1().getSpcx() + "|" + s.getNode1().getSpcy());
//        }


//        UndirectedGraph<Location, Segment> ug = new SimpleGraph<Location, Segment>(Segment.class);
//
//        for(Location l : lcc.getLocations()){
//            ug.addVertex(l);
//        }
//
//        for(Segment s : scc.getSegments()){
//            ug.addEdge(s.getToNode(), s.getFromNode());
//        }
//
//        Location s = new Location(14, "Stillman Hall", 1825399.99612252, 729498.427258271);
//        Location e = new Location(14, "Arps Hall", 1825761.98379103, 729523.554250369);
//
//        List<Segment> l = DijkstraShortestPath.findPathBetween(ug, s, e);
//
//        for(Segment ss : l){
//            System.out.println(ss.getToNode().getName() + " - " +  ss.getFromNode().getName());
//        }
    }
}
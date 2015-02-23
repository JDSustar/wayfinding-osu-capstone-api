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

//        LocationController lc = new LocationController();
//        LocationCollection lcc = lc.locations();

//        for(Location l : lcc.getLocations()){
//            System.out.println(l.getId() + "|" + l.getName() + "|" + l.getSpcx() + "|" + l.getSpcy());
//        }

//        SegmentController sc = new SegmentController();
//        SegmentCollection scc = sc.segments();

//        for(Segment s : scc.getSegments()){
//            System.out.println(s.getId() + "|" + s.getNode1().getSpcx() + "|" + s.getNode1().getSpcy());
//        }


        UndirectedGraph<Location, Segment> ug = new SimpleGraph<Location, Segment>(Segment.class);

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


        Location l1 = new Location(1, "1", 0.0, 0.0);
        Location l2 = new Location(2, "2", 1.0, 0.0);
        Location l3 = new Location(3, "3", 0.0, 2.0);
        Location l4 = new Location(4, "4", 2.0, 3.0);
        ug.addVertex(l1);
        ug.addVertex(l2);
        ug.addVertex(l3);
        ug.addVertex(l4);
        Segment s1 = new Segment(l1, l2);
        Segment s2 = new Segment(l1, l3);
        Segment s3 = new Segment(l2, l4);
        Segment s4 = new Segment(l3, l4);
        ug.addEdge(s1.getToNode(), s1.getFromNode());
        ug.addEdge(s2.getToNode(), s2.getFromNode());
        ug.addEdge(s3.getToNode(), s3.getFromNode());
        ug.addEdge(s4.getToNode(), s4.getFromNode());

        List<Segment> l = DijkstraShortestPath.findPathBetween(ug, l1, l4);

        for(Segment ss : l){
            System.out.println(ss.getToNode().getName() + " - " +  ss.getFromNode().getName());
        }
    }
}
package com;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import utilities.Coordinate;

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

        UndirectedGraph<Location, Segment> ug = new SimpleGraph<Location, Segment>(Segment.class);

        for(Segment s : scc.getSegments()){
            ug.addVertex(s.getToNode());

            for(int i=0; i<s.getIntermediateNodes().size(); i++){
                ug.addVertex(s.getIntermediateNodes().get(i));
            }

            ug.addVertex(s.getFromNode());
        }

        for(Segment s : scc.getSegments()){
            if(s.getIntermediateNodes().size() == 0){
                ug.addEdge(s.getToNode(), s.getFromNode());
            }
            else if (s.getIntermediateNodes().size() == 1) {
                ug.addEdge(s.getToNode(), s.getIntermediateNodes().get(0));
                ug.addEdge(s.getFromNode(), s.getIntermediateNodes().get(0));
            }
            else{
                ug.addEdge(s.getToNode(), s.getIntermediateNodes().get(0));

                for( int i=0; i<s.getIntermediateNodes().size()-1; i++){
                    ug.addEdge(s.getIntermediateNodes().get(i), s.getIntermediateNodes().get(i+1));
                }

                ug.addEdge(s.getFromNode(), s.getIntermediateNodes().get(s.getIntermediateNodes().size()-1));
            }

        }

        Location s = new Location(10588, "Stillman Hall", new Coordinate(1825399.99612252, 729498.427258271, Coordinate.TYPE.NAD_27));
        Location e = new Location(10591, "Arps Hall", new Coordinate(1825761.98379103,729523.554250369, Coordinate.TYPE.NAD_27));

        List<Segment> l = DijkstraShortestPath.findPathBetween(ug, s, e);

        for(Segment ss : l){
            System.out.println(ss.getToNode().getName() + " - " +  ss.getFromNode().getName());
        }
    }
}
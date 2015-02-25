package com;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utilities.Coordinate;

import java.util.*;

@RestController
public class RouteController {

    @RequestMapping("/generateRoute")
    public Route generateRoute() {

        SegmentController sc = new SegmentController();
        SegmentCollection scc = sc.segments();

        Pseudograph<Node, Segment> ug = new Pseudograph<Node, Segment>(Segment.class);

        for(Segment s : scc.getSegments()){
            ug.addVertex(s.getFromNode());
            ug.addVertex(s.getToNode());
            ug.addEdge(s.getFromNode(), s.getToNode(), s);
        }

        List<Segment> l = DijkstraShortestPath.findPathBetween(ug, scc.getSegments().get(4).getFromNode(), scc.getSegments().get(652).getToNode());

        List<Node> routeNodes = new ArrayList<Node>();

        for(Segment s : l)
        {
            routeNodes.add(s.getFromNode());
            for(Node n : s.getIntermediateNodes())
            {
                routeNodes.add(n);
            }
        }

        return new Route(routeNodes);
    }
}
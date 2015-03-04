package com;

import org.jgrapht.graph.Pseudograph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.Coordinate;

import java.util.*;

@RestController
public class RouteController
{
    static Pseudograph<Node, Segment> ug = null;

    /**
     * Generates a route using Dijkstra Shortest Path algorithm from the origin to the destination
     * provided via the request parameters.
     *
     * @param from The desired origin of the path (as the location's unique id)
     * @param to The desired destination of the path (as the location's unique id)
     * @return A Route of the shortest path.
     */
    @RequestMapping("/generateRoute")
    public Route generateRoute(@RequestParam(value="from")int from, @RequestParam(value="to")int to)
    {
        if (ug == null)
        {
            // Get the segments, as we need them to add them to the graph
            SegmentController sc = new SegmentController();
            SegmentCollection scc = sc.segments();

            ug = new Pseudograph<Node, Segment>(Segment.class);

            for (Segment s : scc.getSegments())
            {
                ug.addVertex(s.getEndNode());
                ug.addVertex(s.getStartNode());
                ug.addEdge(s.getEndNode(), s.getStartNode(), s);
            }
        }

        // Get the locations so that we can loop through them
        LocationController lc = new LocationController();
        LocationCollection lcc = lc.locations();

        // Initialize the start and end locations
        Location startLocation = null;
        Location endLocation = null;

        // Find the start and end location object instances based on the unique IDs given
        for(Location l : lcc.getLocations())
        {
            if(l.getId() == from)
            {
                startLocation = l;
            }
            else if (l.getId() == to)
            {
                endLocation = l;
            }
        }

        // Initialize the start and end nodes
        Node startNode = null;
        Node endNode = null;

        // Find the instances of the start and end nodes in the graph based on the
        // coordinates of the start and end locations.
        for(Node n : ug.vertexSet())
        {
            if(Coordinate.isSamePoint(n.getCoordinate(), startLocation.getCoordinate()))
            {
                startNode = n;
            }
            else if(Coordinate.isSamePoint(n.getCoordinate(), endLocation.getCoordinate()))
            {
                endNode = n;
            }
        }

        // Calculate the shortest path
        List<Segment> shortestPath = DijkstraShortestPath.findPathBetween(ug, startNode, endNode);

        List<Node> routeNodes = new ArrayList<Node>();

        // Add first node
        routeNodes.add(startNode);

        // Foreach segment on the shortest path
        for (Segment s : shortestPath)
        {
            // check to see if the start is the same as the one already in the list
            if(s.getStartNode() == routeNodes.get(routeNodes.size() - 1))
            {
                // and all intermediate nodes in normal order
                for (Node n : s.getIntermediateNodes())
                {
                    routeNodes.add(n);
                }

                // Add end node
                routeNodes.add(s.getEndNode());
            }
            else if (s.getEndNode() == routeNodes.get(routeNodes.size() - 1))
            {
                // Everything has to be added in reverse order.
                ArrayList<Node> reversedIntermediateNodes = new ArrayList<Node>(s.getIntermediateNodes());
                Collections.reverse(reversedIntermediateNodes);

                for(Node n : reversedIntermediateNodes)
                {
                    routeNodes.add(n);
                }

                // Add "start" node (which is really the end node because the segment is backwards)
                routeNodes.add(s.getStartNode());
            }
            else
            {
                throw new AssertionError("Route Not Continuous.");
            }
        }

        return new Route(routeNodes);
    }
}
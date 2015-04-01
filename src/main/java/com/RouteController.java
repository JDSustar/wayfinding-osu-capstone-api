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
    private Location startLocation = null;
    private Location endLocation = null;
    private LocationCollection lcc = null;
    private Node startNode = null;
    private Node endNode = null;
    private List<Segment> shortestPath = null;
    private List<Node> routeNodes = new ArrayList<Node>();
    private double radius = 1.5; // 1.5 feet

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
        // Check to see if the graph has been loaded to the server
        graphLoadCheck();

        // Get the locations so that we can loop through them
        loadLocations();

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

        // Find the start and end nodes in the graph
        findStartEndNodes();

        // Calculate the shortest path
        shortestPath = findShortestPath();

        if(shortestPath != null)
        {
            // Create a list of nodes based on the list of shortest path segments
            createRoute();

            // Return the list of nodes as a route object
            return new Route(routeNodes, startLocation, endLocation);
        }
        else {
            return null;
        }
    }

    /**
     * Generates a route using Dijkstra Shortest Path algorithm from the current location to the
     * destination provided via the request parameters.
     *
     * @param destID The desired destination of the path(as the location's unique id)
     * @param currLat The Latitude value of the current location
     * @param currLong The Longitude value of the current location
     * @return A Route of the shortest path.
     */
    @RequestMapping("/generateRouteCurrent")
    public Route generateRoute(@RequestParam(value="dest")int destID, @RequestParam(value="currlat")double currLat, @RequestParam(value="currlong")double currLong){

        // Check to see if the graph has been loaded to the server
        graphLoadCheck();

        // Get the locations so that we can loop through them
        loadLocations();

        // Find the end location object instance based on the unique ID given
        for(Location l : lcc.getLocations())
        {
            if(l.getId() == destID)
            {
                endLocation = l;
            }
        }

        // Find the start and end nodes in the graph
        findStartEndNodes(new Coordinate(currLat, currLong, Coordinate.TYPE.GCS));

        if(startNode == null)
        {
            return null; // Starting node could not be found within 300 feet of current location. Route not available.
        }

        // Calculate the shortest path
        shortestPath = findShortestPath();

        if(shortestPath != null)
        {
            // Create a list of nodes based on the list of shortest path segments
            createRoute();

            // Return the list of nodes as a route object
            return new Route(routeNodes, new Location(-1, "Current Location", startNode.getCoordinate()), endLocation);
        }
        else {
            return null;
        }
    }

    /**
     * Loads the graph to the server if it has not been loaded already
     */
    private void graphLoadCheck(){
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
    }

    /**
     * Loads the locations to the server
     */
    private void loadLocations(){
        lcc = new LocationController().locations();
    }

    /**
     * Find the instances of the start and end nodes in the graph based on the coordinates of the
     * start and end locations
     */
    private void findStartEndNodes(){
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
    }

    /**
     * Find the instances of the start and end nodes in the graph based on the coordinates of the
     * start and end locations
     * @param currentLocation Start Location
     */
    private void findStartEndNodes(Coordinate currentLocation){
        for(Node n : ug.vertexSet())
        {
            if(Coordinate.isSamePoint(n.getCoordinate(), currentLocation))
            {
                startNode = n;
            }
            else if(Coordinate.isSamePoint(n.getCoordinate(), endLocation.getCoordinate()))
            {
                endNode = n;
            }
        }

        if(startNode == null){
            findClosestNode(currentLocation);
        }
    }

    /**
     * Find the shortest path
     * @return A list of segments of the shortest path
     */
    private List<Segment> findShortestPath(){
        return DijkstraShortestPath.findPathBetween(ug, startNode, endNode);
    }

    /**
     * Creates the shortest path route as a list of nodes that is to be returned to the application
     */
    private void createRoute(){
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
    }

    private void findClosestNode(Coordinate currentLocation){
        double EPSILON = radius;

        while(startNode == null) {
            for (Node n : ug.vertexSet()) {
                if (Coordinate.distance(n.getCoordinate(), currentLocation) < EPSILON) {
                    startNode = n;
                    break;
                }
            }

            if(startNode == null){
                EPSILON+=radius;
            }

            if(EPSILON > 300)
            {
                break;
            }
        }
    }
}
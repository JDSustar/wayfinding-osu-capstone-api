package com;

import org.jgrapht.graph.Pseudograph;
import org.jgrapht.alg.DijkstraShortestPath;
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
    private double radius = 0.0002840909; // 1.5 feet

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

        // Create a list of nodes based on the list of shortest path segments
        createRoute();

        // Return the list of nodes as a route object
        return new Route(routeNodes);
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
    @RequestMapping("/generateRoutecurrent")
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

        // Calculate the shortest path
        shortestPath = findShortestPath();

        // Create a list of nodes based on the list of shortest path segments
        createRoute();

        // Return the list of nodes as a route object
        return new Route(routeNodes);
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
        // Foreach segment on the shortest path
        for (Segment s : shortestPath)
        {
            // add the first node
            routeNodes.add(s.getStartNode());

            // and all intermediate nodes
            for (Node n : s.getIntermediateNodes())
            {
                routeNodes.add(n);
            }

            // But do not add the final node, because it will be the same as the starting node of the next segment
        }

        // Add the final node only from the last segment
        routeNodes.add(shortestPath.get(shortestPath.size() - 1).getEndNode());
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
        }
    }
}
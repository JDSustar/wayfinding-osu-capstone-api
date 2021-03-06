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
    private static Pseudograph<Node, Segment> wheelchairGraph = null;
    private BuildingCollection bcc = null;
    private double radius = 1.5; // 1.5 feet

    /**
     * Generates a route using Dijkstra Shortest Path algorithm from the origin to the destination
     * provided via the request parameters.
     *
     * @param from The desired origin of the path (as the location's unique id)
     * @param to   The desired destination of the path (as the location's unique id)
     * @return A Route of the shortest path.
     */
    @RequestMapping("/generateRoute")
    public Route generateRoute(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to, @RequestParam(value = "wheelchair", defaultValue = "false") boolean wheelchair)
    {
        // Check to see if the graph has been loaded to the server
        graphLoadCheck();

        // Get the locations so that we can loop through them
        loadBuildings();

        Building startBuilding = bcc.getBuilding(from);
        Building endBuilding = bcc.getBuilding(to);

        // Find the start and end nodes in the graph
        Route bestRoute = null;

        for (int startIndex = 0; startIndex < startBuilding.getDoors().size(); startIndex++)
        {
            for (int endIndex = 0; endIndex < endBuilding.getDoors().size(); endIndex++)
            {
                Door startDoor = startBuilding.getDoors().get(startIndex);
                Door endDoor = endBuilding.getDoors().get(endIndex);

                Node startNode = findNodeForDoor(startDoor, wheelchair);
                Node endNode = findNodeForDoor(endDoor, wheelchair);

                if (startNode == null || endNode == null)
                {
                    continue;
                }

                // Calculate the shortest path
                List<Segment> shortestPath = findShortestPath(startNode, endNode, wheelchair);
                if (shortestPath != null)
                {
                    if (shortestPath == null)
                    {
                        continue;
                    }

                    List<Coordinate> routeCoordinates = createRouteCoordinates(shortestPath, startNode);
                    Route currentRoute = new Route(routeCoordinates, startDoor, endDoor);

                    if (bestRoute == null || currentRoute.getLengthInFeet() < bestRoute.getLengthInFeet())
                    {
                        bestRoute = currentRoute;
                    }
                }
            }
        }
        if (bestRoute == null)
        {
            return new Route(null, null, null, "No route exists between " + startBuilding.getName() + " and " + endBuilding.getName());
        }
        return bestRoute;
    }

    /**
     * Generates a route using Dijkstra Shortest Path algorithm from the current location to the
     * destination provided via the request parameters.
     *
     * @param destID   The desired destination of the path(as the location's unique id)
     * @param currLat  The Latitude value of the current location
     * @param currLong The Longitude value of the current location
     * @return A Route of the shortest path.
     */
    @RequestMapping("/generateRouteCurrent")
    public Route generateRoute(@RequestParam(value = "dest") int destID, @RequestParam(value = "currlat") double currLat, @RequestParam(value = "currlong") double currLong, @RequestParam(value = "wheelchair", defaultValue = "false") boolean wheelchair)
    {
        // Check to see if the graph has been loaded to the server
        graphLoadCheck();

        // Get the locations so that we can loop through them
        loadBuildings();

        // Find the end location object instance based on the unique ID given
        Building endBuilding = bcc.getBuilding(destID);

        // Find the start and end nodes in the graph
        Node startNode = findClosestNode(new Coordinate(currLat, currLong, Coordinate.TYPE.GCS), wheelchair);

        if (startNode == null)
        {
            return new Route(null, null, null, "You are not close enough to paths on campus to calculate a route using your current location."); // Starting node could not be found within 300 feet of current location. Route not available.
        }

        Route bestRoute = null;

        for (int endIndex = 0; endIndex < endBuilding.getDoors().size(); endIndex++)
        {
            Door endDoor = endBuilding.getDoors().get(endIndex);
            Node endNode = findNodeForDoor(endDoor, wheelchair);

            if (endNode == null)
            {
                continue;
            }

            List<Segment> shortestPath = findShortestPath(startNode, endNode, wheelchair);

            if (shortestPath == null)
            {
                continue;
            }

            List<Coordinate> routeCoordinates = createRouteCoordinates(shortestPath, startNode);
            Route currentRoute = new Route(routeCoordinates, new Door(-1, "Current Location", new Coordinate(currLat, currLong, Coordinate.TYPE.GCS)), endDoor);

            if (bestRoute == null || currentRoute.getLengthInFeet() < bestRoute.getLengthInFeet())
            {
                bestRoute = currentRoute;
            }
        }
        if (bestRoute == null)
        {
            return new Route(null, null, null, "No route exists between your current location and " + endBuilding.getName() + ".");
        }
        return bestRoute;
    }

    /**
     * Loads the graph to the server if it has not been loaded already
     */
    private void graphLoadCheck()
    {
        if (ug == null || wheelchairGraph == null)
        {
            // Get the segments, as we need them to add them to the graph
            SegmentController sc = new SegmentController();
            SegmentCollection scc = sc.segments();

            ug = new Pseudograph<Node, Segment>(Segment.class);
            wheelchairGraph = new Pseudograph<Node, Segment>(Segment.class);

            for (Segment s : scc.getSegments())
            {
                ug.addVertex(s.getEndNode());
                ug.addVertex(s.getStartNode());
                ug.addEdge(s.getEndNode(), s.getStartNode(), s);

                if (s.getAccessible() == 1)
                {
                    wheelchairGraph.addVertex(s.getEndNode());
                    wheelchairGraph.addVertex(s.getStartNode());
                    wheelchairGraph.addEdge(s.getEndNode(), s.getStartNode(), s);
                }
            }
        }
    }

    /**
     * Loads the locations to the server
     */
    private void loadBuildings()
    {
        bcc = new BuildingController().buildings();
    }

    /**
     * Find the instances of the start and end nodes in the graph based on the coordinates of the
     * start and end locations
     */
    private Node findNodeForDoor(Door door, boolean wheelchairNode)
    {
        if (wheelchairNode)
        {
            for (Node n : wheelchairGraph.vertexSet())
            {
                if (Coordinate.isSamePoint(n.getCoordinate(), door.getCoordinate()))
                {
                    return n;
                }
            }
        } else
        {
            for (Node n : ug.vertexSet())
            {
                if (Coordinate.isSamePoint(n.getCoordinate(), door.getCoordinate()))
                {
                    return n;
                }
            }
        }

        return null;
    }

    /**
     * Find the shortest path
     *
     * @return A list of segments of the shortest path
     */
    private List<Segment> findShortestPath(Node startNode, Node endNode, boolean wheelchairAccessibleRoute)
    {
        if (wheelchairAccessibleRoute)
        {
            return DijkstraShortestPath.findPathBetween(wheelchairGraph, startNode, endNode);
        } else
        {
            return DijkstraShortestPath.findPathBetween(ug, startNode, endNode);
        }
    }

    /**
     * Creates the shortest path route as a list of nodes that is to be returned to the application
     */
    private List<Coordinate> createRouteCoordinates(List<Segment> path, Node startNode)
    {
        List<Coordinate> routeCoordinates = new ArrayList<Coordinate>();

        routeCoordinates.add(startNode.getCoordinate());

        // Foreach segment on the shortest path
        for (Segment s : path)
        {
            // check to see if the start is the same as the one already in the list
            if (s.getStartNode().getCoordinate() == routeCoordinates.get(routeCoordinates.size() - 1))
            {
                // and all intermediate nodes in normal order
                for (Node n : s.getIntermediateNodes())
                {
                    routeCoordinates.add(n.getCoordinate());
                }

                // Add end node
                routeCoordinates.add(s.getEndNode().getCoordinate());
            } else if (s.getEndNode().getCoordinate() == routeCoordinates.get(routeCoordinates.size() - 1))
            {
                // Everything has to be added in reverse order.
                ArrayList<Node> reversedIntermediateNodes = new ArrayList<Node>(s.getIntermediateNodes());
                Collections.reverse(reversedIntermediateNodes);

                for (Node n : reversedIntermediateNodes)
                {
                    routeCoordinates.add(n.getCoordinate());
                }

                // Add "start" node (which is really the end node because the segment is backwards)
                routeCoordinates.add(s.getStartNode().getCoordinate());
            } else
            {
                throw new AssertionError("Route Not Continuous.");
            }
        }

        return routeCoordinates;
    }

    private Node findClosestNode(Coordinate currentLocation, boolean wheelchairNode)
    {
        Node closestNode = null;

        double EPSILON = radius;

        while (closestNode == null)
        {
            if (wheelchairNode)
            {
                for (Node n : wheelchairGraph.vertexSet())
                {
                    if (Coordinate.distance(n.getCoordinate(), currentLocation) < EPSILON)
                    {
                        closestNode = n;
                        break;
                    }
                }
            } else
            {
                for (Node n : ug.vertexSet())
                {
                    if (Coordinate.distance(n.getCoordinate(), currentLocation) < EPSILON)
                    {
                        closestNode = n;
                        break;
                    }
                }
            }

            if (closestNode == null)
            {
                EPSILON += radius;
            }

            if (EPSILON > 300)
            {
                break;
            }
        }

        return closestNode;
    }
}
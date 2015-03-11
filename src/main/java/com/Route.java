package com;

import utilities.Coordinate;

import java.util.List;

public class Route {
    private Location startLocation;
    private Location endLocation;
    private double lengthInFeet;
    private final List<Node> route;

    public Route(List<Node> route, Location start, Location end){
        this.route = route;
        startLocation = start;
        endLocation = end;

        double length = 0;
        for(int i = 0; i < route.size() - 1; i++)
        {
            length += Coordinate.distance(route.get(i).getCoordinate(), route.get(i+1).getCoordinate());
        }

        lengthInFeet = length;
    }

    public List<Node> getRoute(){
        return route;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public double getLengthInFeet() {
        return lengthInFeet;
    }
}

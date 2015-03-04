package com;

import java.util.List;

public class Route {
    private Location startLocation;
    private Location endLocation;
    private double lengthInFeet;
    private final List<Node> route;

    public Route(List<Node> route, Location start, Location end){
        this.route = route;

        double length = 0;
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

package com;

import utilities.Coordinate;

import java.util.List;

public class Route {
    private Door startDoor;
    private Door endDoor;
    private double lengthInFeet;
    private final List<Coordinate> route;

    public Route(List<Coordinate> route, Door start, Door end){
        this.route = route;
        startDoor = start;
        endDoor = end;

        double length = 0;
        for(int i = 0; i < route.size() - 1; i++)
        {
            length += Coordinate.distance(route.get(i), route.get(i + 1));
        }

        lengthInFeet = length;
    }

    public List<Coordinate> getRoute(){
        return route;
    }

    public Door getStartDoor() {
        return startDoor;
    }

    public Door getEndDoor() {
        return endDoor;
    }

    public double getLengthInFeet() {
        return lengthInFeet;
    }
}

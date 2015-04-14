package com;

import utilities.Coordinate;

import java.util.List;

public class Route {
    private Door startDoor;
    private Door endDoor;
    private double lengthInFeet;
    private final List<Node> route;
    private String errorMsg;

    public Route(List<Node> route, Door start, Door end, String errorMsg){
        this.route = route;
        startDoor = start;
        endDoor = end;

        double length = 0;
        if (route != null) {
            for (int i = 0; i < route.size() - 1; i++) {
                length += Coordinate.distance(route.get(i).getCoordinate(), route.get(i + 1).getCoordinate());
            }
        }
        lengthInFeet = length;
        this.errorMsg = errorMsg;
    }

    public List<Node> getRoute(){
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

    public String getErrorMsg() { return errorMsg; }

}

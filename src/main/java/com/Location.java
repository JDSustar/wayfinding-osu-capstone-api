package com;

import utilities.Coordinate;

public class Location{
    private final int id;
    private final String name;
    private int distanceFromStart;
    private boolean visited;
    private final Coordinate coordinate;

    public Location(int id, String name, Coordinate coordinate) {
        this.id = id;
        this.name = name;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.visited = false;
        this.coordinate = coordinate;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public String getName()
    {
        return name;
    }

    public int getId() {return id;}

    public boolean isVisited()
    {
        return this.visited;
    }

    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }

    public Coordinate getCoordinate(){ return this.coordinate; }

}
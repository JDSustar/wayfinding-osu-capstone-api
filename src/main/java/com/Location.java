package com;

import java.util.ArrayList;

public class Location{
    private final int id;
    private final String name;
    private int distanceFromStart;
    private boolean visited;

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.visited = false;
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

    public ArrayList<Segment> getEdges() {
        return getEdges();
    }
}
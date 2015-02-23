package com;

public class Location{
    private final int id;
    private final String name;
    private int distanceFromStart;
    private boolean visited;
    private final double latitude;
    private final double longitude;

    public Location(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.visited = false;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude(){ return latitude; }

    public double getLongitude() {  return longitude; }

}
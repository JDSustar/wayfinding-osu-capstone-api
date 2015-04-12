package com;

import utilities.Coordinate;

public class Door extends Node{
    private String name;

    private int id;

    public Door(int id, String name, Coordinate coord)
    {
        super(coord);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Location: " + this.name + " -- Lat: " + this.getCoordinate().getLatitude() + " Long: " + this.getCoordinate().getLongitude();
    }
}
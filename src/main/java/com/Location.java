package com;

import utilities.Coordinate;

public class Location extends Node{
    private String name;

    private int id;

    public Location(int id, String name, Coordinate coord)
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
}
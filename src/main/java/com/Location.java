package com;

public class Location{
    private final int id;
    private final String name;

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public int getId() {return id;}
}
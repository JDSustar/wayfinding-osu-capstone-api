package com;

public class Location{
    private final int id;
    private final String name;
    private final double spcx;
    private final double spcy;

    public Location(int id, String name, double spcx, double spcy) {
        this.id = id;
        this.name = name;
        this.spcx = spcx;
        this.spcy = spcy;
    }

    public String getName()
    {
        return name;
    }

    public int getId() {return id;}
}
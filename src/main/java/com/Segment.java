package com;

public class Segment {
    private final Location loc1;
    private final Location loc2;

    public Segment(Location loc1, Location loc2){
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public Location getLocation1(){
        return loc1;
    }

    public Location getLocation2(){
        return loc2;
    }
}

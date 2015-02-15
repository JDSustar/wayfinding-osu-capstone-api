package com;

import java.util.List;

/**
 * Created by Jim on 2/13/2015.
 */
public class Segment {
    private final int weight;
    private final int accessible;
    private final String streetCrossing;
    private final String description;
    private final String hazard;
    private final Location toNode = new Location(-1,"NOTHING");
    private final Location fromNode = new Location(-1,"NOTHING");


    public Segment(int weight, int accessible, String streetCrossing, String description,
        String hazard) {
        this.weight = weight;
        this.accessible = accessible;
        this.streetCrossing = streetCrossing;
        this.description = description;
        this.hazard = hazard;
    }

    public String getStreetCrossing()
    {
        return streetCrossing;
    }

    public String getDescription()
    {
        return description;
    }

    public String getHazard()
    {
        return hazard;
    }

    public int getWeight() {return weight;}

    public int getAccessible() {return accessible;}

    public int getNeighbourIndex(int nodeIndex) {
        if (this.toNode.getId() == nodeIndex) {
            return this.toNode.getId();
        } else {
            return this.fromNode.getId();
        }
    }

}

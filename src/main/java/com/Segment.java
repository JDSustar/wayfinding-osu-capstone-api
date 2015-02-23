package com;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Jim on 2/13/2015.
 */
public class Segment {
//    private final int weight;
//    private final int accessible;
//    private final String streetCrossing;
//    private final String description;
//    private final String hazard;
    private final Location startNode;
    private final Location endNode;
//    private final List<Location> intermediateNodes;

    public Segment(Location l1, Location l2){
        this.startNode = l1;
        this.endNode = l2;
    }

//    public Segment(int weight, int accessible, String streetCrossing, String description,
//        String hazard, Location startNode, Location endNode, List<Location> intermediateNodes) {
//        this.weight = weight;
//        this.accessible = accessible;
//        this.streetCrossing = streetCrossing;
//        this.description = description;
//        this.hazard = hazard;
//        this.startNode = startNode;
//        this.endNode = endNode;
//        this.intermediateNodes = intermediateNodes;
//    }
//
//    public String getStreetCrossing()
//    {
//        return streetCrossing;
//    }
//
//    public String getDescription()
//    {
//        return description;
//    }
//
//    public String getHazard()
//    {
//        return hazard;
//    }
//
//    public int getWeight() {return weight;}
//
//    public int getAccessible() {return accessible;}

    public Location getToNode()
    {
        return startNode;
    }

    public Location getFromNode()
    {
        return endNode;
    }

//    public int getNeighbourIndex(int nodeIndex) {
//        if (this.startNode.getId() == nodeIndex) {
//            return this.startNode.getId();
//        } else {
//            return this.endNode.getId();
//        }
//    }

}

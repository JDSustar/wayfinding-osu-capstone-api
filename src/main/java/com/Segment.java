package com;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Jim on 2/13/2015.
 */
public class Segment {
    private int weight;
    private int accessible;
    private String streetCrossing;
    private String description;
    private String hazard;
    private Node startNode;
    private Node endNode;
    private List<Node> intermediateNodes;

    public Segment(int weight, int accessible, String streetCrossing, String description,
        String hazard, Node startNode, Node endNode, List<Node> intermediateNodes) {
        this.weight = weight;
        this.accessible = accessible;
        this.streetCrossing = streetCrossing;
        this.description = description;
        this.hazard = hazard;
        this.startNode = startNode;
        this.endNode = endNode;
        this.intermediateNodes = intermediateNodes;
    }

    public Segment(int weight, int accessible, String streetCrossing, String description,
                   String hazard, Node startNode, Node endNode) {
        this.weight = weight;
        this.accessible = accessible;
        this.streetCrossing = streetCrossing;
        this.description = description;
        this.hazard = hazard;
        this.startNode = startNode;
        this.endNode = endNode;
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

    public Node getToNode()
    {
        return startNode;
    }

    public Node getFromNode()
    {
        return endNode;
    }

    public List<Node> getIntermediateNodes() { return intermediateNodes; }

}

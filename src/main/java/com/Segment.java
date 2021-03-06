package com;

import utilities.Coordinate;

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
    private double lengthInFeet;

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

        calculateLength();
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

        calculateLength();
    }

    public String getStreetCrossing() {
        return streetCrossing;
    }

    public String getDescription() {
        return description;
    }

    public String getHazard() {
        return hazard;
    }

    public int getWeight() {
        return weight;
    }

    public int getAccessible() {
        return accessible;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public List<Node> getIntermediateNodes() {
        return intermediateNodes;
    }

    public double getLengthInFeet() {
        return lengthInFeet;
    }

    private void calculateLength() {
        if (intermediateNodes == null || intermediateNodes.size() == 0) {
            lengthInFeet = Coordinate.distance(startNode.getCoordinate(), endNode.getCoordinate());
        } else {
            double length = 0;
            length += Coordinate.distance(startNode.getCoordinate(), intermediateNodes.get(0).getCoordinate());

            for (int i = 1; i < intermediateNodes.size(); i++) {
                length += Coordinate.distance(intermediateNodes.get(i - 1).getCoordinate(), intermediateNodes.get(i).getCoordinate());
            }

            length += Coordinate.distance(intermediateNodes.get(intermediateNodes.size() - 1).getCoordinate(), endNode.getCoordinate());

            lengthInFeet = length;
        }
    }
}

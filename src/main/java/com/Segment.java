package com;

import java.util.*;

public class Segment {
    private int id;
    private double distance;
    private Location Node1;
    private Location Node2;

    public Segment(int id, Location Node1, Location Node2) {
        this.id = id;
        this.Node1 = Node1;
        this.Node2 = Node2;
        distance = Math.sqrt(Math.pow(Node2.getSpcx() - Node1.getSpcx(), 2) + Math.pow(Node2.getSpcy() - Node1.getSpcy(), 2));
        System.out.println("distance: " + distance);
    }

    public int getId() { return id; }

    public Location getNode1()
    {
        return Node1;
    }

    public Location getNode2()
    {
        return Node2;
    }

}

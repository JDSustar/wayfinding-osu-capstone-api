package com;

import java.util.*;

public class Segment {
    private int id;
    private Location Node1;
    private Location Node2;


    public Segment(int id, Location Node1, Location Node2) {
        this.id = id;
        this.Node1 = Node1;
        this.Node2 = Node2;
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

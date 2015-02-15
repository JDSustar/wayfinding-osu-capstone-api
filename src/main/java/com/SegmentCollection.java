package com;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 2/13/2015.
 */
public class SegmentCollection {
    private final List<Segment> edges;

    public SegmentCollection()
    {
        edges = new ArrayList<Segment>();
    }

    public SegmentCollection(List<Segment> edges){
        this.edges = edges;
    }

    public List<Segment> getEdges(){
        return edges;
    }
}

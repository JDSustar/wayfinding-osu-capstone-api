package com;

import java.util.*;

public class SegmentCollection {
    private final List<Segment> segments;

    public SegmentCollection()
    {
        segments = new ArrayList<Segment>();
    }

    public SegmentCollection(List<Segment> segments){
        this.segments = segments;
    }

    public List<Segment> getSegments(){
        return segments;
    }

}

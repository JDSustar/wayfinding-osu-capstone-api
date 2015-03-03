package com;

import java.util.ArrayList;
import java.util.List;

public class SegmentCollection {
    private static List<Segment> segments;

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

    public void add(int i, Segment s){
        if(!segments.contains(s)){
            segments.add(i, s);
        }
    }
}

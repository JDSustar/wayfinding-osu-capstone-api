package com;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 2/13/2015.
 */
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
}

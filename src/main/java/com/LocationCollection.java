package com;

import java.util.*;

public class LocationCollection
{
    private List<Location> locations;

    public LocationCollection()
    {
        locations = new ArrayList<Location>();
    }

    public LocationCollection(List<Location> buildings)
    {
        this.locations = buildings;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void add(int i, Location l){
        if(!locations.contains(l)){
            locations.add(i, l);
        }
    }
}
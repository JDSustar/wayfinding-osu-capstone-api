
package com;


import java.util.*;


public class LocationCollection
{
    public List<Location> locations;

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
}
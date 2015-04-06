package com;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasforte on 3/31/15.
 */
public class Building {

    private String name;
    private int id;
    private int buildingId;
    private List<Location> doors;

    public Building (List<Location> doors, String name, int id, int buildingId) {
        this.name = name;
        this.id = id;
        this.doors = doors;
        this.buildingId = buildingId;
    }

    public String getName () { return this.name; }

    public List<Location> getDoors () { return this.doors; }

    public int getId () { return this.id; }

    public String toString () { return this.name; }

    public int getBuildingId () { return this.buildingId; }

}


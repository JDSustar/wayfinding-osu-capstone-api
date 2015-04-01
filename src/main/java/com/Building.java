package com;

import java.util.ArrayList;

/**
 * Created by thomasforte on 3/31/15.
 */
public class Building {

    private String name;
    private int id;
    private final ArrayList<Location> doors;

    public Building (ArrayList<Location> doors, String name, int id) {
        this.name = name;
        this.id = id;
        this.doors = doors;
    }

    public String getName () { return this.name; }

    public int getId () { return this.id; }

    public String toString () { return this.name; }

}


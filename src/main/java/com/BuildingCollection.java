package com;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasforte on 3/31/15.
 */
public class BuildingCollection {
    private List<Building> buildings;

    public BuildingCollection()
    {
        buildings = new ArrayList<Building>();
    }

    public BuildingCollection(List<Building> buildings)
    {
        this.buildings = buildings;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void add(int i, Building b){
        if(!buildings.contains(b)){
            buildings.add(i, b);
        }
    }

    public Building getBuilding(int id)
    {
        for(Building b : buildings)
        {
            if(b.getId() == id)
            {
                return b;
            }
        }

        return null;
    }
}

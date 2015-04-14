package com;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utilities.Coordinate;

import java.util.List;

/**
 * Created by JasonDesktop on 4/13/2015.
 */
public class Tour
{
    private String _name;
    private int _id;
    private Route _route;

    public Tour(String name, int id, Route route)
    {
        this._name = name;
        this._id = id;
        this._route = route;
    }

    public String getName()
    {
        return _name;
    }

    public int getId()
    {
        return _id;
    }

    @JsonIgnore
    public Route getRoute() { return _route; }
}

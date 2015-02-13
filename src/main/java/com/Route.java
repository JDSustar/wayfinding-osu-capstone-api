package com;

import java.util.List;

public class Route {
    private final List<Location> route;

    public Route(List<Location> route){
        this.route = route;
    }

    public List<Location> getRoute(){
        return route;
    }

}

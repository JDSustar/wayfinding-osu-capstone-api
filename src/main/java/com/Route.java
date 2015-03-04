package com;

import java.util.List;

public class Route {
    private final List<Node> route;

    public Route(List<Node> route){
        this.route = route;
    }

    public List<Node> getRoute(){
        return route;
    }

}

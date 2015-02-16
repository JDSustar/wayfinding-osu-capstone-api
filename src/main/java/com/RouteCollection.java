package com;

import java.util.*;

public class RouteCollection {
    private List<Route> routes;

    public RouteCollection() {
        routes = new ArrayList<Route>();
    }

    public RouteCollection(List<Route> routes){
        this.routes = routes;
    }

    public List<Route> getRoutes(){
        return routes;
    }
}

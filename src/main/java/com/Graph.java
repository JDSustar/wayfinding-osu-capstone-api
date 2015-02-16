package com;

public class Graph {
    private Location[] nodes;
    private int noOfNodes;
    private Segment[] edges;
    private int noOfEdges;

    public Location[] getNodes() {
        return nodes;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public Segment[] getEdges() {
        return edges;
    }

    public int getNoOfEdges() {
        return noOfEdges;
    }

    public Graph (Segment[] edges)
    {
        this.edges = edges;
        this.noOfNodes = calculateNoOfNodes(edges);
        this.nodes = getNodes();

        /*for (int n = 0; n < this.noOfNodes; n++) {
            this.nodes[n] = new Location();
        }*/

        // Add all the edges to the nodes. Each edge is added to 2 nodes (the "to" and the "from")
        this.noOfEdges = edges.length;
        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
            //this.nodes[edges[edgeToAdd].getNode1().getId()].getEdges().add(edges[edgeToAdd]);
//            this.nodes[edges[edgeToAdd].getNode2().getId()].getEdges().add(edges[edgeToAdd]);
        }
    }

    private int calculateNoOfNodes(Segment[] edges) {
        int noOfNodes = 0;
        for (Segment e:edges ) {
            if (e.getNode1().getId() > noOfNodes) noOfNodes = e.getNode1().getId();
//            if (e.getNode2().getId() > noOfNodes) noOfNodes = e.getNode2().getId();
        }
        noOfNodes++;
        return noOfNodes;
    }
}

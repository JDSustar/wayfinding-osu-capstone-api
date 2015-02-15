package com;

/**
 * Created by Jim on 2/15/2015.
 */
public class Graph {
    private Location[] nodes;

    public Location[] getNodes() {
        return nodes;
    }

    private int noOfNodes;

    public int getNoOfNodes() {
        return noOfNodes;
    }

    private Segment[] edges;

    public Segment[] getEdges() {
        return edges;
    }

    private int noOfEdges;

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
            this.nodes[edges[edgeToAdd].getFromNode().getId()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNode().getId()].getEdges().add(edges[edgeToAdd]);
        }
    }

    private int calculateNoOfNodes(Segment[] edges) {
        int noOfNodes = 0;
        for (Segment e:edges ) {
            if (e.getToNode().getId() > noOfNodes) noOfNodes = e.getToNode().getId();
            if (e.getFromNode().getId() > noOfNodes) noOfNodes = e.getFromNode().getId();
        }
        noOfNodes++;
        return noOfNodes;
    }
}

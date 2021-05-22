package main.java.impl;

import engine.interfaces.IEdge;
import engine.interfaces.INode;

public class GraphEdge implements IEdge {
    private INode start;
    private INode end;
    private int weight;
    private String label;

    public GraphEdge(INode start, INode end, int weight, String label) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.label = label;
    }

    public GraphEdge(IEdge other){
        this.start = new GraphNode(other.getStart());
        this.end = new GraphNode(other.getEnd());
        this.weight = other.getWeight();
        this.label = new String(other.getLabel());
    }

    public INode getStart() {
        return start;
    }

    public void setStart(INode start) {
        this.start = start;
    }

    public INode getEnd() {
        return end;
    }

    public void setEnd(INode end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "GraphEdge{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                ", label='" + label + '\'' +
                '}';
    }
}

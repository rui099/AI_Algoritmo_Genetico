package main.java.impl;

import engine.enums.NodeType;
import engine.interfaces.INode;

public class GraphNode implements INode{
    NodeType type;
    int label;
    int x;
    int y;

    public GraphNode(NodeType type, int label, int x, int y) {
        this.type = type;
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public GraphNode(INode other){
        this.type = other.getType();
        this.label = other.getLabel();
        this.x = other.getX();
        this.y = other.getY();
    }


    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "type=" + type +
                ", label='" + label + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

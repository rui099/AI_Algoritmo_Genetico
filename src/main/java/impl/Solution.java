package impl;

import engine.*;
import engine.enums.NodeType;
import engine.exceptions.ElementNotFoundException;
import engine.interfaces.IEdge;
import engine.interfaces.INode;
import engine.interfaces.ISolution;
import engine.results.Result;
import org.miv.pherd.geom.Point2;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;

public class Solution implements ISolution
{
    private VirusConfiguration virusConfiguration;
    private ArrayList<INode> nodes;
    private ArrayList<IEdge> edges;


    public Solution(ArrayList<INode> nodes, ArrayList<IEdge> edges, VirusConfiguration virusConfiguration) {
        this.nodes = nodes;
        this.edges = edges;
        this.virusConfiguration = virusConfiguration;
    }

    public ArrayList<INode> getNodes() {
        return this.nodes;
    }

    public void setNodes(ArrayList<INode> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<IEdge> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<IEdge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "configuration=" + virusConfiguration+
                ", \nnodes=" + nodes.stream().map(x -> x.toString()).reduce((x,y) -> x + ", \n\t"+y) +
                ", \nedges=" + edges.stream().map(x -> x.toString()).reduce((x,y) -> x + ", \n\t"+y) +
                '}';
    }

    public VirusConfiguration getVirusConfiguration() {
        return virusConfiguration;
    }

    public void setVirusConfiguration(VirusConfiguration virusConfiguration) {
        this.virusConfiguration = virusConfiguration;
    }
}

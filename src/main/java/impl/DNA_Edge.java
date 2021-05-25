package main.java.impl;

import engine.Engine;
import engine.exceptions.VisualizationNotFoundException;
import engine.interfaces.IEdge;
import engine.interfaces.INode;
import engine.interfaces.ISolution;
import engine.results.EdgeResult;
import engine.results.NodeResult;
import engine.results.Result;
import main.Exceptions.XYNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class DNA_Edge implements Comparable<DNA_Edge> {

    private ArrayList<INode> nodelist;
    private ArrayList<IEdge> edgeList;
    private double fitness;
    private Engine eng;

    public DNA_Edge(double percentagem,ArrayList<INode> nodelist, Engine eng) {
        this.nodelist = nodelist;
        this.edgeList = new ArrayList<>();
        this.eng = eng;
        DNA(percentagem);
    }

    public DNA_Edge(ArrayList<INode> nodelist, ArrayList<IEdge> edgeList, Engine eng) {
        this.nodelist = nodelist;
        this.edgeList = edgeList;
        this.eng = eng;
    }

    public ArrayList<INode> getNodelist() {
        return nodelist;
    }

    public void setNodelist(ArrayList<INode> nodelist) {
        this.nodelist = nodelist;
    }

    public ArrayList<IEdge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(ArrayList<IEdge> edgeList) {
        this.edgeList = edgeList;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    private int randInt(int upperbound) {
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }

    private INode randNode (){
        INode node =  nodelist.get(randInt(nodelist.size()));
        return node;
    }

    public int ranWeight(){
        Random rand = new Random();
        int int_random = rand.nextInt(3) +1;
        return int_random;
    }

    public void DNA(double percentagem){
        int nNodes = getNodelist().size();
        //int nEdges = eng.getVirusConfiguration().getN_edges();

        int i =0;
        double numMaxEdges = ((nNodes*(nNodes-1))/2) + nNodes;
        int numDeElem = (int) (percentagem * numMaxEdges);

        while(getEdgeList().size() < numDeElem){

            INode nodeStart = randNode();
            INode nodeEnd = randNode();
            if(!containsEdge(nodeStart,nodeEnd)) {
                i++;
                IEdge edge = new GraphEdge(nodeStart, nodeEnd, ranWeight(), String.valueOf(i));
                getEdgeList().add(edge);
            }
        }
    }

    public boolean containsEdge(INode node, INode node2){
        boolean contains = false;
        Iterator iterator = edgeList.iterator();
        while (iterator.hasNext()) {
            IEdge edge = (IEdge) iterator.next();
            if(edge.getStart().getLabel() == node.getLabel() && edge.getEnd().getLabel() == node2.getLabel() ||
                    edge.getStart().getLabel() == node2.getLabel() && edge.getEnd().getLabel() == node.getLabel())
                contains = true;
        }
        return contains;
    }

    private Result getResultToTest() {
        ISolution solutionNodes = new impl.Solution(getNodelist(), getEdgeList(), eng.getVirusConfiguration());
        eng.testSolution(solutionNodes);
        Result result = eng.testSolution(solutionNodes);
        return result;
    }

    public void calcEdgeNodeFitness() {
        Result result = getResultToTest();

        double count_ok = 0;

        for (int i = 0; i < getEdgeList().size(); i++) {
            EdgeResult nodeResult = result.getEdgeResults().get(i);
            if (nodeResult.isBoth_nodes_ok()){
                count_ok = count_ok + 0.01;;
            }
        }
        setFitness(count_ok);
    }

    public void mutateEdgeNodes(double mutationFreq) {

        for (int i = 0; i < getEdgeList().size(); i++) {
            double ranDouble = randomDouble();
            if (ranDouble < mutationFreq) {
                INode nodeStart = randNode();
                INode nodeEnd = randNode();
                if (!containsEdge(nodeStart, nodeEnd)) {
                    IEdge edge = new GraphEdge(nodeStart, nodeEnd, ranWeight(), getEdgeList().get(i).getLabel());
                    getEdgeList().set(i, edge);
                }
            }
        }
    }

    public double randomDouble() {
        Random rn = new Random();
        double result = rn.nextDouble() * 1;
        return result;
    }

    public ArrayList<IEdge> cloneList() {
        ArrayList<IEdge> novaLista = new ArrayList<>();
        Iterator iterator = getEdgeList().iterator();
        while (iterator.hasNext()) {
            IEdge edge = (GraphEdge) iterator.next();
            novaLista.add(new GraphEdge(edge.getStart(), edge.getEnd(), edge.getWeight(), edge.getLabel()));
        }
        return novaLista;
    }

    public void updateVisualization() throws VisualizationNotFoundException {
        ISolution solutionNodes = new impl.Solution(getNodelist(), getEdgeList(), eng.getVirusConfiguration());
        eng.updateVisualization("viz1", solutionNodes);
    }

    public DNA_Edge crossOver(DNA_Edge outroDna){
        double randD = randomDouble();
        DNA_Edge newDna = new DNA_Edge(getNodelist(),cloneList(),eng);

        for(int i = 0 ; i < getEdgeList().size() ; i++){
            if(randD > 0.5) {
                INode edgeStart = outroDna.edgeList.get(i).getStart();
                INode edgeEnd = outroDna.edgeList.get(i).getEnd();
                if (!newDna.containsEdge(edgeStart, edgeEnd)) {
                    newDna.getEdgeList().set(i, new GraphEdge(outroDna.edgeList.get(i)));
                }
            }
        }
        return newDna;
    }

    public void removeUselessEdgeNodes() {
        Result result = getResultToTest();
        int i = 0;
        Iterator it = getEdgeList().iterator();
        while (it.hasNext()) {
            IEdge edge = (IEdge) it.next();
            EdgeResult edgeResult = result.getEdgeResults().get(i);
            if (!edgeResult.isBoth_nodes_ok()) {
                it.remove();
            }
            i++;
        }
    }

    public void calcWeightFitness() {
        Result result = getResultToTest();
        double count_ok = 0;

        for (int i = 0; i < getEdgeList().size(); i++) {
            EdgeResult edgeResult = result.getEdgeResults().get(i);
            if (edgeResult.isIs_weight_ok()){
                count_ok = count_ok + 0.01;;
            }
        }
        setFitness(count_ok);
    }

    public void mutateWeight(double mutationFreq) {
        Iterator it = getEdgeList().iterator();
        while (it.hasNext()) {
            IEdge edge = (IEdge) it.next();
            double ranDouble = randomDouble();
            if (ranDouble < mutationFreq) {
                edge.setWeight(ranWeight());
            }
        }
    }

    @Override
    public String toString() {
       String s = "DNA_Edge{" +
                "fitness=" + fitness +
                "}\n";
        /*Iterator it = getEdgeList().iterator();
        while (it.hasNext()) {
            IEdge edge = (IEdge) it.next();
            s = s + "Label |" + edge.getLabel() + " | end - " + edge.getEnd() + " start " + edge.getStart() + " X - " + edge.getWeight() + "\n";
        }*/

        return s;
    }

    @Override
    public int compareTo(DNA_Edge o) {
        DNA_Edge tmp = (DNA_Edge) o;
        if (getFitness() > tmp.getFitness()) {
            return -1;
        } else if (this.getFitness() < tmp.getFitness()) {
            return 1;
        }
        return 0;

    }

}

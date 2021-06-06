package main.java.impl;

import engine.Engine;
import engine.VirusConfiguration;
import engine.exceptions.ElementNotFoundException;
import engine.exceptions.VirusDoesNotExistException;
import engine.exceptions.VisualizationNotFoundException;
import engine.interfaces.IEdge;
import engine.interfaces.INode;
import main.Alg_Genetico.Population;
import main.Exceptions.EdgesNotFoundException;
import main.Exceptions.IncompleteNodesException;
import main.Exceptions.XYNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

public class TryMe
{

    public static void main(String args[]) throws VisualizationNotFoundException, VirusDoesNotExistException, IOException, ElementNotFoundException, XYNotFoundException, IncompleteNodesException, EdgesNotFoundException {


    int virus_id = 1;

    //1- Initialize the Engine for Virus number 1
    final Engine eng = new Engine(virus_id);

    //2- Let's add a new window so that we can see our solution later on
    eng.addVisualization("viz1");

    //3- Get the configuration of the Virus and print it to know what we are up against...
    VirusConfiguration conf = eng.getVirusConfiguration();
    System.out.println("\n\nTrying to find the cure for: " + conf);
    Population pp = new Population(eng, 20, 3, 0.03  , 50);
    int counter = 0;
    while (!pp.isAcabouNodeXY()) {
        pp.genNodeXYPop();
        pp.mutatePopulationXY();
        pp.calcNodeFitness();
        pp.visualizationNodePop();
        System.out.println(pp.toStringNodeList());
        counter++;
    }

    System.out.println("foram feitas " + counter + " iterações");
    System.in.read();
    int counter2 = 0;
    pp.prepararListaPTipo();
    while (!pp.isAcabouNodeType()) {
        pp.genNodeTypePop();
        pp.mutatePopulationType();
        pp.calcTypeFitness();
        pp.visualizationNodePop();
        System.out.println(pp.toStringNodeList());
        counter2++;
    }
    System.out.println("foram feitas " + counter2 + " iterações");
    System.out.println(pp.getcompleteNodeDNA().toString());

    System.in.read();
    int counter3 = 0;
    while (!pp.isAcabouEdgeNodes()) {
        //inserir percentagem de edges a gerar
        pp.genEdgeNodePop(0.6);
        pp.mutatePopulationEdgeNode();
        pp.calcEdgeNodeFitness();
        pp.visualizationEdgePop();
        System.out.println(pp.toStringEdgeList());
        counter3++;
    }
    System.out.println("foram feitas " + counter3 + " iterações");
    System.out.println(pp.getBestNodeEdges().toString());

    System.in.read();
    int counter4 = 0;
    pp.prepararListaPPeso();
    while (!pp.isAcabouEdgeWeights()) {
        pp.genWeightPop();
        pp.mutatePopulationWeight();
        pp.calcWeightFitness();
        pp.visualizationEdgePop();
        System.out.println(pp.toStringEdgeList());
        counter4++;
    }
    System.out.println("foram feitas " + counter4 + " iterações");
    System.out.println(pp.getCompleteEdges().toString());
    System.out.println("iterações de XY " + counter + " iterações de Type " + counter2 +
            " iterações de Edges " + counter3 + " iterações de Weights " + counter4);

    ArrayList<INode> nodes = pp.getcompleteNodeDNA().getNodeList();
    ArrayList<IEdge> edges = pp.getCompleteEdges().getEdgeList();

   // ISolution solution = new impl.Solution(nodes, edges, conf);
   // eng.submit(solution, "só somar");



}}

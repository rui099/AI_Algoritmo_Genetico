package main.java.impl;

import engine.Engine;
import engine.VirusConfiguration;
import engine.exceptions.ElementNotFoundException;
import engine.exceptions.VirusDoesNotExistException;
import engine.exceptions.VisualizationNotFoundException;
import main.Exceptions.XYNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TryMe
{

    public static void main(String args[]) throws VisualizationNotFoundException, VirusDoesNotExistException, IOException, ElementNotFoundException, XYNotFoundException {

        int virus_id = 8;

        //1- Initialize the Engine for Virus number 1
        final Engine eng = new Engine(virus_id);

        //2- Let's add a new window so that we can see our solution later on
        eng.addVisualization("viz1");

        //3- Get the configuration of the Virus and print it to know what we are up against...
        VirusConfiguration conf = eng.getVirusConfiguration();
        System.out.println("\n\nTrying to find the cure for: " + conf);
        Population pp = new Population(eng,20,3,0.04,100);
        int counter =0;
         while (!pp.isAcabouNodeXY()){
            pp.genNodeXYPop();
            pp.mutatePopulationXY();
            pp.calcNodeFitness();
            //pp.visualizationNodePop();
            System.out.println(pp.toStringNodeList());
             System.out.println("it - " + counter);
            counter++;
        }

        System.out.println("foram feitas " + counter + " iterações");
        //new BufferedReader(new InputStreamReader(System.in)).readLine();
        int counter2 =0;
        pp.prepararListaPTipo();
        while (!pp.isAcabouNodeType()){
            pp.genNodeTypePop();
            pp.mutatePopulationType();
            pp.calcTypeFitness();
            pp.visualizationNodePop();
            System.out.println(pp.toStringNodeList());
            counter2++;
        }
        System.out.println("foram feitas " + counter2 + " iterações");
        System.out.println(pp.getcompleteNodeDNA().toString());

        //new BufferedReader(new InputStreamReader(System.in)).readLine();
        int counter3 =0;
        while (!pp.isAcabouEdgeNodes()){
            pp.genEdgeNodePop();
            pp.mutatePopulationEdgeNode();
            pp.calcEdgeNodeFitness();
            pp.visualizationEdgePop();
            System.out.println(pp.toStringEdgeList());
            counter3++;
        }
        System.out.println("foram feitas " + counter3 + " iterações");
        System.out.println(pp.getBestNodeEdges().toString());

        new BufferedReader(new InputStreamReader(System.in)).readLine();
        int counter4 =0;
        pp.prepararListaPPeso();
        while (!pp.isAcabouEdgeWeights()){
            pp.genWeightPop();
            pp.mutatePopulationWeight();
            pp.calcWeightFitness();
            pp.visualizationEdgePop();
            System.out.println(pp.toStringEdgeList());
            counter4++;
        }
        System.out.println("foram feitas " + counter4 + " iterações");
        System.out.println(pp.getCompleteEdges().toString());
/*
        //4- Let's just invent a solution (a valid one!) since we did not implement the Genetic Algorithm yet...
        GraphNode n1 = new GraphNode(NodeType.purple, 1, conf.getX_origin(), conf.getY_origin());
        GraphNode n2 = new GraphNode(NodeType.green, 2, conf.getX_origin() + 2, conf.getY_origin());
        GraphNode n3 = new GraphNode(NodeType.purple, 3, conf.getX_origin() + 1, conf.getY_origin()+2);

        GraphEdge e1 = new GraphEdge(n1, n2, 1, "a");
        GraphEdge e2 = new GraphEdge(n2, n3, 3, "b");
        GraphEdge e3 = new GraphEdge(n1, n3, 2, "c");

        ArrayList<INode> nodes = new ArrayList<>(Arrays.asList(n1, n2, n3));
        ArrayList<IEdge> edges = new ArrayList<>(Arrays.asList(e1, e2, e3));

        ISolution fakeSolution = new impl.Solution(nodes, edges, conf);

        //5 - Let's visualize it
        eng.updateVisualization("viz1", fakeSolution);

        //6 - Let's test it
        Result res = eng.testSolution(fakeSolution);
        System.out.println(conf.getHeigth());
        System.out.println(conf.getWidth());
        System.out.println(NodeType.values()[1]);

        System.out.println("\n*** Look how good your solution is ***\n");
        System.out.println(res.toString());

        //7 - It's not a good one but let's send it to the server, just to see if it works!
        //eng.submit(fakeSolution, "FakeTeam");

        //8 - Now let's just pause this until you press enter, so that you can take a look at the Virus and at your solution
        new BufferedReader(new InputStreamReader(System.in)).readLine();



        /////////// Your Genetic Algorithm comes instead of all the code above! ///////////

        // Initialize population
        // ...

        // While stopping criteria not reached
        //// Assess fitness of each solution
        //// Select the best
        //// Reproduce
        //// Maybe display the best solution so far so that you can see how it's going?
        //// Rinse and repeat!
    }
*/
}}

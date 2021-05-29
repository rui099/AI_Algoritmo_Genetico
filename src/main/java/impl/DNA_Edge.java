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

    /**
     * Metodo responsavel por gerar um intiro de 0 até upperbound
     * @return  um numero inteiro de 0 a upperbound
     */

    private int randInt(int upperbound) {
        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }
    /**
     * Metodo responsavel por retornar um node á sorte da lista de nodes
     * @return a referencia de um node da lista de nodes
     */
    private INode randNode (){
        INode node =  nodelist.get(randInt(nodelist.size()));
        return node;
    }

    /**
     * Metodo responsavel por gerar um inteiro de 0 a 3
     * @return uma inteiro de 0 a 3
     */
    public int ranWeight(){
        Random rand = new Random();
        int int_random = rand.nextInt(3) +1;
        return int_random;
    }

    /**
     * Metodo responsavel por adicionar uma percentagem do numero total de edges á lista de edges, estas
     * edges nunca são repetidas.
     * @param percentagem - percentagem no numero de edges a ser criado
     */
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

    /**
     * Metodo responsavel por verificar se existe uma edge entre os 2 nodes que recebe
     * @param node - node recebido para verificar se existe edge
     * @param node2 - node recebido para verificar se existe edge
     * @return true se existe a edges e false se não existe
     */
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

    /**
     * Metodo responsavel por criar uma solução de teste com a lista de nodes e a lista de edges,
     * feito com a intenção de não repetir codigo.
     * @return uma instancia de Result pronto a ser usada.
     */
    private Result getResultToTest() {
        ISolution solutionNodes = new impl.Solution(getNodelist(), getEdgeList(), eng.getVirusConfiguration());
        eng.testSolution(solutionNodes);
        Result result = eng.testSolution(solutionNodes);
        return result;
    }

    /**
     * Metodo responsavel por calcular o fitness para as edges, quando calculado insere o valor na
     * variavel responsabel por guardar o valor do fitness
     */
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
    /**
     * Metodo responsavel por mutar as edges, itera a lista de edges e se um numero Double random for maior que
     * a frequencia de mutação a edges é mutada
     * @param mutationFreq - frequencia de mutação
     */
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

    /**
     * Metodo responsavel por gerar um numero do tipo Double de 0 a 1
     * @return numero de zero a 1
     */
    public double randomDouble() {
        Random rn = new Random();
        double result = rn.nextDouble() * 1;
        return result;
    }

    /**
     * Metodo responsavel por retornar um clone da lista de edges
     * @return novaLista - clone da lista de nodes
     */
    public ArrayList<IEdge> cloneList() {
        ArrayList<IEdge> novaLista = new ArrayList<>();
        Iterator iterator = getEdgeList().iterator();
        while (iterator.hasNext()) {
            IEdge edge = (GraphEdge) iterator.next();
            novaLista.add(new GraphEdge(edge.getStart(), edge.getEnd(), edge.getWeight(), edge.getLabel()));
        }
        return novaLista;
    }

    /**
     * Metodo responsavel por fazer um update á visualização do virus a ser usado.
     */
    public void updateVisualization() throws VisualizationNotFoundException {
        ISolution solutionNodes = new impl.Solution(getNodelist(), getEdgeList(), eng.getVirusConfiguration());
        eng.updateVisualization("viz1", solutionNodes);
    }

    /**
     * Metodo responsavel por fazer um crossOver uniforme das edges em que há uma probabilidade de 50%
     * de uma edge de cada DNA passar para a procxima geração.
     * @param outroDna - DNA a ser misturado.
     * @return DNA com o crossOver feito
     */
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

    /**
     * Metodo responsavel por remover edges que não estão corrretas.
     */
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
    /**
     * Metodo responsavel por calcular o fitness para o Weight das edges, quando calculado insere o valor na
     * variavel responsabel por guardar o valor do fitness
     */
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

    /**
     * Metodo responsavel por mutar o weight das edges, itera a lista de edges e se um numero Double random for maior que
     * a frequencia de mutação o weight da edge é mutado
     * @param mutationFreq - frequencia de mutação
     */
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

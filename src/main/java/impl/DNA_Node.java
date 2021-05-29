package main.java.impl;

import engine.Engine;
import engine.enums.NodeType;
import engine.exceptions.VisualizationNotFoundException;
import engine.interfaces.IEdge;
import engine.interfaces.INode;
import engine.interfaces.ISolution;
import engine.results.NodeResult;
import engine.results.Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class DNA_Node implements Comparable<DNA_Node> {
    private double fitness;
    private int heigth;
    private int width;
    private ArrayList<INode> nodeList;
    private Engine eng;


    public DNA_Node(Engine eng, ArrayList<INode> nodeList) {
        this.heigth = eng.getVirusConfiguration().getHeigth();
        this.width = eng.getVirusConfiguration().getWidth();
        this.eng = eng;
        this.nodeList = nodeList;
    }

    public DNA_Node(Engine eng) {
        this.heigth = eng.getVirusConfiguration().getHeigth();
        this.width = eng.getVirusConfiguration().getWidth();
        this.eng = eng;
        this.nodeList = new ArrayList<>();
        if (nodeList.size() == 0) {
            DNA();

            calcNodeXYFitness();
        }
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<INode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<INode> nodeList) {
        this.nodeList = nodeList;
    }
    /**
     * Metodo responsavel por gerar o X e Y quando a classe é instanciada.
     */

    public void DNA() {

        int inicioX = eng.getVirusConfiguration().getX_origin();
        int inicioY = eng.getVirusConfiguration().getY_origin();
        int fimX = eng.getVirusConfiguration().getX_origin() + heigth;
        int fimY = eng.getVirusConfiguration().getY_origin() + width;
        int label = 0;

        for (int i = inicioX; i <= fimX; i++) {
            for (int j = inicioY; j <= fimY; j++) {
                double rand = randomDouble();
                if (rand < 0.6) {
                    label++;
                    INode node = randNode(label, i, j);
                    nodeList.add(node);
                }
            }
        }
    }

    /**
     * Metodo responsavel por gerar um numero entre a lowerbound e a upperbound.
     * @param lowerbound
     * @param upperbound
     * @return um numero entre lowerbound e upperbound
     */

    public int randInt(int lowerbound, int upperbound) {
        int int_random = lowerbound + (int) (Math.random() * (upperbound - lowerbound));
        return int_random;
    }

    /**
     * Metodo responsavel por criar um node com type radom e label , x e y recebidos.
     * @param label
     * @param x
     * @param y
     * @return retorna um node com x ,y e label racebidos e type á sorte.
     */

    private GraphNode randNode(int label, int x, int y) {
        GraphNode randN = new GraphNode(randType(), label, x, y);
        return randN;
    }

    /**
     * Metodo responsavel por gerar um type á sorte.
     * @return retorna uma posição random no enum dos NodeTypes.
     */

    private NodeType randType() {
        Random random = new Random();
        int randNum = random.nextInt(NodeType.values().length);

        return NodeType.values()[randNum];
    }

    /**
     * Metodo responsavel por criar uma solução de teste com a lista de nodes e uma lista de edges vazia
     * feito com a intenção de não repetir codigo.
     * @return uma instancia de Result pronto a ser usada.
     */

    private Result getResultToTest() {
        ArrayList<IEdge> dummyEdges = new ArrayList<>();
        ISolution solutionNodes = new impl.Solution(nodeList, dummyEdges, eng.getVirusConfiguration());
        eng.testSolution(solutionNodes);
        Result result = eng.testSolution(solutionNodes);
        return result;
    }

    /**
     * Metodo responsavel por fazer um update á visualização do virus a ser usado.
     */
    public void updateVisualization() throws VisualizationNotFoundException {
        ArrayList<IEdge> dummyEdges = new ArrayList<>();
        ISolution solutionNodes = new impl.Solution(nodeList, dummyEdges, eng.getVirusConfiguration());
        eng.updateVisualization("viz1", solutionNodes);
    }

    /**
     * Metodo responsavel por calcular o fitness para o X e Y dos nodes, quando calculado insere o valor na
     * variavel responsabel por guardar o valor do fitness
     */
    public void calcNodeXYFitness() {
        Result result = getResultToTest();
        double count_ok = 0;

        Iterator iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            INode node = (INode) iterator.next();
            NodeResult nodeResult = result.getResultForNode(node.getLabel());
            if (nodeResult.isIs_x_ok() && nodeResult.isIs_y_ok()) {
                count_ok = count_ok + 0.01;
            }
        }
        setFitness(count_ok);
    }

    /**
     * Metodo responsavel por calcular o fitness para o Type dos nodes, quando calculado insere o valor na
     * variavel responsabel por guardar o valor do fitness
     */
    public void calcNodeTypeFitness() {
        Result result = getResultToTest();
        double count_ok = 0;

        Iterator iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            INode node = (INode) iterator.next();
            NodeResult nodeResult = result.getResultForNode(node.getLabel());
            if (nodeResult.isOk()){
                count_ok = count_ok + 0.01;;
            }
        }
        setFitness(count_ok);
    }

    /**
     * Metodo responsavel por remover os nodes inuteis da solução.
     */

    public void removeUselessXYNodes() {
        Result result = getResultToTest();

        Iterator it = getNodeList().iterator();
        while (it.hasNext()) {
            INode node = (INode) it.next();
            NodeResult nodeResult = result.getResultForNode(node.getLabel());
            if (!nodeResult.isIs_y_ok() || !nodeResult.isIs_x_ok()) {
                it.remove();
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
     * Metodo responsavel por mutar o type do node, itera a lista de nodes e se um numero Double random for maior que
     * a frequencia de mutação o type do node é mutado
     * @param mutationFreq - frequencia de mutação
     */

    public void mutateType(double mutationFreq) {
        Iterator it = nodeList.iterator();
        while (it.hasNext()) {
            INode node = (INode) it.next();
            double ranDouble = randomDouble();
            if (ranDouble < mutationFreq) {
                node.setType(randType());
            }
        }
    }

    /**
     * Metodo responsavel por mutar o type do node, itera a lista de nodes e se um numero Double random for maior que
     * a frequencia de mutação o X do node é mutado e depois é feito o mesmo para o y.
     * @param mutationFreq - frequencia de mutação
     */
    public void mutateXY(double mutationFreq) {

        int inicioX = eng.getVirusConfiguration().getX_origin();
        int inicioY = eng.getVirusConfiguration().getY_origin();
        int fimX = eng.getVirusConfiguration().getX_origin() + heigth;
        int fimY = eng.getVirusConfiguration().getY_origin() + width;

        for (int i = 0; i < getNodeList().size(); i++) {
            INode tempNode = new GraphNode(getNodeList().get(i));

            double ranDouble = randomDouble();
            if (ranDouble < mutationFreq) {
                int x = randInt(inicioX, fimX);
                tempNode.setX(x);
            }
            double ranDouble1 = randomDouble();
            if (ranDouble1 < mutationFreq) {
                int y = randInt(inicioY, fimY);
                tempNode.setY(y);
            }

            if (!contemNodeXY(tempNode)) {
                getNodeList().set(i, tempNode);
            }
        }
    }

    /**
     * Metodo responsavel por verificar se existe um node com o mesmo X e Y do node que recebe
     * @param node - node a procurar na lista
     */

    public boolean contemNodeXY(INode node) {
        boolean igual = false;
        Iterator it = nodeList.iterator();
        while (it.hasNext()) {
            INode tempNode = (INode) it.next();
            if (tempNode.getX() == node.getX() && tempNode.getY() == node.getY())
                igual = true;
        }
        return igual;
    }

    /**
     * Metodo responsavel por retornar um clone da lista de nodes
     * @return novaLista - clone da lista de nodes
     */
    public ArrayList<INode> cloneList() {
        ArrayList<INode> novaLista = new ArrayList<>();
        Iterator iterator = getNodeList().iterator();
        while (iterator.hasNext()) {
            INode node = (GraphNode) iterator.next();
            novaLista.add(new GraphNode(node.getType(), node.getLabel(), node.getX(), node.getY()));
        }
        return novaLista;
    }

    @Override
    public String toString() {
        String s = "";
        s = "Nodes do virus { " + "fitness=" + fitness  + "}\n";

       /*Iterator it = nodeList.iterator();
        while (it.hasNext()) {
            INode node = (INode) it.next();
            s = s + "Label |" + node.getLabel() + " | Type - " + node.getType() + " Y - " + node.getY() + " X - " + node.getX() + "\n";
        }*/
        return s;
    }

    @Override
    public int compareTo(DNA_Node o) {
        DNA_Node tmp = (DNA_Node) o;
        if (getFitness() > tmp.getFitness()) {
            return -1;
        } else if (this.getFitness() < tmp.getFitness()) {
            return 1;
        }
        return 0;

    }

}

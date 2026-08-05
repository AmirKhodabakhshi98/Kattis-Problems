package GenericClasses;



/**
    Author: Amir Khodabakhshi
    Initial creation: 10/mar-2025
    Updated last: 11/mar-2025

    Graph class made for graph problems in TDDD95. Will add functionality as needed for assignments
    Initialization of a graph with n vertices takes O(n).
    Adding m directed edges takes O(m). In and out degrees are incremented automatically as we add edges.
    Nodes are id'd from 0 to n-1.
    Note it doesn't update degree's dynamically if edges are removed, lazy solution for eulerpath algo.

 **/

import java.util.*;
public class Graph{


    public Node[] nodes;
    public LinkedList<Edge> edges;

    //Node constructor
    public Graph(int n){
        this(n,"");
    }

    //Node constructor for different algorithms. So far for eulerpath and djikstra
    public Graph(int n, String s){
        switch (s){
            case "":
                initNodes(n);
                break;
            case "djikstra": //too lazy to fix misspelling everywhere.
                initDjikstra(n);
                break;
            case "djikstraDecimal": //too lazy to fix misspelling everywhere.
                initDjikstraDecimal(n);
                break;
        }
    }

    private void initNodes(int n){
        nodes = new Node[n];
        for (int i = 0; i < n; i++){
            nodes[i] = new Node(i);
        }
    }

    //Initialize nodes with max value, as needed for djikstras algo.
    private void initDjikstra(int n){
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
            nodes[i].value = Integer.MIN_VALUE;
        }
    }

    //Initialize nodes with decimal/double max value, as needed for djikstras in getshorty algo.
    private void initDjikstraDecimal(int n){
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
            nodes[i].valueDecimal = Double.NEGATIVE_INFINITY;
        }
    }


    //equal weights for unweighted graph.
    public void addDirectedEdge(int from, int to, int weight){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], weight));
        nodes[from].outDegree++;
        nodes[to].inDegree++;
    }
    public void addDirectedEdge(int from, int to, double weight){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], weight));
        nodes[from].outDegree++;
        nodes[to].inDegree++;
    }



    public class Node{
        public boolean visited = false;
        public int id;
        public int value;
        public int weight;
        public double valueDecimal;
        public int prevNode = -1;
        public LinkedList<Edge> edges = new LinkedList<>();

        public int inDegree = 0;
        public int outDegree = 0;

        public Node(int id){
            this.id = id;
        }

    }


    public class Edge{
        public Node from;
        public Node to;
        public int weight;
        public double weightDecimal;

        public Edge(Node from, Node to, int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        public Edge(Node from, Node to, double weight){
            this.from = from;
            this.to = to;
            this.weightDecimal = weight;
        }
    }



}

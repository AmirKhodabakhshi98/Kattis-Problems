package GenericClasses;



/**
    Author: Amir Khodabakhshi
    Initial creation: 4/4-25
    Simpler version as old one got too convoluted.

    Note to future self: import/implement/extend this to modify as needed instead of convoluting it.
    or think carefully to try to modify in a general way
 **/

import java.util.LinkedList;
import Kattis.Kattio;


public class GraphSimple {


    public Node[] nodes;
    public LinkedList<Edge> edges;

    public GraphSimple(){

    }

    //Node constructor
    public GraphSimple(int n){
        this(n, 0);
    }


    //Node constructor for different algorithms. So far for eulerpath and djikstra
    public GraphSimple(int n, int defaultWeight){
        initNodes(n,defaultWeight);
    }

    private void initNodes(int n, int defaultWeight){
        nodes = new Node[n];
        for (int i = 0; i < n; i++){
            nodes[i] = new Node(i, defaultWeight);
        }
    }


    public void addDirectedEdge(int from, int to){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], 0));
    }

    public void addUndirectedEdge(int from, int to){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], 0));
        nodes[to].edges.add(new Edge(nodes[to], nodes[from], 0));

    }

    //equal weights for unweighted graph.
    public void addDirectedEdge(int from, int to, int weight){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], weight));
    }

    public void addUndirectedEdge(int from, int to, int weight){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], weight));
        nodes[to].edges.add(new Edge(nodes[to], nodes[from], weight));
    }

    private void addEdge(int from, int to, int weight){
        nodes[from].edges.add(new Edge(nodes[from], nodes[to], weight));
    }


    public class Node{
        public boolean visited = false;
        public int id;
        public int weight = 0;
        public char c;
        public int prevNode = -1;
        public LinkedList<Edge> edges = new LinkedList<>();

        public Node(int id){
            this.id = id;
        }
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
        }
    }


    public class Edge{
        public Node from;
        public Node to;
        public int weight;


        public Edge(Node from, Node to, int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}

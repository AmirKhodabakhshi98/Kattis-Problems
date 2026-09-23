package ETE389;

import Kattis.Kattio;

import java.util.*;

public class ExcavatorExpedition {


    Node[] graph;
    LinkedList<Node> topSortedGraph = new LinkedList<>();
    Stack<Node> stack = new Stack<>();


    public ExcavatorExpedition(Node[] graph) {
        this.graph = graph;
        topologicalSort();
        dp();
    }

    private void topologicalSort(){
        stack.push(graph[0]);

        while (!stack.empty()){
            Node n = stack.pop();

            if (!n.inbound){
                continue;
            }

            if (n.visited){
                topSortedGraph.addFirst(n);
                continue;
            }

            n.visited = true;
            stack.push(n);

            for (Node edge : n.edges) {
                if (edge.inbound && !edge.visited){
                    stack.push(edge);
                }
            }
        }
    }


    private void dp(){
        graph[0].best=0;
        for (Node n : topSortedGraph) {
            if (n.best==Integer.MIN_VALUE){
                continue;
            }
            for (Node edge : n.edges) {
                if (edge.best < n.best + edge.weight) {
                    edge.best = n.best + edge.weight;
                }
            }
        }
    }


    private static class Node{
        boolean visited;
        LinkedList<Node> edges;
        int best = Integer.MIN_VALUE;
        boolean inbound = false;
        int weight;
        public Node( int weight){
            this.weight = weight;
            edges = new LinkedList<>();
        }
    }


    public static void main(String[] args) {
        Kattio io = new Kattio();
        int v = io.getInt();
        int e = io.getInt();
        String line = io.getLine();
        Node[] graph = new Node[v];

        //hemma/mormor neutral
        graph[0] = new Node(0);
        graph[v-1] = new Node(0);

        for (int i = 1; i < v-1; i++){
            if (line.charAt(i) =='.'){
                graph[i] = new Node(-1);
            }else {
                graph[i] = new Node( 1);
            }
        }

        for (int i = 0; i < e; i++){
            int from = io.getInt();
            int to = io.getInt();
            graph[from].edges.add(graph[to]);
            graph[to].inbound = true;
        }

        graph[0].inbound=true;
        new ExcavatorExpedition(graph);

        io.println(graph[graph.length-1].best);
        io.flush();
        io.close();
    }

}

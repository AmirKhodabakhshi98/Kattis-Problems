package ETE389;

import Kattis.Kattio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class ExcavatorExpedition {

    HashSet<Integer> visited = new HashSet<>();

    //LinkedList<Node> graph =  new LinkedList<>();
    Node[] graph;
    LinkedList<Node> topSortedGraph = new LinkedList<>();
    //Node[] topSortedNodes; //= new Node[graph.size()];
    int sortIndex;

    private void dp(){
        //topSortedGraph.getFirst().best=0;
        graph[0].best=0;
        for (int i = 0; i<topSortedGraph.size(); i++){
            Node n  = topSortedGraph.get(i);
            if (n.best==Integer.MIN_VALUE){
                continue;
            }
            for (Node edge : n.edges) {

                if (edge.best < n.best + edge.weight) {//NBLIR UNDERFLOW?
    //                System.out.println(edge.best + " vs " + n.best +edge.weight);
                    edge.best = n.best + edge.weight;
                }
            }
        }
    }


    public ExcavatorExpedition(Node[] graph) {
        this.graph = graph;
        //sortIndex = graph.size();
        topologicalSort();
    //    System.out.println(topSortedGraph.toString());
        dp();
    }


    private void topologicalSort(){
        //visited.add(graph.size()-1);
            //while (!visited.isEmpty()){
            //    dfs(graph(visited.remove(0)));

            //}
        for (int i = 0; i <graph.length; i++){//byt tbx hash om för lkngsamt
            if (!graph[i].visited){
                dfs(graph[i]);
            }
        }


/*
        for (int i = graph.length-1; i >=0; i--){//byt tbx hash om för lkngsamt
            if (!graph[i].visited){
        //        dfs(graph[i]);
            }
        }
        */


    }

    private void dfs(Node node){
        node.visited = true;
        for(Node n : node.edges){
            if(n.visited){
                continue;
            }
       //     n.visited = true;
            dfs(n);
        }
        topSortedGraph.addFirst(node);
    }

    private static class Node{
        int id;
        boolean visited;
        LinkedList<Node> edges;
        int best = Integer.MIN_VALUE;
        int weight;
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            edges = new LinkedList<>();
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }


    public static void main(String[] args) {
        Kattio io = new Kattio();
        int v = io.getInt();
        int e = io.getInt();
        String line = io.getLine();
        Node[] graph = new Node[v];
        //hemma/mormor neutral
        graph[0] = new Node(0,0);
        graph[v-1] = new Node(v-1,0);

        for (int i = 1; i < v-1; i++){
            char c = line.charAt(i);

            if (c=='.'){
                graph[i] = new Node(i,-1);
            }else {
                graph[i] = new Node(i, 1);
            }
        }
        for (int i = 0; i < e; i++){
            graph[io.getInt()].edges.add(graph[io.getInt()]);
        }

        new ExcavatorExpedition(graph);
        io.println(graph[graph.length-1].best);
        io.flush();
        io.close();
    }

}

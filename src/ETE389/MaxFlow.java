package ETE389;

import Kattis.Kattio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MaxFlow {

    private Node from;
    private Node to;
    private Node[] nodes;

    public MaxFlow(int from, int to, Node[] nodes) {
        this.nodes = nodes;
        this.from = nodes[from];
        this.to = nodes[to];
    }






    private void bfs(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(from);
        from.visited = true;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            for (Edge edge : cur.edges) {
                Node next = edge.to;
                if (edge.residualCapacity>0 && !next.visited) {
                    next.visited = true;
                    queue.add(next);
                }

            }

        }
    }



    private static class Edge{
        Node from;
        Node to;
        int capacity;
        int residualCapacity;
        int flow = 0;
        public Edge(Node from, Node to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            residualCapacity = capacity;
        }
    }


    private static class Node{
        ArrayList<Edge> edges = new ArrayList<>();
        int flow = 0;
        boolean visited = false;

    }



    public static void main(String[] args) {
        Kattio io = new Kattio();

        int n = io.getInt();
        int m = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        Node[] nodes = new Node[n];
       // Node[] edges = new Node[m];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
        }
        for (int i = 0; i < m; i++) {
           // Edge e = new Edge(io.getInt(),io.getInt(),io.getInt())
            int from = io.getInt();
            int to = io.getInt();
            int capacity = io.getInt();
            Edge edge = new Edge(nodes[from], nodes[to], capacity);
            nodes[from].edges.add(edge);
        }
        System.err.println();

    }

}

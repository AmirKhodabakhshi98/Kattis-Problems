package ETE389;

import Kattis.Kattio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MaxFlow {

    private Node from;
    private Node to;
    private Node[] nodes;
    int maxFlow = 0;
    HashSet<Edge> pathEdges = new HashSet<>();

    public MaxFlow(int from, int to, Node[] nodes) {
        this.nodes = nodes;
        this.from = nodes[from];
        this.to = nodes[to];
        edmondKarp();
    }


    private void edmondKarp(){

        while (true){
            if (!bfs()){
                break;
            }

            int bottleneck = pathFlow();

            Node current  = to;

            while (current != from){
                Edge e = current.parentEdge;
                e.residualCapacity-=bottleneck;
                current = current.parent;
                pathEdges.add(e);
            }
            maxFlow += bottleneck;
        }
    }

    private int pathFlow(){
        int bottleneck = Integer.MAX_VALUE;
        Node current = to;

        while (current != from){
            int residualCapacity = current.parentEdge.residualCapacity;
            bottleneck = Math.min(bottleneck, residualCapacity);
            current = current.parent;
        }
        return bottleneck;
    }



    private boolean bfs(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(from);
        for (Node node : nodes) {
            node.visited = false;
        }
        from.visited = true;


        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            for (Edge edge : cur.edges) {
                Node next = edge.to;
                if (edge.residualCapacity>0 && !next.visited) {
                    next.visited = true;
                    queue.add(next);
                    next.parent = cur;
                    next.parentEdge = edge;
                    if (next == to){
                        return true;
                    }
                }
            }
        }
        return false;
    }



    private static class Edge{
        Node from;
        Node to;
        int capacity;
        int residualCapacity;

        public Edge(Node from, Node to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            residualCapacity = capacity;
        }
    }


    private static class Node{
        Node parent = null;
        LinkedList<Edge> edges = new LinkedList<>();
        Edge parentEdge = null;
        int ID = -1;
        boolean visited = false;

        Node(int ID){
            this.ID = ID;
        }
    }



    public static void main(String[] args) {
        Kattio io = new Kattio();

        int n = io.getInt();
        int m = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        Node[] nodes = new Node[n];
        Edge[] edges = new Edge[m];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < m; i++) {
            int from = io.getInt();
            int to = io.getInt();
            int capacity = io.getInt();
            Edge edge = new Edge(nodes[from], nodes[to], capacity);
            nodes[from].edges.add(edge);
            edges[i] = edge;
        }
        MaxFlow mf = new MaxFlow(s,t,nodes);


        io.println(n + " " +  mf.maxFlow + " " + mf.pathEdges.size());
        if (mf.maxFlow != 0){
            for (Edge edge : mf.pathEdges) {
                if (edge.capacity-edge.residualCapacity<0 || edge.capacity == edge.residualCapacity){
                    continue;
                }
                io.println(edge.from.ID + " " + edge.to.ID + " " + (edge.capacity-edge.residualCapacity));
            }
        }

        io.flush();
        io.close();
    }

}

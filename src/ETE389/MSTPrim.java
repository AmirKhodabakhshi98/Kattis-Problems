package ETE389;

import Kattis.Kattio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class MSTPrim {

    PriorityQueue<Edge> pq;
    Edge[] edges;
    ArrayList<Edge> mst;
    int totalcost = 0;
    Node[] nodes;
    int[][] output;
    int nbrOfNodesVisited = 0;


    public MSTPrim(Edge[]edges, Node[] nodes) {
        this.edges = edges;
        pq = new PriorityQueue<>();
        mst =  new ArrayList<>();
        this.nodes = nodes;

        prim();

        if (nbrOfNodesVisited != nodes.length) {
            totalcost = -1;
            return;
        }

        sortOutput();

    }


    private void sortOutput(){
        output = new int[mst.size()][2];

        for (int i = 0; i<output.length; i++){
            Edge e = mst.removeLast();
            output[i][0] = e.from;
            output[i][1] = e.to;
        }

        for (int i = 0; i<output.length; i++) {
            if (output[i][0] > output[i][1]) {
                int temp = output[i][1];
                output[i][1] = output[i][0];
                output[i][0] = temp;
            }
        }

        Arrays.sort(output, Arrays::compare);
    }


    private void prim(){
        Node n = nodes[0];
        n.visited = true;
        pq.addAll(n.edges);
        nbrOfNodesVisited++;

        while (!pq.isEmpty()){
            Edge e = pq.poll();
            if (!nodes[e.from].visited || !nodes[e.to].visited){
                if (!nodes[e.from].visited){
                    n =  nodes[e.from];
                }else{
                    n =  nodes[e.to];
                }

                mst.add(e);
                totalcost+=e.weight;
                nbrOfNodesVisited++;
                n.visited = true;

                for (Edge edge : n.edges) {
                    if (!nodes[edge.from].visited || !nodes[edge.to].visited){
                        pq.add(edge);
                    }
                }
            }
        }
    }

    private static class Edge implements Comparable<Edge>{

        int from;
        int to;
        int weight;
        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    private static class Node{
        ArrayList<Edge> edges = new ArrayList<>();
        boolean visited;
    }

    public static void main(String[] args) {
        Kattio io = new Kattio();

        while (true) {
            int n = io.getInt();
            int m = io.getInt();
            if (n == 0 && m == 0) {
                break;
            }

            Node[] nodes = new Node[n];
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node();
            }

            Edge[] edges = new Edge[m];
            for (int i = 0; i < edges.length; i++) {
                Edge e =  new Edge(io.getInt(),io.getInt(),io.getInt());
                edges[i] = e;
                nodes[e.from].edges.add(e);
                nodes[e.to].edges.add(e);
            }


            MSTPrim mst = new MSTPrim(edges, nodes);
            if (mst.totalcost == -1){
                io.println("Impossible");
                continue;
            }
            io.println(mst.totalcost);
            for (int[] ints : mst.output) {
                io.println(ints[0] + " " + ints[1]);
            }

        }

        io.flush();
        io.close();

    }

}

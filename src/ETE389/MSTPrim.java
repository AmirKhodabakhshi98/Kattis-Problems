package ETE389;

import Kattis.Kattio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MSTPrim {

    PriorityQueue<Edge> pq;
    Edge[] edges;
    HashSet<Edge> mst;
    //HashSet<Integer> unused;
   // HashSet<Integer> visited;
    int totalcost = 0;
    Node[] nodes;

    public MSTPrim(Edge[]edges, Node[] nodes) {
            this.edges = edges;
            pq = new PriorityQueue<>();
       // pq.addAll(Arrays.asList(edges));
      //  unused = new HashSet<>(edges.length);
        mst = new HashSet<>(edges.length);
      //  visited = new HashSet<>(edges.length);
        this.nodes = nodes;

        prim();



            System.out.println(totalcost);
            for (Edge edge : mst) {
                System.out.println(edge.from + " " + edge.to + " " + edge.weight);
            }

    }


    int total = 0;
    private void prim(){
        Node n = nodes[0];
        n.visited = true;
        pq.addAll(n.edges);
        total++;

        while (!pq.isEmpty()){
            Edge e = pq.poll();
            if (!nodes[e.from].visited || !nodes[e.to].visited){
     //           System.err.println(e.from + " " + e.to);
                if (!nodes[e.from].visited){
                    n =  nodes[e.from];

                }else{
                    n =  nodes[e.to];
                }

                mst.add(e);
                totalcost+=e.weight;
                total++;
                n.visited = true;

                for (Edge edge : n.edges) {

                    if (!nodes[edge.from].visited || !nodes[edge.to].visited){
                     //   System.err.println("adding edge " + total);
                        pq.add(edge);
                    }
                }
            }

        }


    }


/*
    int iterations=  0;
    private void prim(){

        //pq tom men nt unused -> fanns nt.
        while (!unused.isEmpty() && !pq.isEmpty()){
            iterations++;
            //Edge e = pq.poll();
            Edge e = pq.peek();

            if (unused.contains(e.from) && visited.contains(e.to)
            || (unused.contains(e.to) && visited.contains(e.from))
                ){
                unused.remove(e.from);
                unused.remove(e.to);
                visited.add(e.from);
                visited.add(e.to);
                mst.add(e);
                totalcost += e.weight;
                pq.poll();
            }
        }
        System.out.println("iterations: " + iterations);
    }
*/

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

    static void main() {
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
            //mst print

        }

        io.flush();
        io.close();

    }

}

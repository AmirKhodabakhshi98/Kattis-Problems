package ETE389;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class SSP {



    private static void dij(int n, int m, int q, int s,Node[] nodes, int[]queries, Kattio io){
        nodes[s].dist=0;
        PriorityQueue<Node> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[n];
       // queue.addAll(Arrays.asList(nodes));
      //  queue.remove(nodes[0]);
        queue.add(nodes[s]);
     //   for (Edge edge: nodes[s].edges) {
       //     queue.add(edge.dest);
       // }

        while (!queue.isEmpty()){
            Node node = queue.poll();

            for (Edge e : node.edges){
                Node neighbor = e.dest;

                if (node.dist+e.weight < neighbor.dist){
                    neighbor.dist=node.dist+e.weight;
                    queue.add(neighbor);
                }
            }

        }

        for (int i: queries) {
            if (nodes[i].dist==Integer.MAX_VALUE){
                io.println("Impossible");
            }else {
                io.println(nodes[i].dist);
            }
        }
        io.flush();

    }



    private static class Edge implements Comparable<Edge>{
        Node source;
        Node dest;
        int weight;
        public Edge(Node source, Node dest, int weight){
            this.source=source;
            this.dest=dest;
            this.weight=weight;
        }
        public int compareTo(Edge e){
            return this.weight-e.weight;
        }

    }



    private static class Node implements Comparable<Node>{
        public int compareTo(Node n){
            return this.dist-n.dist;
        }
        int dist = Integer.MAX_VALUE;

        private int index;
        private ArrayList<Edge> edges = new ArrayList<>();
        public Node(int index){
            this.index=index;
        }
        private void addEdge(Edge edge){
            edges.add(edge);
        }

    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        while (true) {
            int n = io.getInt();
            int m = io.getInt();
            int q = io.getInt();
            int s = io.getInt();
            if (n==0 && m == 0 && q ==0 && s==0){
                break;
            }
            Node[] nodes = new Node[n];
             for (int i=0; i<n; i++){
               nodes[i] = new Node(i);
            }
            PriorityQueue<Edge> edgeQueue = new PriorityQueue<>();
            for (int i = 0; i < m; i++) {
                int source = io.getInt();
                int dest = io.getInt();
                int weight = io.getInt();
                // nodes[source].addEdge(new Edge(source,dest,weight));
              //  edgeQueue.add(new Edge(source, dest, weight));
                nodes[source].addEdge(new Edge(nodes[source],nodes[dest],weight));
            }
            int[] queries = new int[q];
            for (int i=0; i<q; i++){
                queries[i] = io.getInt();
            }
            dij(n, m, q, s,nodes,queries, io);

        }
        io.flush();
        io.close();
    }
}

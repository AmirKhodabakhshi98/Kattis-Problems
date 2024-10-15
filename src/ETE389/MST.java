package ETE389;

import java.awt.List;
import java.util.*;

public class MST {
    PriorityQueue<Edge> edges;
    Kattio io;
    int n;
    int m;
    int[] parent;
    int[] rank;
    public MST(PriorityQueue<Edge> edges, Kattio io, int n, int m){
        this.edges=edges;
        this.io=io;
        this.n=n;
        this.m=m;
        parent = new int[n];
        rank = new int[n];
        for (int i=0; i<parent.length; i++){
            makeSet(i);
        }
        kruskal();
    }


    private void makeSet(int i){
        parent[i] = i;
        rank[i]=0;
    }

    private int find(int i){
        if (parent[i] != i){
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    private void union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);

        if (rootX!=rootY){
            if (rank[rootX]>rank[rootY]){
                parent[rootY] = rootX;
            }
            else if (rank[rootX] < rank[rootY]){
                parent[rootX] = rootY;
            }else{
                parent[rootY] = rootX;
                rank[rootX] += 1;
            }
        }
    }

    private  void kruskal(){
       // Set<Integer> set = new HashSet<>();
        ArrayList<Edge> res = new ArrayList<>();
        int weight = 0;

        while (!edges.isEmpty()){
            Edge edge = edges.poll();
            int x = find(edge.n1);
            int y = find(edge.n2);
            if (x!=y){
                res.add(edge);
                weight+=edge.weight;
                union(x,y);
            }
        }


        if (res.isEmpty()){
            io.println("Impossible");
            return;
        }


        //checka flera träd somehow
        //boolean[] multiTree = new boolean[res.size()];
        int par = parent[res.get(0).n1];

        for (int i=0; i<res.size(); i++){
            Edge edge = res.get(i);
            if (par != parent[edge.n1] || par != parent[edge.n2]){
                weight-=edge.weight;
                res.remove(i);
                i--;
            }
        }


      //  res.sort((e1,e2) -> e1.n1 - e2.n1);
        //lexo verkar funka
        res.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return String.valueOf(o1.n1).compareTo(String.valueOf(o2.n1));
            }
        });

   //     System.out.println(res.toString());




        io.println(weight);


        for (Edge edge: res) {
            io.println(edge.n1 + " " + edge.n2);
        }

    }



    private static class Edge implements Comparable<Edge>{
        int n1;
        int n2;
        int weight;
        public Edge(int n1, int n2, int weight){
            this.n1=n1;
            this.n2=n2;
            this.weight=weight;
        }

        public String toString(){
            return n1 + " " + n2 + " " + weight;
        }
        @Override
        public int compareTo(Edge edge) {
            if (this.weight != edge.weight){
                return this.weight-edge.weight;
            }
            if (this.n1 != edge.n1){
                return this.n1-edge.n1;
            }
            return this.n2-edge.n2;
        }
    }






    public static void main(String[] args) {
        Kattio io = new Kattio(System.in,System.out);

        while (true) {
            int n = io.getInt();
            int m = io.getInt();
            if (n==0 && m==0){
                return;
            }
            PriorityQueue<Edge> queue = new PriorityQueue<>();
            for (int i=0; i<m; i++){
                int n1 = io.getInt();
                int n2 = io.getInt();

                if (n2 < n1){ //sort här, för output later
                    int temp = n1;
                    n1=n2;
                    n2=temp;
                }

                queue.add(new Edge(n1,n2,io.getInt())); //arr->queue om för långsamt
            }
            new MST(queue,io, n, m);
            io.flush();


        }
    }
}

/*
fel för detta testfall. 2 träd output
4 2
0 1 1
2 3 1
0 0

out:
2
0 1
2 3

kan d va discon. graf input?

//If there is more than one minimum spanning tree for a given graph, then any one of them will do.
borde va fixat nu.

 */

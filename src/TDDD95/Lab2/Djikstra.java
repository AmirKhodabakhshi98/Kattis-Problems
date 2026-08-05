package TDDD95.Lab2;
import Kattis.*;
import GenericClasses.*;

/**
 Author: Amir Khodabakhshi
 Date: 11/mar-2025
 Description & Usage:
    Class with static method to run djikstra's algorithm. Takes a positively weighted, directed graph object as defined by
    the attached Graph class. Performs operations on the graph to determine the least costly path from a source node
    to all other nodes. The cost to reach a node is stored in its value attribute, where Integer.MAX_VALUE designates
    unreachable nodes. Also, it stores the previous node for each reachable node in the graph, which can be
    reconstructed using the getPath method.


 Time complexity for graph with n number of nodes and m number of edges:
    Graph initialization: O(n+m), to create n nodes and m edges.
    Djikstra: O(m * log(n)),
        Java's priority queue takes logarithmic time for poll and add. In this implementation, nodes may be inserted
        multiple times in the pq, and for each time their edges are looped. Since it's big O notation we can discard
        the constant c which indicates the repetetive number of times that nodes are added/polled from the pq:
        T(c * m * log(n)) -> O(m * log(n)).
    getPath: O(n), as in the worst case the path to the target node includes every node in the graph. The path is then
    reversed to get it in order, but that's also linear time.

 Sources:
    https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html
    https://cp-algorithms.com/graph/dijkstra.html
 **/


import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Djikstra {


    /*
        Inserts start node in priority queue. Pq is sorted in ascending order of cost to reach a node.
        Each iteration a node is popped and all its edges are looped through. If the cost of reaching
        the current node+edge weight is better than the current cost to reach the other node, the cost of the other node
        is updated and it is inserted into the pq. Here we also store the previous node traversed to reach a node, allowing
        path reconstruction.
     */
    public static void djikstra(Graph g, int s){
        g.nodes[s].value = 0;
        PriorityQueue<Graph.Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.value, b.value)); //asc
        pq.add(g.nodes[s]);

        while(!pq.isEmpty()){
            Graph.Node node = pq.poll();
            //System.err.println(node.id);
            for(Graph.Edge e : node.edges){
                if (e.weight+node.value < e.to.value){
                    e.to.prevNode = node.id;
                    e.to.value = e.weight+node.value;
                    pq.add(e.to);
                }
            }
        }
    }

    /*
    Returns the least costly path, from the given source node in the djikstra method above to inputted target node.
    It does so by starting from the target node and iterating through the previous node to reach it, until we reach
    the start node. The result is then reversed to get the path in correct order from start to target node.
    */
    public static LinkedList<Integer> getPath(Graph g, int target){
        LinkedList<Integer> path = new LinkedList<>();
        path.add(target);

        while (g.nodes[target].prevNode != -1){
            path.add(g.nodes[target].prevNode);
        }
        Collections.reverse(path);
        return path;
    }



    public static void main(String[] args) {
            Kattio io = new Kattio();

            while (true){
                int n = io.getInt();
                int m = io.getInt();
                int q = io.getInt();
                int s = io.getInt();
                if (n == 0 && q == 0 && s==0 && m==0){
                    break;
                }
                Graph g = new Graph(n, "djikstra");
                for (int i = 0; i < m; i++) {
                    int u = io.getInt();
                    int v = io.getInt();
                    int w = io.getInt();
                    g.addDirectedEdge(u,v,w);
                }
                djikstra(g,s);
                //System.err.println();
                for (int i = 0; i<q; i++){
                    int to = io.getInt();
                    int cost = g.nodes[to].value;
                    if (cost == Integer.MAX_VALUE){
                        io.println("Impossible");
                    }else {
                        io.println(g.nodes[to].value);
                    }
                }
            }

            io.flush();
            io.close();
    }
}

package TDDD95.Lab2;
import Kattis.Kattio;
import GenericClasses.*;

/**
    Author: Amir Khodabakhshi
    Date: 10/mar-2025
    Description & Usage:
        Finds and returns an Eulerian path for a given directed graph, given that at least one such path exists.
        Returns empty list if no such path exists.
        For finding the path an iterative version of Hierholzer's algorithm is implemented, starting from a startNode as given by
        getEulerStartNode (see method comments for further explanation). After a proposed path is found, a check is done
        to ensure no edges remain.
        Input should be of type Graph as defined in the attached Graph class.
        Note that the method alters the given graph, and removes edges as it goes through them.


    Time complexity for graph with n number of nodes and m number of edges:
        Graph node initialization O(n)
        Graph adding m edges O(m)
        noEdgeLeft O(n), as it just loops through nodes to check if they have edges left
        getEulerStartNode O(n), also just loops each node once.
        getEulerPath O(m+2n). The 2n comes from calls to the previous 2 methods. O(m) for Hierholzer's algorithm as per the literature.
        The implementation finds the path in reverse order, thus a call is made to Collections.reverse to return the path
        in correct order. This is done in linear time O(m+1) -> O(m), since if a path exists it will be number of edges + 1.
        In total we get O(3m+3n) -> O(m+n)
        Note that removing the first/last item(edge) in javas linkedList is constant time, which is used to delete edges.

    Sources for theory and implementation:
        https://cp-algorithms.com/graph/euler_path.html
        https://www.topcoder.com/thrive/articles/eulerian-path-and-circuit-in-graphs
        Competitive Programming 4 - Book 1
 **/

import java.util.*;

public class EulerPath {



    public static LinkedList<Integer> getEulerPath(Graph g) {
        LinkedList<Integer> path = new LinkedList<>();
        int start = getEulerStartNode(g);
        if (start == -1) {
            return path;
        }

        Stack<Graph.Node> stack = new Stack<>();
        stack.add(g.nodes[start]);

        while (!stack.isEmpty()){
            Graph.Node node = stack.peek();
            if (node.edges.isEmpty()){
                path.add(node.id);
                stack.pop();
            }else {
                stack.push(node.edges.removeFirst().to);
            }
        }

        if (noEdgeLeft(g)){
            Collections.reverse(path);
            return path;
        }

        return new LinkedList<>();
    }



    //Iterates through nodes and returns true if no edges remain in the graph.
    //Used after running euler path finding algorithm, if an edge remains then the euler path is not found.
    //I.e the graph is disconnected (note, isolated nodes are fine)
    private static boolean noEdgeLeft(Graph g){
        for (Graph.Node node : g.nodes) {
            if (!node.edges.isEmpty()){
                return false;
            }
        }
        return true;
    }

    //Iterates over all nodes and checks their in and out degrees.
    //If all nodes have equal number of in- and out degrees then a euler cycle exists, thus so does a path.
    //Returns 0 in the above case, as it doesn't matter where we start.
    //Else there should be exactly 2 nodes with odd degree, where one of them has (inDegree-outDegree) == 1
    //and the other has (outDegree-InDegree) == 1
    //In this case it returns the latter, as the node with an extra edge out should be our starting node.
    //Returns -1 in all other cases, indicating no such path can exist.
    private static int getEulerStartNode(Graph g) {
        int unEven = 0;
        int start = 0;
        boolean startFound = false;
        boolean endFound = false;

        for (Graph.Node node : g.nodes) {
            if (node.inDegree != node.outDegree){
                unEven++;
                if (unEven>2){
                    return -1;
                }
                if (node.outDegree - node.inDegree == 1){
                    startFound = true;
                    start = node.id;
                }else if (node.inDegree - node.outDegree == 1){
                    endFound = true;
                }
            }
        }

        if (startFound && !endFound){
            return -1;
        }
        if (unEven == 0 || unEven == 2){
            return start;
        }

        return -1;
    }



    public static void main(String[] args) {
        Kattio io = new Kattio();

        while (true){
            int n = io.getInt();
            int m = io.getInt();
            if (n==0 && m==0){
                break;
            }

            Graph g = new Graph(n);
            for (int i = 0; i < m; i++) {
                g.addDirectedEdge(io.getInt(), io.getInt(),0);
            }
            LinkedList<Integer> path = getEulerPath(g);
            if (path.isEmpty()){
                io.println("Impossible");
            }else {
                StringBuilder sb = new StringBuilder(path.size() * 2);
                for (Integer i : path) {
                    sb.append(i).append(" ");
                }
                io.println(sb);
            }
        }
        io.flush();
        io.close();
    }
}

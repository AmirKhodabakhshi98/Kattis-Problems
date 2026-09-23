package ETE389;

import Kattis.Kattio;

import java.util.*;

public class ExcavatorExpedition {

    //statica hela?
    //rec -> iter ?

    HashSet<Integer> visited = new HashSet<>();

    //LinkedList<Node> graph =  new LinkedList<>();
    Node[] graph;
    LinkedList<Node> topSortedGraph = new LinkedList<>();
    LinkedList<Integer> topSortedGraphArr = new LinkedList<>();
    //Node[] topSortedNodes; //= new Node[graph.size()];
    int sortIndex;
    int test = 0;
    private void dp(){
        //topSortedGraph.getFirst().best=0;
        graph[0].best=0;
        //for (int i = 0; i<topSortedGraph.size(); i++){
            for (Node n : topSortedGraph) {


          //  Node n  = node;//= topSortedGraph.get(i);
           // Node n = stack.pop();
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


    private void dpArr(){
        //topSortedGraph.getFirst().best=0;
        //graphArr[0].best=0;
        //bestArr[0]=0;
        bestVisited[0]=true;
        inboundArr[0] = true;

      //  System.err.println(Arrays.toString(weightArr));
  //      System.err.println(Arrays.toString(bestVisited));
     //   for (Integer i : edgesArr[0]) {
       //     System.out.println(i);
   //     }

        //for (int i = 0; i<topSortedGraphArr.size(); i++){//f/redaswtgreyheayheayh
            for (Integer i : topSortedGraphArr) {


            //Node n  = topSortedGraph.get(i);
            int n = i; // topSortedGraphArr.get(i);
            if (!inboundArr[n]){
                continue;
            }
        //    if (bestVisited[n]==false){
        //        continue;
      //      }
            /*if (n.best==Integer.MIN_VALUE){
                continue;
            }*/

            if (edgesArr[n]==null){
                continue;
            }
            for (Integer integer : edgesArr[n]) {
       //         System.err.println("aaaaaaaaaa");

                if (!bestVisited[integer]){
                    bestArr[integer] =  bestArr[n]+weightArr[integer];
                    bestVisited[integer]=true;
                }
                else if (bestArr[integer] < bestArr[n]+weightArr[integer]){
                    bestArr[integer] =  bestArr[n]+weightArr[integer];
          //          System.err.println("aaaaaaaaaa");
                }
            }

            /*for (Node edge : n.edges) {

                if (edge.best < n.best + edge.weight) {//NBLIR UNDERFLOW?
                    //                System.out.println(edge.best + " vs " + n.best +edge.weight);
                    edge.best = n.best + edge.weight;
                }
            }*/
        }
    //    System.err.println(Arrays.toString(bestVisited));

    }

    private Stack<Node> stack2 = new Stack<>();
    private void dp2(){
        while (stack2.empty() == false){


        }
    }

    int[] graphArr;
    boolean[]bestVisited;
    //graph, edgesArr, weightArr, inboundArr
    public ExcavatorExpedition(int[] graphArr, LinkedList<Integer>[] edgesArr, int[] weightArr, boolean[] inboundArr) {
        this.graphArr = graphArr;
        this.edgesArr = edgesArr;
        bestArr = new int[graphArr.length];
        bestVisited = new boolean[graphArr.length];
        this.inboundArr = inboundArr;
        this.weightArr = weightArr;
        visitedArr =  new boolean[graphArr.length];


        //this.graph = graph;
        //sortIndex = graph.size();

        //  LinkedList<Integer>[] edgesArr = new LinkedList[graph.length];
      //  weightArr = new int[graphArr.length];
       // inboundArr = new boolean[graphArr.length];

        topologicalSort();
  //      System.err.println(topSortedGraphArr.toString());
        //    System.out.println(topSortedGraph.toString());
        dpArr();
    }
    Stack<Integer>stackArr=  new Stack<>();
    private void dfsIterative3(){
        stackArr.push(graphArr[0]);

        while (!stackArr.empty()){
            int node = stackArr.pop();
            //Node n = stack.pop();
            if (!inboundArr[node]){
                continue;
            }
            /*if (!n.inbound){
                continue;
            }*/
            if (visitedArr[node]){
                topSortedGraphArr.addFirst(node);
                continue;
            }
            /*if (n.visited){

                topSortedGraph.addFirst(n);
                continue;
            }*/
            //n.visited = true;
            visitedArr[node] = true;
            //stack.push(n);
            stackArr.push(node);
           /* for (Node edge : n.edges) {
                if (edge.inbound && !edge.visited){
                    stack.push(edge);
                }
            }*/
            if (edgesArr[node]==null){
                continue;
            }
            for (Integer i : edgesArr[node]){
                if (inboundArr[i] && !visitedArr[i]){
                    stackArr.push(i);
                }
            }

        }
    }

    public ExcavatorExpedition(Node[] graph) {
        this.graph = graph;
        //sortIndex = graph.size();
      //  bestArr = new int[graph.length];
     //   LinkedList<Integer>[] edgesArr = new LinkedList[graph.length];
      //  visitedArr =  new boolean[graph.length];
       //  weightArr = new int[graph.length];
       //  inboundArr = new boolean[graph.length];
        topologicalSort();
    //    System.out.println(topSortedGraph.toString());
        dp();
    }


    private void topologicalSort(){
        //visited.add(graph.size()-1);
            //while (!visited.isEmpty()){
            //    dfs(graph(visited.remove(0)));
       // dfs(graph[0]);
   //     dfsIterative(graph[0]);

  /*          //}

        for (int i = 0; i <graph.length; i++){//byt tbx hash om för lkngsamt

            if (!graph[i].visited){
                //dfs(graph[i]);
            //    dfsIterative(graph[i]);
            }
        }
*/
   //     dfs(graph[0]);
    //    dfsIterative3();
    //    dfsIterative2();
/*
        for (int i = graph.length-1; i >=0; i--){//byt tbx hash om för lkngsamt
            if (!graph[i].visited){
        //        dfs(graph[i]);
            }
        }
        */

  //      System.err.println(calls);
    }

    Stack<Node> stack = new Stack<>();

    private void dfsIterative2(){
        stack.push(graph[0]);
     //   graph[0].visited=true;

        while (!stack.empty()){
            Node n = stack.pop();
         //   n.visited=true;
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
    int calls = 0;
    private void dfs(Node node){
        //   calls++;
        if (!node.inbound){
            return;
        }
        node.visited = true;

        for(Node n : node.edges){
            if(n.visited || !n.inbound){
                //    if (n.visited){
                continue;
            }
            //     n.visited = true;
            dfs(n);
        }
        if (node.inbound){
            topSortedGraph.addFirst(node);
        }

    }





    private int[]bestArr;
    LinkedList<Integer>[] edgesArr;
    boolean[] visitedArr;
    private int[] weightArr;
    private boolean[] inboundArr;

    private static class Node{
        int id;
        boolean visited;
        LinkedList<Node> edges;
        int best = Integer.MIN_VALUE;
        boolean inbound = false;
        int weight;
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            edges = new LinkedList<>(); //ba om d finns edge?
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
        /*
     int[]bestArr;
    LinkedList<Integer>[] edgesArr;
    boolean[] visitedArr;
     int[] weightArr;
     boolean[] inboundArr;
    */
        //hemma/mormor neutral
        graph[0] = new Node(0,0);
        graph[v-1] = new Node(v-1,0);
       // int[] graph = new int[v];
        //weights samma;
    //    int[] weightArr =  new int[v];
        for (int i = 1; i < v-1; i++){
       //     char c = line.charAt(i);

            if (line.charAt(i) =='.'){
                graph[i] = new Node(i,-1);
                //weightArr[i]=-1;

            }else {
                graph[i] = new Node(i, 1);
               // weightArr[i]=1;
            }
        }
       // boolean[] inboundArr = new boolean[v];
      //  LinkedList<Integer>[] edgesArr = new LinkedList[v];

        for (int i = 0; i < e; i++){
            int from = io.getInt();
            int to = io.getInt();
            graph[from].edges.add(graph[to]);
            graph[to].inbound = true;
           // graph[io.getInt()].edges.add(graph[io.getInt()]);

            /*inboundArr[to]=true;
            if (edgesArr[from]==null){
                edgesArr[from] = new LinkedList<>();
            }
            edgesArr[from].add(to);
            */
        }
        graph[0].inbound=true;
      //  inboundArr[0]=true;
        new ExcavatorExpedition(graph);
     //   ExcavatorExpedition ee = new ExcavatorExpedition(graph, edgesArr, weightArr, inboundArr);
        io.println(graph[graph.length-1].best);
 //       io.println(ee.bestArr[graph.length-1]);
        io.flush();
        io.close();
    }

}

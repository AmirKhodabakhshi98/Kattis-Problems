package ETE389;


//pseudocode
/*
For each top edge node
    Djikstra to
        each bottom edge node
shortest nbr+path.


 */

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BlockCrusherNodes {


    private class Node{
        public int h;
        public int w;
        public int weight;

        public ArrayList<Node> neighbors = new ArrayList<Node>();

        public Node parent;
        public int distance = Integer.MAX_VALUE;

        public Node(int h, int w, int weight){
            this.h = h;
            this.w = w;
            this.weight = weight;
        }
    }

    private int H;
    private int W;
    private ArrayList<Node> bestPath =  new ArrayList<Node>();
    private int bestPathWeight = Integer.MAX_VALUE;
    private Node[][] graph;

    public BlockCrusherNodes(int H, int W, int[][] block) {
        this.H = H;
        this.W = W;
        graph = new Node[H][W];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                graph[i][j] = new Node(i, j, block[i][j]);
            }
        }

       addNeighbours();

    }

    //kanske billigare o räkna medan den kör ist - ifall för långsamt
    private void addNeighbours(){
        for (int h = 0; h < H; h++) {
            for (int w = 0; w < W; w++) {
                Node n =  graph[h][w];

                for(int offsetH = -1; offsetH <= 1; offsetH++){
                    for(int offsetW = -1; offsetW <= 1; offsetW++){
                        if(offsetH == 0 && offsetW == 0){
                            continue;
                        }

                        int neighbourH = h+offsetH;
                        int neighbourW = w+offsetW;
                        if (neighbourH>=0 && neighbourH<H &&
                                neighbourW >= 0 && neighbourW<W){
                            n.neighbors.add(graph[neighbourH][neighbourW]);
                        }
                    }
                }
            }
        }
    }

    //for next top row node search
    private void reset(){
        for (Node[] nodes : graph){
            for (Node node : nodes){
                node.distance = Integer.MAX_VALUE;
                node.parent = null;
            }
        }
    }






    int[][] block;
    //Search each top node for min weight path to any bottom node
    private  int[][] fractureFinder(int[][] block){
        this.block = block;
        for (int i = 0; i < W; i++) {
            reset();
            djikstra(i);
            for (int j = 0; j < W; j++) {
                if (graph[H-1][j].distance<bestPathWeight){
                    bestPathWeight = graph[H-1][j].distance;
                    savePath(j);
                }
            }
        }
        updatePath();
        return block;
    }

    //output formattering
    private void savePath(int w){
        Node n = graph[H-1][w];
        bestPath = new ArrayList<Node>();
        while (true){
            bestPath.add(n);
            if (n.parent == null){
                break;
            }
            n = n.parent;
        }

    }

    //bästa path
    private void updatePath(){
        for (Node node : bestPath) {
            block[node.h][node.w] = 0;
        }
    }



    private  void djikstra(int startPos){
        Node startNode = graph[0][startPos];
        startNode.distance = startNode.weight;

        PriorityQueue<Node> pq = new PriorityQueue<Node>(
                (a, b) -> a.distance - b.distance
        );
        pq.add(startNode);

        while (!pq.isEmpty()){
            Node node = pq.poll();
            for (Node neighbor : node.neighbors) {
                int newDistance = node.distance + neighbor.weight;
                if(newDistance < neighbor.distance){
                    neighbor.distance = newDistance;
                    neighbor.parent = node;
                    pq.add(neighbor);
                }
            }
        }
    }




    private static int[] wordToDigits(String word){
        int[] digits  = new int[word.length()];
        for(int i = 0; i < word.length(); i++){
            digits[i] = word.charAt(i) - '0';
        }
        return digits;

    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        while (true){
            int H = io.getInt();
            int W = io.getInt();

            //no more cases
            if (H == 0 && W == 0){
                break;
            }

            int[][] block = new int[H][W];
            for (int h = 0; h < H; h++) {
                String word = io.getWord();
                block[h] = wordToDigits(word);
            }

            BlockCrusherNodes bc = new BlockCrusherNodes(H,W, block);
            block = bc.fractureFinder(block);


            for (int h = 0; h < H; h++) {
                for (int w = 0; w < W; w++) {
                    if (block[h][w] == 0){
                        System.out.print(" ");
                    }else  {
                        System.out.print(block[h][w]);
                    }
                }
                System.out.println();
            }
            System.out.println();



        }


        io.flush();
        io.close();


    }

}

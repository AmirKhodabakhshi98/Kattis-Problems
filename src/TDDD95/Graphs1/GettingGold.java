package TDDD95.Graphs1;
import GenericClasses.GraphSimple;
import Kattis.*;

import java.util.LinkedList;
import java.util.Queue;

public class GettingGold {


    private class Node{
        char c;
        boolean visited;
        int h;
        int w;
        public Node(char c, boolean visited, int w, int h){
            this.c = c;
            this.visited = visited;
            this.h = h;
            this.w = w;
        }
    }

    private Kattio io;
    int startW;
    int startH;
    Node[][] graph;
    int gold = 0;

    GettingGold(int W, int H, Kattio io){
        this.io = io;
        graph = new Node[W][H];

        for (int h = 0; h < H; h++){
            char[] line = io.getLine().toCharArray();
            for (int w = 0; w < W; w++){
                char c = line[w];
                graph[w][h] = new Node(c, false, w, h);
                if (c=='P'){
                    startW = w;
                    startH = h;
                }else if (c=='#'){
                    graph[w][h].visited = true; //so w edont have to visit walls later
                }
            }
        }
        bfs();
        io.println(gold);
        io.flush();
        io.close();
    }


    private void bfs(){
        Queue<Node> q = new LinkedList<>();
        q.add(graph[startW][startH]);
        while (!q.isEmpty()){
            Node n = q.poll();

            if (n.visited){
                continue;
            }
            n.visited = true;


            Node above = graph[n.w][n.h-1];
            Node below = graph[n.w][n.h+1];
            Node right = graph[n.w+1][n.h];
            Node left = graph[n.w-1][n.h];

            if (n.c=='G'){
                gold++;
            }

            //near trap, retreat!
            if (above.c == 'T' || below.c == 'T' || right.c == 'T' || left.c == 'T'){
                continue;
            }

            if (!above.visited){
                q.add(above);
            }

            if (!below.visited){
                q.add(below);
            }
            if (!right.visited){
                q.add(right);
            }
            if (!left.visited){
                q.add(left);
            }


        }
    }


    public static void main(String[] args) {

        Kattio io = new Kattio();
        int W = io.getInt();
        int H = io.getInt();
        new GettingGold(W,H, io);




    }

}

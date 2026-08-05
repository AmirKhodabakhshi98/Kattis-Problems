package TDDD95.Graphs1;
import Kattis.Kattio;



import java.util.LinkedList;
import java.util.Queue;

public class Frogger {



    private static void frogger(int W, int H, int maxMoves, Kattio io){
        char[][] g = new char[W][H];

        for(int h = 0; h < H; h++){
            char[] line = io.getLine().toCharArray();
            for(int w = 0; w < W; w++){
                g[w][h] = line[w];
            }
        }

    }


    







    public static void main(String[] args) {
        Kattio io = new Kattio();
        int cases = io.getInt();

        for (int i = 0; i < cases; i++){
            int maxMoves = io.getInt();
            int H = io.getInt()+2;
            int W = io.getInt();
            frogger(W,H,maxMoves,io);
        }
        io.flush();
        io.close();
    }
}


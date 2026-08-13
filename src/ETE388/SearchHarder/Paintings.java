package ETE388.SearchHarder;
import Kattis.Kattio;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Paintings {
    Node[] colors;
    HashMap<String, Integer> colorID;
    HashMap<Integer, HashSet<Integer>> forbiddenEdges;
    private int visitedTotal = 0;
    int uniquePaths = 0;
    ArrayList<Integer> bestPath = new ArrayList<>();

    public Paintings(Node[] colors, HashMap<String, Integer> colorID,
                     HashMap<Integer, HashSet<Integer>> forbiddenEdges) {
        this.colors = colors;
        this.colorID = colorID;
        this.forbiddenEdges = forbiddenEdges;
     //   System.err.println(colors.length);
        //solve
        for (int i = 0; i < colors.length; i++) {
            naive(colors[i]);
            //System.err.println("new start node");
            /*for (Node color : colors) {
                color.visited=false;
            }*/
            visitedTotal = 0;
        }
    }



    //för varje nod besök alla unvisited
    private void naive(Node n){
        //System.err.println(n.name);
        n.visited=true;
        visitedTotal++;
        if (visitedTotal == colors.length) {
            uniquePaths++;
          //  System.err.println("found, backtracking");
       //     n.visited=false;
            return;
        }

        for (int i = 0; i < colors.length; i++) {

            Node next = colors[i];

            if (next.visited){
                continue;
            }
            //next.visited=true;

            //forbidden
            if (forbiddenEdges.get(next.value).contains(n.value)
            || forbiddenEdges.get(n.value).contains(next.value)) {
                continue;
            }

                naive(next);
                visitedTotal--;
            //    System.err.println("backtracking");
                next.visited = false;

        }
        n.visited=false;

    }

    private static class Node {
        String name; //kan nog bli billigare sen med hashmap/id o ba jmf ID
        int value;
        boolean visited = false;
        ArrayList<Color> forbiddenEdges = new ArrayList<>();

        public Node(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }


    //igen med hashmap för jmf etc efter dum solution klar
    public static void main(String[] args) {
        Kattio io = new Kattio();

        int testCases = io.getInt();

        for (int i = 0; i < testCases; i++) {
            int nbrOfColors = io.getInt();
            HashMap<String, Integer> colorID = new HashMap<>();
            HashMap<Integer, HashSet<Integer>> forbiddenEdges = new HashMap<>();


            Node[] colors = new Node[nbrOfColors];
            for (int j = 0; j < nbrOfColors; j++) {
                //colors[j] = new Node(io.getWord(),nbrOfColors-j-1); //0 indexat
                colors[j] = new Node(io.getWord(),j); //0 indexat
                colorID.put(colors[j].name, colors[j].value);
                forbiddenEdges.put(colors[j].value, new HashSet<>(11)); //12max färger
            }

            int forbiddenPairs = io.getInt();
            for (int j = 0; j < forbiddenPairs; j++) {
                int id1 = colorID.get(io.getWord());
                int id2 = colorID.get(io.getWord());
                forbiddenEdges.get(id1).add(id2);
                forbiddenEdges.get(id2).add(id1);
            }
            Paintings p = new Paintings(colors, colorID, forbiddenEdges);
            io.println(p.uniquePaths);
            io.println();

        }

        io.flush();
        io.close();



    }
}

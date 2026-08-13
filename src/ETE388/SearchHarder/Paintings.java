package ETE388.SearchHarder;
import Kattis.Kattio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Paintings {
    Node[] colors;
    HashMap<String, Integer> colorID;
    HashMap<Integer, HashSet<Integer>> forbiddenEdges;

    private int visitedTotal = 0;
    int uniquePaths = 0;
    private int[] bestPath;
    private int[] currPath;

    public Paintings(Node[] colors, HashMap<String, Integer> colorID,
                     HashMap<Integer, HashSet<Integer>> forbiddenEdges) {
        this.colors = colors;
        this.colorID = colorID;
        this.forbiddenEdges = forbiddenEdges;

        for (int i = 0; i < colors.length; i++) {
            currPath = new int[colors.length];
            visitedTotal = 0;
            dfs(colors[i]);
        }
    }




    //för varje nod besök alla unvisited
    private void dfs(Node n){
        n.visited=true;
        currPath[visitedTotal] = n.value;
        visitedTotal++;

        if (visitedTotal == colors.length) {
            uniquePaths++;
            if (bestPath == null){
                bestPath = Arrays.copyOf(currPath, currPath.length);
            }
            return;
        }

        for (int i = 0; i < colors.length; i++) {

            Node next = colors[i];

            if (next.visited){
                continue;
            }

            //forbidden
            if (forbiddenEdges.get(next.value).contains(n.value)
            || forbiddenEdges.get(n.value).contains(next.value)) {
                continue;
            }
            dfs(next);
            visitedTotal--;
            next.visited = false;
        }
        n.visited=false;
    }

    private static class Node {
        String name;
        int value;
        boolean visited = false;

        public Node(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }


    public static void main(String[] args) {
        Kattio io = new Kattio();

        int testCases = io.getInt();

        for (int i = 0; i < testCases; i++) {
            int nbrOfColors = io.getInt();
            HashMap<String, Integer> colorID = new HashMap<>();
            HashMap<Integer, HashSet<Integer>> forbiddenEdges = new HashMap<>();


            Node[] colors = new Node[nbrOfColors];
            for (int j = 0; j < nbrOfColors; j++) {
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
            for (int j = 0; j < colors.length; j++) {
                io.print(colors[p.bestPath[j]].name+" ");
            }
            io.println();
        }
        io.flush();
        io.close();
    }
}

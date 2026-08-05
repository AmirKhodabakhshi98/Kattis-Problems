package TDDD95.Graphs3;
import Kattis.Kattio;
import GenericClasses.*;

//todo - kom ihåg generalize graph bättre så jag kan använda d här opsså pallar nt nu

public class XYZZY {

    private static int edges = 0;

    public static void BellmanFord(Graph g, int s){
        g.nodes[s].value=100;
        
        for (int i=0; i<edges-1; i++){
            for (Graph.Node n : g.nodes) {
                for (Graph.Edge e : n.edges) {
                    if (n.value != Integer.MIN_VALUE){
                        int temp = Math.max(e.to.value, n.value+e.to.weight);
                        if (temp<=0){
                            continue;
                        }
                        e.to.value = temp;
                    }
                }
            }
        }
    }





    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);


        while (true){
            int n = io.getInt();
            if (n==-1) break;
            //System.err.println(n);
            Graph g = new Graph(n+1, "djikstra");
            for (int i = 1; i <= n; i++) {
                int energy = io.getInt();
                g.nodes[i].weight=energy;
                int nbrOfEdges = io.getInt();
                edges+=nbrOfEdges;
                for (int j = 0; j < nbrOfEdges; j++) {
                    int to = io.getInt();
                    g.addDirectedEdge(i,to ,0); //nvm? - energi e from nt to. elr kan testa starta från slutet blir typ samma? behöver ändå bara t/f svar hm
                }
            }
            BellmanFord(g,1);
            if (g.nodes[n].value < 0){
                io.println("hopeless");
            }else{
                io.println("winnable");
            }
        }

        io.flush();
        io.close();



    }
    

}

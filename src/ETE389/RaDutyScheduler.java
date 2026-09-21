package ETE389;
import Kattis.Kattio;


public class RaDutyScheduler {





    private static void EdgeInit(MaxFlow.Node[] nodes, int from, int to, int capacity){

        MaxFlow.Edge edge = new MaxFlow.Edge(nodes[from], nodes[to], capacity,true);
        MaxFlow.Edge reverseEdge = new MaxFlow.Edge(nodes[to], nodes[from], 0, false);
        edge.reverseEdge = reverseEdge;
        reverseEdge.reverseEdge = edge;
        nodes[from].edges.add(edge);
        nodes[to].edges.add(reverseEdge);

    }


    private static void binarySearch(MaxFlow.Node[] nodes, int lower, int upper){
            

    }


    static void main() {
        Kattio io = new Kattio();

        int nbrOfRAs = io.getInt();
        int days =  io.getInt();
        MaxFlow.Node[] nodes = new MaxFlow.Node[nbrOfRAs+days+2];
        int source = nodes.length-2;
        int sink = nodes.length-1;
        nodes[source] = new MaxFlow.Node(-1);
        nodes[sink] = new MaxFlow.Node(-1);
        int capacity = 0;

        for (int i = 0; i < days; i++) {

            nodes[i] = new MaxFlow.Node(i);
            EdgeInit(nodes,i,sink,2);
        }

        for (int i = days; i < nodes.length-2; i++) {
            String line = io.getLine();
            String[] words = line.split(" ");
            nodes[i] = new MaxFlow.Node(i,words[0]);

            for (String word : words) {
                int day = Integer.parseInt(word);
                EdgeInit(nodes, i, day, 1);
            }
            //String name = io.getWord();
        }


    }
}

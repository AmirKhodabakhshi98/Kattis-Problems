package ETE389;
import Kattis.Kattio;

public class Paintball {



    private static void EdgeInit(MaxFlow.Node[] nodes, int from, int to){

        MaxFlow.Edge edge = new MaxFlow.Edge(nodes[from], nodes[to], 1,true);
        MaxFlow.Edge reverseEdge = new MaxFlow.Edge(nodes[to], nodes[from], 0, false);
        edge.reverseEdge = reverseEdge;
        reverseEdge.reverseEdge = edge;
        nodes[from].edges.add(edge);
        nodes[to].edges.add(reverseEdge);

    }



    public static void main(String[] args) {
        Kattio io = new Kattio();
        int n = io.getInt();
        int m = io.getInt();
        MaxFlow.Node[] nodes = new MaxFlow.Node[n*2+2]; //lr+st
        nodes[0] = new MaxFlow.Node(0,false);//pseudo source

        for (int i = 1; i < n+1; i++) {
            nodes[i] = new MaxFlow.Node(i,true);
        }
        for (int i = n+1; i<nodes.length; i++){
            nodes[i] = new MaxFlow.Node(i,false);
        }


        for (int i = 0; i < m; i++) {

            int from = io.getInt();
            int to = io.getInt()+n;
            EdgeInit(nodes, from, to);

            int temp = from;
            from = to-n;
            to = temp+n;
            EdgeInit(nodes,from,to);

        }

        int source = 0;
        int sink = nodes.length - 1; //pseudo sink

        //sink/source kopplingar
        for (int i = 1; i < n+1; i++) {
            EdgeInit(nodes,source,i);
            EdgeInit(nodes,i+n,sink);
        }


        MaxFlow mf = new MaxFlow(source,sink,nodes);

            int outputSize = 0;
            int[] output = new  int[n];

            for (MaxFlow.Edge edge : mf.pathEdges) {
                if (!edge.forward || edge.capacity == edge.residualCapacity || !edge.from.left) {
                    continue;
                }
                output[edge.from.ID-1] = edge.to.ID-n;
                outputSize++;
            }

            if (outputSize!=n || mf.maxFlow==0){
                io.println("Impossible");
            }else {
                for (int i : output) {
                    io.println(i);
                }
            }





        io.flush();
        io.close();

    }

}

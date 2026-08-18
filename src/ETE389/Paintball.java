package ETE389;
import Kattis.Kattio;

public class Paintball {





    public static void main(String[] args) {
        Kattio io = new Kattio();
        int n = io.getInt();
        int m = io.getInt();
        MaxFlow.Node[] nodes = new MaxFlow.Node[n*2+2]; //lr+st
        for (int i = 0; i < n; i++) {
            nodes[i] = new MaxFlow.Node(i+1,true);
        }
        for (int i = n; i<nodes.length; i++){
            nodes[i] = new MaxFlow.Node(i+1,false);
        }

        int capacity = 1;
   //     System.err.println("aaaaaaaaa" + nodes[nodes.length-2].edges.size());//nedan fel 2

        for (int i = 0; i < m; i++) {
            int from = io.getInt()-1;
            int to = io.getInt()+n-1;
            MaxFlow.Edge edge = new MaxFlow.Edge(nodes[from], nodes[to], capacity,true);
            MaxFlow.Edge reverseEdge = new MaxFlow.Edge(nodes[to], nodes[from], 0, false);
            edge.reverseEdge = reverseEdge;
            reverseEdge.reverseEdge = edge;
            nodes[from].edges.add(edge);
            nodes[to].edges.add(reverseEdge);

            if (from == nodes.length - 2 || to == nodes.length - 2){
     //           System.err.println("1111111111111111111");
            }


            int temp = from;
            from = to-n;
            to = temp+n;

            if (from == nodes.length - 2 || to == nodes.length - 2){
        //        System.err.println("222222222222222");
            }

            edge = new MaxFlow.Edge(nodes[from], nodes[to], capacity,true);
            reverseEdge = new MaxFlow.Edge(nodes[to], nodes[from], 0, false);
            edge.reverseEdge = reverseEdge;
            reverseEdge.reverseEdge = edge;
            nodes[from].edges.add(edge);
            nodes[to].edges.add(reverseEdge);

        }

        int from = nodes.length - 2;
        int to = nodes.length - 1;
        //nodes[from].left = true;
    //    System.err.println(nodes[from].edges.size());
     //   System.err.println(nodes[to].edges.size());
        for (int i = 0; i < n; i++) {
            MaxFlow.Edge  edgeFrom = new MaxFlow.Edge(nodes[from],nodes[i],capacity, true);
            MaxFlow.Edge  reverseEdgeFrom = new MaxFlow.Edge(nodes[i],nodes[from],0, false);
            edgeFrom.reverseEdge = reverseEdgeFrom;
            reverseEdgeFrom.reverseEdge = edgeFrom;
            nodes[from].edges.add(edgeFrom);
            nodes[i].edges.add(reverseEdgeFrom);

            int j = i+n;
            MaxFlow.Edge edgeTo = new MaxFlow.Edge(nodes[j],nodes[to],capacity, true);
            MaxFlow.Edge  reverseEdgeTo = new MaxFlow.Edge(nodes[to],nodes[j],0, false);
            edgeTo.reverseEdge = reverseEdgeTo;
            reverseEdgeTo.reverseEdge = edgeTo;
            nodes[to].edges.add(reverseEdgeTo);
            nodes[j].edges.add(edgeTo);
        }

      //  System.err.println(nodes[from].edges.size());
    //    System.err.println(nodes[to].edges.size());
        MaxFlow mf = new MaxFlow(from,to,nodes);

        int p = 0;
        for (MaxFlow.Edge pathEdge : mf.pathEdges) {
            if (pathEdge.from.left && pathEdge.forward && pathEdge.capacity!=pathEdge.residualCapacity) {
                p++;
            }
        }//rätt
        if (p != n || mf.maxFlow == 0){
            io.println("Impossible");
        }else {
            int[] output = new  int[n];

            for (MaxFlow.Edge edge : mf.pathEdges) {
                if (!edge.forward || edge.capacity == edge.residualCapacity || !edge.from.left) {
                    continue;
                }
                output[edge.from.ID-1] = edge.to.ID-n;
                //io.println(edge.from.ID + " " + (edge.to.ID-n));
            }
            for (int i : output) {
                io.println(i);
            }
        }



        io.flush();
        io.close();

    }

}

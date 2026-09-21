package ETE389;
import Kattis.Kattio;

import java.util.LinkedList;


public class RaDutyScheduler {




    private static void resetRaCapacities(MaxFlow.Node[] nodes, int start, int end, int capacity){
        for (int i = start; i <= end; i++){
            MaxFlow.Node node = nodes[i];
            for (MaxFlow.Edge edge : node.edges) {
                if(edge.forward){
                    edge.capacity = capacity;
                    edge.residualCapacity = capacity;
                }else {
                    edge.capacity = 0;
                    edge.residualCapacity = 0;
                }
            }
        }
    }


    private static void EdgeInit(MaxFlow.Node[] nodes, int from, int to, int capacity){

        MaxFlow.Edge edge = new MaxFlow.Edge(nodes[from], nodes[to], capacity,true);
        MaxFlow.Edge reverseEdge = new MaxFlow.Edge(nodes[to], nodes[from], 0, false);
        edge.reverseEdge = reverseEdge;
        reverseEdge.reverseEdge = edge;
        nodes[from].edges.add(edge);
        nodes[to].edges.add(reverseEdge);

    }


    private static void extractAnswer(MaxFlow.Node[] nodes, int raStart, int raEnd){


    }

    private class Answer{
        String name;
        int[] days;
    }

    private static int binarySearch(MaxFlow.Node[] nodes, int lower, int upper, int source, int sink, int raStart, int raEnd, int days){
        MaxFlow mf = new MaxFlow(); // = new MaxFlow(source,sink, nodes);
        int maxSinkCap = upper*2; //uppeR=)days
        int ans = -1;

        while (lower <= upper){
            int capacity = lower + (upper-lower) /2;
            resetRaCapacities(nodes,0,days-1,2); //dag end
            resetRaCapacities(nodes,raStart,raEnd,1);//ra dag
            resetRaCapacities(nodes,source,source,capacity);//ra dag

            //mf = new MaxFlow();
            mf.manualEdmondKarp(source,sink,nodes);

            if (mf.maxFlow == 2L *days){
                ans = capacity;
                upper = capacity-1;
            }else {
                lower = capacity+1;
            }

        }

        return ans;
    }

    static void main(String[] args) {
        Kattio io = new Kattio();

        int nbrOfRAs = io.getInt();
        int days =  io.getInt();
        MaxFlow.Node[] nodes = new MaxFlow.Node[nbrOfRAs+days+2];
        int source = nodes.length-2;
        int sink = nodes.length-1;
        nodes[source] = new MaxFlow.Node(-1);
        nodes[sink] = new MaxFlow.Node(-1);
        int raStart = days;
        int raEnd = days+nbrOfRAs-1;
        int capacity = 0;

        //days init
        for (int i = 0; i < days; i++) {

            nodes[i] = new MaxFlow.Node(i+1);
            EdgeInit(nodes,i,sink,2);
        }


        //rainit
        for (int i = days; i < nodes.length-2; i++) {
            String line = io.getLine();
            String[] words = line.split(" ");
            nodes[i] = new MaxFlow.Node(i,words[0]);
            EdgeInit(nodes,source, i,capacity);
///////////ha koll på minmax dagar här sen om för långsamt
            for (int j = 1; j < words.length; j++) {
                int day = Integer.parseInt(words[j]);
                EdgeInit(nodes, i, day-1, 1);
            }
            //String name = io.getWord();
        }


        //MaxFlow mf = binarySearch(nodes,1,days,source,sink, raStart, raEnd);
        int bin = binarySearch(nodes,1,days,source,sink, raStart, raEnd, days);
        //System.out.println(bin);
        resetRaCapacities(nodes,0,days-1,2); //dag end
        resetRaCapacities(nodes,raStart,raEnd,1);//ra dag
        resetRaCapacities(nodes,source,source,bin);//ra dag
        MaxFlow mf = new MaxFlow();
        mf.manualEdmondKarp(source,sink,nodes);


        for (int i = 0; i < days; i++) {
            MaxFlow.Node node = nodes[i];
            io.print("Day " + node.ID + ": ");
            LinkedList<String> list = new LinkedList<>();
            for (MaxFlow.Edge edge : node.edges) {
                if (!edge.forward){
                    if (edge.residualCapacity>0){
                     //   list.add(edge.to.name);
                        io.print(edge.to.name + " ");
                    }
                }
            }
                io.println();
        }

        io.flush();
        io.close();
      //  System.out.println(mf.maxFlow);

    }
}

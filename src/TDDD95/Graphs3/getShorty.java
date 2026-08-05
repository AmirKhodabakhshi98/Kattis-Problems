package TDDD95.Graphs3;
import Kattis.Kattio;
import GenericClasses.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.PriorityQueue;

public class getShorty {


    private static void djikstraGetShorty(Graph g, int s){
        g.nodes[s].valueDecimal = 1;
        PriorityQueue<Graph.Node> pq = new PriorityQueue<>((a, b) -> Double.compare(b.valueDecimal, a.valueDecimal)); //desc
        pq.add(g.nodes[s]);

        while(!pq.isEmpty()){
            Graph.Node node = pq.poll();
            //System.err.println(node.id);
            for(Graph.Edge e : node.edges){
                double temp = Math.log(e.weightDecimal)+ Math.log(node.valueDecimal);
                if (temp > Math.log(e.to.valueDecimal)){ //ln minus infinity..................................................
                    e.to.prevNode = node.id;
                    e.to.valueDecimal = temp;
                    pq.add(e.to);
                }

            }
        }
    }


    public static void main(String[] args) {
        Kattio io = new Kattio();

        while (true){
            int n = io.getInt();
            int m = io.getInt();
            if(n==0 && m==0){
                break;
            }
            Graph g = new Graph(n, "djikstraDecimal");
            for (int i = 0; i < m; i++) {
                int n1 = io.getInt();
                int n2 = io.getInt();
                double weight = io.getDouble();
                g.addDirectedEdge(n1,n2,weight);
            }
            djikstraGetShorty(g,0);
            if (g.nodes[n-1].valueDecimal == Double.NEGATIVE_INFINITY){
                g.nodes[n-1].valueDecimal=1;
            }else if (g.nodes[n-1].valueDecimal < 0){
                g.nodes[n-1].valueDecimal=0;
            }

            //io.printf("%.4f",g.nodes[n-1].valueDecimal);
            double d = Math.exp(g.nodes[n - 1].valueDecimal);
            BigDecimal b = BigDecimal.valueOf(d).setScale(4, RoundingMode.DOWN);
            io.println(d);
           // double t = 0.123456789;
            //io.printf("%.4f",t);
            //b = new BigDecimal(t).setScale(4, RoundingMode.DOWN);
           // io.println(b);
        }

        io.flush();
        io.close();
    }
}

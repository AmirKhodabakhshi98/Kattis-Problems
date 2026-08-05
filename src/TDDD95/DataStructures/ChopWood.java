package TDDD95.DataStructures;
import Kattis.*;

import java.util.*;

public class ChopWood {


    private static class Node implements Comparable<Node>{
        int name;
        int degree;

        public Node(int name){
            this.name=name;
            this.degree=0;
        }

        private void addDegree(){
            degree++;
        }
        private void lowerDegree(){
            degree--;
        }

        @Override
        public int compareTo(Node o) {

            if (this.degree==o.degree){
                return this.name-o.name;
            }
            return this.degree-o.degree;
        }


    }


    private static void error(Kattio io){
        io.println("Error");
        io.flush();
        io.close();
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int n = io.getInt();
        if (n==1){
            if (io.getInt()==2){
                io.println("1");
            }else {io.println("Error");}
            io.flush();
            io.close();
        }
      //  Node[] nodes = new Node[n];

        PriorityQueue<Node> pq;// = new PriorityQueue<>(n+1);
        Queue<Node> queue = new LinkedList<>();

        HashMap<Integer,Node> map = new HashMap<>();

        for (int i=1; i<=n;i++){
            //nodes[i] = new Node(i+1);
            map.put(i,new Node(i));
        }



        for (int i=0; i<n;i++){
            int num = io.getInt();

            if (i==n-1 && num != n + 1) {
                error(io);
                return;
            }

            Node node;
            //if (num-1>=n){
              //  error(io);
                //return;
            //}
            if (map.containsKey(num)){
                node=map.get(num);
                map.remove(num);
                node.addDegree();
            }else {
                node = new Node(num);
                //nodes[num-1].addDegree();
            }

            map.put(num, node);
            //queue.add(nodes[num-1]);
            queue.add(node);
        }



        //ArrayList<Node> list = new ArrayList<>(map.values());
        // nodes.add((Node) map.values().toArray(new Node[0]));
        //Arrays.sort(nodes);
        StringBuilder sb = new StringBuilder(2*n);
        pq = new PriorityQueue<>();

        for (Node node: map.values()) {
            if (node.degree==0){
                pq.add(node);
            }
        }

        if (pq.isEmpty()){
            error(io);
            return;
        }

        int counter = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.degree == 0) {
                pq.add(node);
            }else {
                node.lowerDegree();

            }

            Node node1 = pq.poll();
            if (node1.degree!=0){
                error(io); return;
            }
            sb.append(node1.name).append("\n");
            counter++;
            if (node.degree==0){
                pq.add(node);
            }
        }


        if (sb.isEmpty() || counter!=n){
            error(io);

        }else {
            io.println(sb);
        }
        io.flush();
        io.close();


    }

}

package TDDD95.DataStructures;
import Kattis.*;

import java.util.*;

public class SetStackComputer {

    private static int push(Stack<HashSet<Integer>> s ){
        s.push(new HashSet<>());
        return getCardinality(s);
    }

    private static int dup(Stack<HashSet<Integer>> s ){
        s.push(new HashSet<Integer>(s.peek()));
        return getCardinality(s);
    }

    private static int union(Stack<HashSet<Integer>> s ){
        HashSet<Integer>unionSet = new HashSet<>(s.pop());
        unionSet.addAll(s.pop());
        s.push(unionSet);
        return getCardinality(s);
    }

    private static int intersect(Stack<HashSet<Integer>> s ){
        HashSet<Integer>intersectSet = new HashSet<>(s.pop());
        intersectSet.retainAll(s.pop());
        s.push(intersectSet);
        return getCardinality(s);
    }

    private static HashMap<HashSet<Integer>,Integer> map = new HashMap<>();
    private static int ID = Integer.MAX_VALUE;

    // (A+B)+B == A+B not A+2B!!!!!!!!!!
    private static int add(Stack<HashSet<Integer>> s ){
        HashSet<Integer> s1 = s.pop();
        HashSet<Integer> s2 = new HashSet<>(s.pop());
        if (!map.containsKey(s1)){
            map.put(s1, ID--);
        }
        s2.add(map.get(s1));
        s.push(s2);
        return getCardinality(s);
    }

    private static int getCardinality(Stack<HashSet<Integer>> s ){
        return s.peek().size();
    }



    public static void main(String[] args) {
        Kattio io = new Kattio(System.in,System.out);
        int T = io.getInt();

        for (int t =0; t<T; t++){
            int N = io.getInt();
            StringBuilder sb = new StringBuilder(N*2); //reminder - kolla upp hur \n räknas storlekmässigt
            Stack<HashSet<Integer>> s = new Stack<>();
            for (int i=0; i<N;i++){
                String str = io.getWord();
                switch (str){
                    case "PUSH":
                        sb.append(push(s));
                        break;
                    case "DUP":
                        sb.append(dup(s));
                        break;
                    case "UNION":
                        sb.append(union(s));
                        break;
                    case "INTERSECT":
                        sb.append(intersect(s));
                        break;
                    case "ADD":
                        sb.append(add(s));
                        break;
                    default: System.exit(1);
                }
                sb.append("\n");
            }
            sb.append("***");
            io.println(sb); //roligt att append(\n) nt behövs för println här xD

        }

        io.flush();
        io.close();
    }
}

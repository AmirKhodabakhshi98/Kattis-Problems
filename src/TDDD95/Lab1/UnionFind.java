package TDDD95.Lab1;
import Kattis.*;

/*
    Author: Amir Khodabakhshi
    Date: 4/feb-2025
    Usage: UnionFind class that initializes a graph without edges with nodes labeled as integers from "start" to "end"(inclusive)
    The "find" method finds the parent of a node 'i', i.e. the group which the node belongs to.
    "Union" joins two groups of nodes, and "same" returns true if two nodes belong to the same group.
    It is assumed that inputs 'a' & 'b' are non-negative integers where a!=b.

    Time complexity: The class initialization requires a one time O(n) cost.
    Time complexity for the methods "same" and "union" is 2*find -> O(find).
    Without path-compression the time complexity of the find operation would be O(n), as in the worst case the entire
    parent array would have to be traversed to find the parent of node 'i'.
    Path-compression is utilized during the find operation in order to flatten the structure and reduce lookup times
    in the future for the iterated nodes. When finding the parent 'b' of a node 'a', each node 'i' in the path has its
    parent updated to 'b', thus making future find operations for those nodes O(1), given that they are unchanged.
    In total the time complexity of the find method is O(log n).
    Source: https://cp-algorithms.com/data_structures/disjoint_set_union.html#path-compression-optimization
 */


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UnionFind {

    private int[] parent;

   public UnionFind(int a, int b){
       parent=IntStream.rangeClosed(a,b).toArray();
   }

    public int find(int i){
            if (parent[i]!=i){
                int root = find(parent[i]);
                parent[i]=root;
                return root;
            }
            return i;
   }


   public boolean same(int a, int b){
       return find(a)==find(b);
   }

   public void union(int a, int b){
        parent[find(b)]=find(a);
   }


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in,System.out);
        int N = io.getInt();
        int Q = io.getInt();
        StringBuilder sb = new StringBuilder(2*Q);
        UnionFind unionFind = new UnionFind(0,N-1);
        for (int q=0; q<Q; q++){
            if (io.getWord().equals("=")){
                unionFind.union(io.getInt(),io.getInt());
            }else {
                if (unionFind.same(io.getInt(),io.getInt())){
                    sb.append("yes\n");
                }else {
                    sb.append("no\n");
                }
            }
        }
        io.print(sb);
        io.flush();
        io.close();
    }







}

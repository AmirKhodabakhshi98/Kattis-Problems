package TDDD95.Lab1;

import Kattis.Kattio;

import java.util.Arrays;

public class FenwickTree {


    int[] tree;
    public FenwickTree(int size){
       tree = new int[size+2];
    }

    public void add(int i, int delta){
        i++;
        while (i< tree.length){
            tree[i]+=delta;
            i+= g(i);
        }
    }


    private int g(int i){
        return i&-i;
    }

    public int sum(int i){
        if (i==0){
            return 0;
        }
        i++;
        int sum = 0;
        while (i>0){
            sum += tree[i];
            i-= g(i);
        }
        return sum;
    }


    public static void main(String[] args) {
        Kattio io = new Kattio();
        int N = io.getInt();
        int Q = io.getInt();
        FenwickTree fenwickTree = new FenwickTree(N);
        StringBuilder sb = new StringBuilder(2*Q);
        for (int q =0; q<Q; q++){
            if (io.getWord().equals("+")){
                fenwickTree.add(io.getInt(),io.getInt());
            }else {
                sb.append(fenwickTree.sum(io.getInt())).append("\n");
            }
        }
        io.print(sb);
        io.flush();
        io.close();
    }
}



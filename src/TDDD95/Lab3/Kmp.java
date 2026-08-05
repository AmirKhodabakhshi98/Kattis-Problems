package TDDD95.Lab3;

import Kattis.Kattio;

import java.util.LinkedList;

/**
 * Amir Khodabakhshi, 12/apr-2025
 *  kmpMatcher function that given two input strings, a text and a pattern,
 *  returns a list of indices where pattern starts in the text. Returns empty list if no matches.
 *
 * kmpMatcher compares chars of the text and pattern to find matches, using the array from
 *  computePrefixFunction that tells us how much to shift if/when mismatch between chars happens.
 *
 *  Implementation follows instructions in the book "Introduction to algorithms 3rd ed." (Cormen et al.),
 *  running in O(N+M) time, where N is the length of the text, and M the length of the pattern. Specifically,
 *  computePrefixFunction takes O(M) processing the pattern to build the prefix array,
 *  and then O(N) for finding matches in the text in kmpMatcher.
 */
public class Kmp {



    
    private static int[] computePrefixFunction(String p){
        int[] pi = new int[p.length()];
        int k = 0;

        for (int q = 1; q < p.length(); q++) {
            while (k>0 && p.charAt(k) != p.charAt(q)) {
                k = pi[k-1];
            }
            if (p.charAt(k)==p.charAt(q)) {
                k++;
            }
            pi[q] = k;
        }
        return pi;
    }


    public static LinkedList<Integer> kmpMatcher(String t, String p){
        int q = 0;
        int[] pi = computePrefixFunction(p);
        LinkedList<Integer> ans = new LinkedList<>();

        for (int i =0; i<t.length(); i++){
            while (q>0 && p.charAt(q) != t.charAt(i)) {
                q = pi[q-1];
            }
            if (p.charAt(q) == t.charAt(i)){
                q++;
            }
            if (q==p.length()){
                ans.add(i-p.length()+1);
                q=pi[q-1];
            }
        }
        return ans;
    }





    public static void main(String[] args) {
        Kattio io = new Kattio();

        while (true){
            String p = io.getLine();
            String t = io.getLine();
            LinkedList<Integer> ans = kmpMatcher(t,p);
            for (Integer i : ans) {
                io.print(i + " ");
            }
            io.println();
            if (!io.ready()){
                break;
            }
        }
        io.flush();
        io.close();
    }
}


package TDDD95.Strings1;
import Kattis.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class EvilStrawWartsLive {

    //n'sta gång ba kolla om d palindrome först. kanske fel svar därifån
    //elr hitta mitten i odd sen flytta den först?


    private static boolean canBePalindrome(String s){
        boolean isOdd = false;
        if (s.length() % 2 != 0){
            isOdd = true;
        }

        int[] counter = new int[123];
        for (int i = 0; i<s.length(); i++){
            char c  = s.charAt(i);
            counter[c]++;
        }

        if (isOdd){
            int nbrOfOdd = 0;
            for (int i = 97; i<=122; i++){
                if (counter[i]%2 != 0){ //exakt 1 udda antal i udda längd
                    nbrOfOdd++;
                }
            }
            if (nbrOfOdd != 1){
                return false;
            }

        }else {
            for (int i = 97; i<=122; i++){
                if (counter[i]%2 != 0){
                    return false; // jämn längd kan nt ha udda antal
                }
            }
        }
        return true;

    }

    private static int getNbrOfSwaps(String s){
        if (!canBePalindrome(s)){
            return -1;
        }
        //minska looparna  om för låpngsamt
        LinkedList<Character> sLL = new LinkedList<>();
        for (int i = 0; i<s.length(); i++){
            sLL.add(s.charAt(i));
        }
        int swapsCounter = 0;

        while (!sLL.isEmpty()){
            int index = sLL.indexOf(sLL.getLast());
            if (index == sLL.size()-1){
                swapsCounter += Math.floorDiv(index,2);
            }else {
                swapsCounter+=index;
                sLL.remove(index); //krockar metoderna med char ascii vs int?
            }

            sLL.removeLast();

        }



        return swapsCounter;
    }


    public static void main(String[] args) {
        Kattio io = new Kattio();
        int n = io.getInt();

        for (int i = 0; i < n; i++) {
            int ans = getNbrOfSwaps(io.getWord());
            if (ans == -1){
                io.println("Impossible");
            }else io.println(ans);
           // System.err.println(Arrays.toString());
        }



        io.flush();
        io.close();

    }
}

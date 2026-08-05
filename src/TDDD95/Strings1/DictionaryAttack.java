package TDDD95.Strings1;

import Kattis.Kattio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class DictionaryAttack {

    LinkedList<String> dict;
    LinkedList<String> passwords;
    Kattio io;


    //d e pw som e 250 o dict 1000..................

    public DictionaryAttack(LinkedList<String> dict, LinkedList<String> passwords) {
        this.dict = dict;
        this.passwords = passwords;
        this.io = io;

        long startTime = System.currentTimeMillis();
        cleanPasswords();
        long endTime = System.currentTimeMillis();
        System.err.println("clean pw " + (endTime - startTime));

        //all anagrams with 3 moves
        startTime = System.currentTimeMillis();
        anagrams3Moves();
        endTime = System.currentTimeMillis();

        System.err.println("anagram " + (endTime - startTime));

        //all of those with 1-3 0's

        startTime = System.currentTimeMillis();
        printNoMatches();
        endTime = System.currentTimeMillis();
        System.err.println("printmatches " + (endTime - startTime));

        //  System.err.println(recurCounterTotal);
        //   System.err.println(recurCounter);

        //System.out.println("");
    }


    private void anagrams3Moves(){
        for (String s : dict) {
            for (int i = 0;i<s.length();i++){
                permuations1 = new HashMap<>();
                permuations2 = new HashMap<>();

                recursive(s,0,i);
                //recursive("abc",0,i);
            }
        }
    }





    //HashMap<Integer,String > permuations1 = new HashMap<>();
    //HashMap<Integer,String > permuations2 = new HashMap<>();

    private static class StackState{
        String s;
        int moves;
        int index;
        public StackState(String s, int moves, int index) {
            this.s = s;
            this.moves = moves;
            this.index = index;
        }
    }
    HashSet<String> permuations = new HashSet<>();
    //   HashSet<String> permuations1 = new HashSet<>();
    //   HashSet<String> permuations2 = new HashSet<>();

    HashMap<String, HashSet<Integer>> permuations1 = new HashMap<>();
    HashMap<String, HashSet<Integer>> permuations2 = new HashMap<>();

    private void recursive(String s, int moves, int index){


        Stack<StackState> stack = new Stack<>();

        stack.push(new StackState(s, moves, index));
        while (!stack.isEmpty()) {
            StackState state = stack.pop();
            s = state.s;
            moves = state.moves;
            index = state.index;
            permuations.add(s);

            if (moves==1){
                if (permuations1.containsKey(s) && permuations1.get(s).contains(index)) {
                    continue;
                }

                permuations1.computeIfAbsent(s, k -> new HashSet<>());
                permuations1.get(s).add(index);


            }
            if (moves==2){
                if ((permuations2.containsKey(s) && permuations2.get(s).contains(index))){
                    continue;
                }
                permuations2.computeIfAbsent(s, k -> new HashSet<>());
                permuations2.get(s).add(index);
            }






            char[] cArr = s.toCharArray();
            char c = s.charAt(index);

            if (index + 1 < s.length()) {
                char cF = s.charAt(index + 1);
                //byt fram
                cArr[index] = cF;
                cArr[index + 1] = c;
                String temp = new String(cArr);
                permuations.add(temp);


                for (int i = 0; i < s.length(); i++) {
                    if (moves==2){
                        break;
                    }
                    // recursive(new String(cArr), moves + 1, i);
                    stack.push(new StackState(temp, moves + 1, i));
                }
                //recursive(new String(cArr),moves +1, index +1);
                cArr[index] = c;
                cArr[index + 1] = cF; //fixa sen utan duplic.
            }
/*

        //byt bak
        if (index-1 >= 0){
            char cB = s.charAt(index-1);
            cArr[index] = cB;
            cArr[index-1] = c;

            for (int i = 0; i<s.length(); i++){
                recursive(new String(cArr),moves +1, i);
            }
            //recursive(new String(cArr), moves +1, index +1);

            cArr[index] = c;
            cArr[index-1] = cB;
        }
*/
            //troligen för långs utan stringbuilder byt sen / elr char arr
            //overlappng subprobs ?


            //sätt 0:a

            if (c != 0) {
                cArr[index] = '0';
                String temp = new String(cArr);
                permuations.add(temp);

                for (int i = 0; i < s.length(); i++) {
                    if (moves==2){
                        break;
                    }
                    stack.push(new StackState(temp, moves + 1, i));
                }
                //   recursive(new String(cArr), moves+1, index+1);
            }

        }

    }


    HashMap<String,String> origPasswords = new HashMap<>();
    private void cleanPasswords() {
        for (int i=0; i<passwords.size(); i++) {
            String s = passwords.get(i);
            passwords.set(i,passwords.get(i).replaceAll("[1-9]","0"));
            origPasswords.put(passwords.get(i),s);
        }
    }



    HashSet<String> noMatches = new HashSet<>();
    private void printNoMatches() {
        for (String password : passwords) {
            if (!permuations.contains(password)) {
          //      io.println(origPasswords.get(password));
                noMatches.add(origPasswords.get(password));
            }
        }
        //io.flush();
     //   io.close();
    }




    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("pome.txt");
        PrintWriter writer = new PrintWriter(file);

        File myObj = new File("dictionary.txt");
        Scanner io = new Scanner(myObj);
        int n = Integer.parseInt(io.nextLine());
        long startTime = System.currentTimeMillis();
        LinkedList<String> dict = new LinkedList<>();
        for (int i =0; i<n; i++){
            dict.add(io.nextLine());
        }


        LinkedList<String> passwords = new LinkedList<>();

        while (io.hasNextLine()){
            passwords.add(io.nextLine());

        }

        DictionaryAttack d = new DictionaryAttack(dict,passwords);
        for (String noMatch : d.noMatches) {
            writer.println(noMatch);
        }
        writer.flush();
        writer.close();



        io.close();



        long endTime = System.currentTimeMillis();
        System.err.println("total time " + (endTime - startTime));

    }
}

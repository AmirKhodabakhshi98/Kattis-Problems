package TDDD95.Strings1;
import Kattis.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class DictionaryAttackCleaned {

    HashSet<String> dict;
    LinkedList<String> passwords;
    Kattio io;

    HashSet<String> permuations = new HashSet<>();
    HashMap<String, HashSet<Integer>> permuations1 = new HashMap<>();
    HashMap<String, HashSet<Integer>> permuations2 = new HashMap<>();

    HashSet<Integer> pwLength = new HashSet<>();
    public DictionaryAttackCleaned(HashSet<String> dict, LinkedList<String> passwords, Kattio io, HashSet<Integer> pwLength) {

        this.pwLength = pwLength;
        this.dict = dict;
        this.passwords = passwords;
        this.io = io;

        //replace 1-9 with 0
        cleanPasswords();

        //all anagrams(?) with 3 moves incl swapping for 0
        anagrams3Moves();

        //print pw's that don't match permuations set
        printNoMatches();
    }


    private void anagrams3Moves(){
        for (String s : dict) {
            if (pwLength.contains(s.length())) {

                for (int i = 0;i<s.length();i++){
                    permuations1 = new HashMap<>();
                    permuations2 = new HashMap<>();

                    notRecursive(s,0,i);
                }

            }


        }
    }


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


    private void notRecursive(String s, int moves, int index){

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

            //byt fram
            if (index + 1 < s.length()) {
                char cF = s.charAt(index + 1);
                cArr[index] = cF;
                cArr[index + 1] = c;
                String temp = new String(cArr);
                permuations.add(temp);


                for (int i = 0; i < s.length(); i++) {
                    if (moves==2){
                        break;
                    }
                    stack.push(new StackState(temp, moves + 1, i));
                }

                cArr[index] = c;
                cArr[index + 1] = cF;
            }


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




    private void printNoMatches() {
        for (String password : passwords) {
            if (!permuations.contains(password)) {
                io.println(origPasswords.get(password));
            }
        }
        io.flush();
        io.close();
    }




    public static void main(String[] args) {
        Kattio io = new Kattio();
        int n = io.getInt();
        HashSet<String> dict = new HashSet<String>();

        for (int i =0; i<n; i++){
            dict.add(io.getWord());
        }

        LinkedList<String> passwords = new LinkedList<>();

        HashSet<Integer> pwLength  = new HashSet<>();
        while (true){
            String word = io.getWord();
            passwords.add(word);
            pwLength.add(word.length());

            if(!io.ready()){
                break;
            }
        }


        new DictionaryAttackCleaned(dict,passwords,io, pwLength);
    }
}

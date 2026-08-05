package TDDD95.Lab3;

import Kattis.Kattio;

import java.io.*;
import java.util.*;

public class SAIS2 {

    /// /////////////////////DET FUNKAAAAAAAAAAAAAAR//////////////////
    /// nästa gång optimera bättre, massa onödiga loops atm
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("suffixTest.txt"));
        String line = br.readLine();
        long startTime = System.currentTimeMillis();
        getSuffixArray(line);
        long endTime = System.currentTimeMillis();
        System.err.println((endTime - startTime) / 1000 + " seconds");
        //   getSuffixArray("GTCCCGATGTCATGTCAGGA");
        System.out.println(Arrays.toString(getSuffixArray("GTCCCGATGTCATGTCAGGA")));
        System.out.println(Arrays.toString(getSuffixArray("ACGTGCCTAGCCTACCGTGCC")));

        /*
        Kattio io = new Kattio(System.in);
        while (true){
            int[] sa = getSuffixArray(io.getLine());
            int n =io.getInt();
            for (int i = 0; i < n; i++) {
                io.print(sa[io.getInt()+1] + " "); //+1 för sentinel
            }
            io.println();

            if (!io.ready()){
                break;
            }
        }
        io.flush();
        io.close();
        */
    }

    enum Type{
        L, //large
        S //small
    }

    public static int[] getSuffixArray(String str){
        int[] s = new int[str.length()+1];
        for (int i = 0; i < str.length(); i++){
            s[i] = str.charAt(i);
        }
        //      System.err.println("s: " + Arrays.toString(s));
        s[str.length()] = 0; //sentinel "$"

        return sais(s);
    }


    private static int[] sais(int[] s){
        Type[] ls = getLStype(s);
        LinkedList<Integer> lms = getLMS(ls);
        int[] suffixArray = induceSort(s,ls,lms,100000);
        //matchar hittills
        //System.err.println(Arrays.toString(getReducedString(s, suffixArray, ls, lms)));
        int[] reducedString = getReducedString(s, suffixArray, ls, lms);
        //      System.err.println("reducedS: " +Arrays.toString(reducedString));
        int[] reducedSuffix;
        if (noDuplicates(reducedString)){
            reducedSuffix = new int[reducedString.length];
            for (int i = 0; i < reducedString.length; i++){
                reducedSuffix[reducedString[i]] = i;
            }
            //        System.err.println("rs suff basfall: " + Arrays.toString(reducedSuffix));

        }else {
            reducedSuffix = sais(reducedString);
        }
        LinkedList<Integer> sortedLMS = new LinkedList<>();

        //    System.err.println("lms innan sort: " + lms);
        //     System.err.println("rs suff innan lmssort: " + Arrays.toString(reducedSuffix));
        for (int i = 0; i<reducedSuffix.length; i++) {
            sortedLMS.addLast(lms.get(reducedSuffix[i]));
        }
        //      System.err.println("sorterad lms: " + sortedLMS);
        return induceSort(s,ls,sortedLMS,100000); //alfabet ökar när vi minskar s?
    }


    //helt onödig kan nog fixa med class sen för RS
    private static boolean noDuplicates(int[] s){
        Set<Integer> set = new HashSet<>();
        for (int i : s) {
            if (set.contains(i)) return false;
            set.add(i);
        }
        return true;
    }
    private static class ReducedString{
        int[] rs;
        int rsAlphabet;
        public ReducedString(int[] rs, int rsAlphabet){
            this.rs = rs;
            this.rsAlphabet = rsAlphabet;
        }
    }
    //måste förbättras sen. O(1) enl stanford???
    private static int[] getReducedString(int[] sIn, int[] suffixArrayIn, Type[] LStypeIn, LinkedList<Integer> LMSIn){
        int[] s = Arrays.copyOf(sIn, sIn.length);
        int[] suffixArray = Arrays.copyOf(suffixArrayIn, suffixArrayIn.length);
        Type[] LStype = Arrays.copyOf(LStypeIn, LStypeIn.length);
        LinkedList<Integer> LMS = new LinkedList<>(LMSIn);


        int[] names = new int[s.length];
        Arrays.fill(names, -1);
        names[s.length-1] = 0; //sentnl $
        int lastLMS = s.length-1;
        int reducedAlphabetSize = 1;
        for(int i = 1 ; i<s.length ; i++){
            if (LMS.contains(suffixArray[i])) {
                if (lmsSubstringUnequal(s,lastLMS,suffixArray[i], LStype)){
                    reducedAlphabetSize++;
                }
                names[suffixArray[i]] = reducedAlphabetSize-1;
                lastLMS = suffixArray[i];
            }
        }
        //       System.err.println("rsLMS: " + Arrays.toString(s));
        int[] reducedS = new int[LMS.size()];
        int index = 0;
        for (int i : names) {
            if (i == -1) {continue;}
            reducedS[index++] = i;
        }
        return reducedS;
    }



    //compares two LMS substrings
    private static boolean lmsSubstringUnequal(int[] s, int p1, int p2, Type[] LStype) {
        boolean isLmsP1 = false;
        boolean isLmsP2 = false;
        while (true) {
            if (s[p1] != s[p2]) {
                return true;
            }
            if (LStype[p1] != LStype[p2]) {
                return true;
            }
            if (isLmsP1 && isLmsP2) {
                return false;
            }
            p1++;
            p2++;
            isLmsP1 = LStype[p1] == Type.S && LStype[p1 - 1] == Type.L;
            isLmsP2 = LStype[p2] == Type.S && LStype[p2 - 1] == Type.L;
            if (isLmsP1 && isLmsP2) {
                continue;
            }
            if (isLmsP1 || isLmsP2) {
                return true;
            }
        }


    }



    private static HashMap<Character,Integer>[] getBucketIndices(int[] s, int alphabetSize){
        int[] bucketSizes = new int[alphabetSize];
        for (int i = 0; i < s.length; i++) {
            bucketSizes[s[i]]++;
        }

        HashMap<Character,Integer> bucketEndIndices = new HashMap<>(); //exclusive
        HashMap<Character,Integer> bucketStartIndices = new HashMap<>(); //incl

        int counter = 0;
        for (int i = 0; i < alphabetSize; i++) {
            if (bucketSizes[i] > 0) {
                bucketStartIndices.put((char) i,counter);
                counter+= bucketSizes[i];
                bucketEndIndices.put((char) i , counter);
            }
        }
        return new HashMap[]{bucketStartIndices,bucketEndIndices};
    }

    private static int[] induceSort(int[] s, Type[] LStype, LinkedList<Integer> LMSindices, int alphabetSize){

        int[] suffixArray = new int[s.length];



        Arrays.fill(suffixArray, -1);
        HashMap<Character,Integer>[] bucketStartEnd = getBucketIndices(s, alphabetSize);
        HashMap<Character,Integer> bucketStartIndices = bucketStartEnd[0];//känns onödigt bökigt
        HashMap<Character,Integer> bucketEndIndices = bucketStartEnd[1];
        HashMap<Character,Integer> tempEnd = new HashMap<>(bucketEndIndices);


        for (Integer lmSindex : LMSindices) {
            char c = (char) s[lmSindex];
            int endIndex = bucketEndIndices.get(c);
            bucketEndIndices.remove(c);
            bucketEndIndices.put(c,endIndex-1);
            suffixArray[endIndex-1] = lmSindex;
        }
        //    System.err.println("sort1: " + Arrays.toString(suffixArray));

        for (Integer suffix : suffixArray) { //left to right scan finding L-types
            if (suffix >0){
                if (LStype[suffix-1] == Type.L) {
                    char c = (char) s[suffix-1];
                    int startIndex = bucketStartIndices.get(c);
                    bucketStartIndices.remove(c);
                    bucketStartIndices.put(c,startIndex+1);
                    suffixArray[startIndex] = suffix-1;
                }
            }
        }
        //    System.err.println("sort2: " + Arrays.toString(suffixArray));

        bucketEndIndices = tempEnd;
        for (int i = suffixArray.length-1; i > 0; i--) {
            if (suffixArray[i] > 0 && LStype[suffixArray[i]-1] == Type.S) {
                char c = (char) s[suffixArray[i]-1];
                int endIndex = bucketEndIndices.get(c);
                bucketEndIndices.remove(c);
                bucketEndIndices.put(c,endIndex-1);
                suffixArray[endIndex-1] = suffixArray[i]-1;
            }

        }
        //    System.err.println("sort3: " + Arrays.toString(suffixArray));

        return suffixArray;
    }

    private static Type[] getLStype(int[] sIn){
        int[] s = Arrays.copyOf(sIn, sIn.length);
        Type[] LStype = new Type[s.length];
        LStype[s.length-1] = Type.S;
        LStype[s.length-2] = Type.L;
        for (int i = s.length-3; i >=0 ; i--) {
            if (s[i] > s[i+1]) {
                LStype[i] = Type.L;
            }else if (s[i] == s[i+1] && LStype[i+1] == Type.L) {
                LStype[i] = Type.L;
            }else {
                LStype[i] = Type.S;
            }
        }
        //    System.err.println("ls: " + Arrays.toString(LStype));
        return LStype;
    }

    private static LinkedList<Integer> getLMS(Type[] LStype){ //linear
        LinkedList<Integer> LMSindices = new LinkedList<Integer>();
        for (int i = 1; i<LStype.length; i++){
            if (LStype[i] == Type.S && LStype[i-1] == Type.L) {
                LMSindices.add(i);
            }
        }

        //     System.err.println("lms: " + LMSindices);
        return LMSindices;
    }



}

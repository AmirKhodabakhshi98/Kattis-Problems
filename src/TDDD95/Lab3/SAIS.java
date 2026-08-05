package TDDD95.Lab3;
/*
package Lab3;

import java.util.*;

public class SAIS {


    enum Type{
        L, //large
        S //small
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
        return LStype;
    }


    private static LinkedList<Integer> getLMS(Type[] LStypeIn){ //linear
        Type[] LStype = Arrays.copyOf(LStypeIn, LStypeIn.length);

        LinkedList<Integer> LMSindices = new LinkedList<Integer>();
        for (int i = 1; i<LStype.length; i++){
            if (LStype[i] == Type.S && LStype[i-1] == Type.L) {
                LMSindices.add(i);
            }
        }
        return new LinkedList<>(LMSindices);
    }


    private static LinkedList<Integer> getLMS(Type[] LStype){ //linear
        LinkedList<Integer> LMSindices = new LinkedList<Integer>();
        for (int i = LStype.length-1; i>0; i--){
            if (LStype[i] == Type.S && LStype[i-1] == Type.L) {
                LMSindices.add(i);
            }
        }
        return LMSindices;
    }



    private static HashMap<Character,Integer>[] getBucketIndices(int[] sIn, int alphabetSize){
        int[] s = Arrays.copyOf(sIn, sIn.length);
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
        return new HashMap[]{new HashMap(bucketStartIndices),new HashMap(bucketEndIndices)};
    }

    private static int[] induceSort(int[] sIn, int[] suffixArrayIn, Type[] LStypeIn, LinkedList<Integer> LMSindicesIn, int alphabetSize){
        int[] s = Arrays.copyOf(sIn, sIn.length);
        int[] suffixArray = Arrays.copyOf(suffixArrayIn, suffixArrayIn.length);
        Type[] LStype = Arrays.copyOf(LStypeIn, LStypeIn.length);
        LinkedList<Integer> LMSindices = new LinkedList<>(LMSindicesIn);



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

        return suffixArray;
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(getSuffixArray("ACGTGCCTAGCCTACCGTGCC")));
        System.out.println(Arrays.toString(getSuffixArray("dabracadabrac")));
        System.out.println(Arrays.toString(getSuffixArray("banana")));
        System.out.println(Arrays.toString(getSuffixArray("GATAGACA")));
        System.out.println(Arrays.toString(getSuffixArray("aaaaaaaaaaa")));
        System.out.println(Arrays.toString(getSuffixArray("srajan")));
        System.out.println(Arrays.toString(getSuffixArray("abracadabradad")));
        System.out.println(Arrays.toString(getSuffixArray("alfalfa")));

      //  System.out.println(Arrays.toString(getSuffixArray("GTCCCGATGTCATGTCAGGA")));
     //   System.out.println(Arrays.toString(getSuffixArray("mmiissiissiippii")));
        System.out.println(Arrays.toString(getSuffixArray("ACGTGCCTAGCCTACCGTGCC")));


    }

    public static int[] getSuffixArray(String s){
        int[] stringArray = new int[s.length()+1];
        for (int i = 0; i < s.length(); i++) {
            stringArray[i] = s.charAt(i);
        }
        stringArray[s.length()] = 0;

        return sais(stringArray,256);
    }






    private static int[] sais(int[] sIn, int alphabetSize){
        int[] s = Arrays.copyOf(sIn, sIn.length);
        int n = s.length;
        int[] suffixArray =  new int[n];


        Type[] LStype = getLStype(s);
        LinkedList<Integer> LMS = getLMS(LStype);
        suffixArray = induceSort(s,suffixArray,LStype,LMS, alphabetSize);
        ReducedString reducedString = getReducedString(s,suffixArray,LStype,LMS);
        //hittills verifierat resultat med deras artikel..

        int reducedAlphabetSize = reducedString.reducedAlphabetSize;
        int[] sPrim = reducedString.s;
        int[] names = reducedString.names;
        int[] reducedSuffixArray;// = sais(sPrim);

        if (reducedAlphabetSize == sPrim.length){ //alla unique
            reducedSuffixArray = invert(sPrim);
        }else {
            reducedSuffixArray = sais(sPrim, reducedAlphabetSize);
        }

        LinkedList<Integer> sortedLMSindices = new LinkedList<>();
        for(int i = 0; i< reducedSuffixArray.length; i++){
            if (LMS.contains(reducedSuffixArray[i])){
                sortedLMSindices.add(reducedSuffixArray[i]);
            }
        }

        System.err.println(Arrays.toString(reducedSuffixArray));
        System.err.println(Arrays.toString(sPrim));
        System.err.println((LMS));

        return induceSort(s, suffixArray, LStype, LMS, alphabetSize);
    }




    private static int[] invert(int[] sIn){
        int[] inv = new int[sIn.length];
        for (int i = 0; i < sIn.length; i++) {
            inv[sIn[i]] = i;
        }
        return inv;
    }


    private static class ReducedString {
        int[] s;
        int reducedAlphabetSize;
        int[] names;

        public ReducedString(int[] s, int reducedAlphabetSize, int[] names) {
            this.s = s;
            this.reducedAlphabetSize = reducedAlphabetSize;
            this.names = names;
        }
    }
    private static ReducedString getReducedString(int[] sIn, int[] suffixArrayIn, Type[] LStypeIn, LinkedList<Integer> LMSIn){
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

        int[] reducedS = new int[LMS.size()];
        int index = 0;
        for (int i : LMS) {
            reducedS[index++] = names[i];
        }
        return new ReducedString(reducedS,reducedAlphabetSize, names);
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








}
**/
package TDDD95.Lab3;/*
package Lab3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class SuffixArray{



























    String s;
    Integer[] suffixes;
    Type[] LStype;
    int[] S1;
    LinkedList<Integer> LMSindices = new LinkedList<>(); //left-most S-type
    int[] bucketSizes ;
    int[] LMSnames;

    enum Type{
        L, //large
        S //small
    }

    private void getLStype(){
        LStype = new Type[s.length()];
        LStype[s.length()-1] = Type.S;
        LStype[s.length()-2] = Type.L;
        for (int i = s.length()-3; i >=0 ; i--) {
            if (s.charAt(i) > s.charAt(i+1)) {
                LStype[i] = Type.L;
            }else if (s.charAt(i) == s.charAt(i+1) && LStype[i+1] == Type.L) {
                LStype[i] = Type.L;
            }else {
                LStype[i] = Type.S;
            }
        }
    }

    private void getLMS(){ //linear
        for (int i = LStype.length-1; i>0; i--){
            if (LStype[i] == Type.S && LStype[i-1] == Type.L) {
                LMSindices.add(i);
            }
        }
    }


    HashMap<Character,Integer> bucketEndIndices = new HashMap<>(); //exclusive
    HashMap<Character,Integer> bucketStartIndices = new HashMap<>(); //incl
    private void getBucketStartIndices(){
        bucketSizes = new int[256]; //assuming alphabet size 256 for simplicity, lab/exercise tasks typically a-z, A-Z.
        for (int i = 0; i < s.length(); i++) {
            bucketSizes[s.charAt(i)]++;
        }

        int counter = 0;
        for (int i = 0; i < 256; i++) {
            if (bucketSizes[i] > 0) {
                bucketStartIndices.put((char)i,counter);
                counter+= bucketSizes[i];
                bucketEndIndices.put((char) i , counter);
            }
        }
    }


    private void sortLMS(){
        suffixes = new Integer[s.length()];
        Arrays.fill(suffixes, -1);
        HashMap<Character,Integer> tempEnd = new HashMap<>(bucketEndIndices); //linjärt??
        HashMap<Character,Integer> bucketStartIndices = new HashMap<>(this.bucketStartIndices);
        
        for (Integer lmSindex : LMSindices) {
            char c = s.charAt(lmSindex);
            int endIndex = bucketEndIndices.get(c);
            bucketEndIndices.remove(c); //finns nog smartare sätt
            bucketEndIndices.put(c,endIndex-1);
            suffixes[endIndex-1] = lmSindex;
        }

        for (Integer suffix : suffixes) { //left to right scan finding L-types
            if (suffix >0){
                if (LStype[suffix-1] == Type.L) {
                    char c = s.charAt(suffix-1);
                    int startIndex = bucketStartIndices.get(c);
                    bucketStartIndices.remove(c);
                    bucketStartIndices.put(c,startIndex+1);
                    suffixes[startIndex] = suffix-1;
                }
            }
        }

        bucketEndIndices = tempEnd;
        tempEnd = new HashMap<>(bucketEndIndices);
        for (int i = suffixes.length-1; i > 0; i--) { //right to left scan finding s type
            if (suffixes[i]> 0 && LStype[suffixes[i]-1] == Type.S) {
                char c = s.charAt(suffixes[i]-1);
                int endIndex = bucketEndIndices.get(c);
                bucketEndIndices.remove(c);
                bucketEndIndices.put(c,endIndex-1);
                suffixes[endIndex-1] = suffixes[i]-1;
            }
        } //verifierat hittills
        //LMS sorterat vid d här laget
        
    }





    public void SAIS(){

        //scan S once to classify all the characters as L- or S-type into t
        getLStype();
        System.out.println(Arrays.toString(LStype));
        //Scan t once to find all the LMS-substrings in S into P1
        getLMS();
        System.out.println(LMSindices);

        getBucketStartIndices();
        System.out.println(bucketEndIndices.toString());
        System.out.println(bucketStartIndices.toString());
        //Induced sort all the LMS-substrings using P1 and B
        sortLMS();
        System.out.println(Arrays.toString(suffixes));

        //Name each LMS-substring in S by its bucket index to get a new shortened string S1
        int[] names = new int[s.length()];
        Arrays.fill(names, -1);
        int counter = 1;
        names[s.length()-1] = 0; //sentnl $

        int lastLMS = s.length()-1;
        int reducedAlphabetSize = 1;
        int j = 0;
        for(int i = 1 ; i<s.length() ; i++){
            if (LMSindices.contains(suffixes[i])) {
                if (lmsSubstringUnequal(s,lastLMS,suffixes[i])){
                    reducedAlphabetSize++;
                }
                names[suffixes[i]] = reducedAlphabetSize-1;
                lastLMS = suffixes[i];
            }
        }
        System.out.println(Arrays.toString(names));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (names[i] != -1){
                sb.append(names[i]+'a'); //how does it avoid confusion 11 - 12 eg?
            }
        }
        System.out.println(sb);

        naiveSlides(sb.toString());
        //if - each character in S1 is unique
            //directly compute SA1 from S1
        //else
            //SA-IS(S,SA1)

        //Induce SA from SA1
        //Return

    }

    //compares two LMS substrings
    private boolean lmsSubstringUnequal(String s, int p1, int p2) {
        boolean isLmsP1 = false;
        boolean isLmsP2 = false;
        while (true) {
            if (s.charAt(p1) != s.charAt(p2)) {
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




    public SuffixArray(String input){
        this.s = input + "$";
        suffixes = new Integer[s.length()];
        SAIS();
    }



    //naive from slides
    public Integer[] naiveSlides(String sPrim){
        Integer[] sa = new Integer[sPrim.length()];

        for (int i = 0; i < s.length(); i++){
            sa[i] = i;
        }

        Arrays.sort(sa, Comparator.comparing(a -> sPrim.substring((Integer) a)));
        return sa;
    }


    public static void main(String[] args) {
       // SuffixArray sa = new SuffixArray("cabbage");
      //  SuffixArray sa = new SuffixArray("dabracadabrac");
      //  SuffixArray sa = new SuffixArray("ACGTGCCTAGCCTACCGTGCC");
        SuffixArray sa = new SuffixArray("banana");
      //  SuffixArray sa = new SuffixArray("GTCCCGATGTCATGTCAGGA");
    }


}


*/
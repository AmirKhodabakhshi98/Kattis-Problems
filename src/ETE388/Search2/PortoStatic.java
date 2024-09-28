package ETE388.Search2;

import java.util.*;

public class PortoStatic {






    private static int makeTree(int alphabetIndex, ArrayList<Character> alphabet, int solutions, Set<Character> cantBeZero,
                                int[] charValues, boolean[] uniqueValuesArr, char[][]wordsCharArray, char[]sumCharArray){

        if (alphabetIndex>=alphabet.size()){
            return solutions;
        }


        for (int i=0; i<=9; i++){

            if (uniqueValuesArr[i]){
                continue;
            }
            if (i==0 && cantBeZero.contains(alphabet.get(alphabetIndex))){
                continue;
            }

            uniqueValuesArr[i]=true;

            charValues[alphabet.get(alphabetIndex)] = i;

            if (alphabetIndex+1<alphabet.size()){
                solutions = makeTree(alphabetIndex+1,alphabet,solutions,cantBeZero,charValues,uniqueValuesArr,wordsCharArray,sumCharArray);
            }
            if (alphabetIndex==alphabet.size()-1){

                if (solved2(sumCharArray,wordsCharArray,charValues)){
                    solutions++;
                }
            }

            uniqueValuesArr[i]=false;
        }
        return solutions;
    }




    private static boolean solved2(char[]sumCharArray, char[][] wordsCharArray, int[] charValues){
        int sumValue = 0;

        for (int i=0; i<sumCharArray.length;i++){
            sumValue+= charValues[sumCharArray[i]] * Math.pow(10, sumCharArray.length-1-i);
        }


        int value = 0;
        int maxWordLength = wordsCharArray[0].length;


        for (int i = 0; i<maxWordLength; i++){
            for (int j = 0; j<wordsCharArray.length; j++){
                if (i >= wordsCharArray[j].length){
                    continue;
                }
                value += charValues[(wordsCharArray[j][i])] * Math.pow(10,wordsCharArray[j].length-i-1);

                if (value>sumValue){
                    return false;
                }
            }

        }


        return sumValue==value;
    }




    public static void main(String[] args) {
        ArrayList<Character> alphabet = new ArrayList<>();



        boolean[] uniqueValuesArr = new boolean[10];
        char[] sumCharArray = new char[0];
        Set<Character> cantBeZero = new HashSet<>();
        int maxWordLength = 0;
        int[] charValues = new int[91];;


        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        char[][] wordsCharArray = new char[n-1][];


        for (int i = 0; i<n; i++){
            char[] string = scanner.next().toCharArray();
            if (string.length>maxWordLength){
                maxWordLength=string.length;
            }
            for (int j =0; j<string.length; j++){
                char c = string[j];
                if (j==0){
                    cantBeZero.add(c);
                }

                if (!alphabet.contains(c)) {
                    alphabet.add(c);
                }
            }
            if (i==n-1){
                sumCharArray = string;
            }else {
                wordsCharArray[i]=string;
            }
        }
        Arrays.sort(wordsCharArray, (a,b)-> Integer.compare(b.length,a.length));



        System.out.println(
                makeTree(0,alphabet,0,cantBeZero,charValues,uniqueValuesArr,wordsCharArray,sumCharArray));

    }
}

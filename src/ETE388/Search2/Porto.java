package ETE388.Search2;

import java.util.*;

public class Porto {

    ArrayList<Character> alphabet = new ArrayList<>();



    private char[][] wordsCharArray;
    private char[] sumCharArray;
    private int solutions;
    private Set<Character> cantBeZero = new HashSet<>();
    int maxWordLength = 0;




    private int[] charValues;

    public Porto(){


        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        wordsCharArray = new char[n-1][];


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
                if (!alphabet.contains(c)){
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


        charValues = new int[91];

        makeTree(0);

        System.out.println(solutions);


    }




    boolean[] uniqueValuesArr = new boolean[10];

    private void makeTree(int alphabetIndex){

        if (alphabetIndex>=alphabet.size()){
            return;
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
                makeTree(alphabetIndex+1);
            }
            if (alphabetIndex==alphabet.size()-1){

                if (solved2()){
                    solutions++;
                }
            }

            uniqueValuesArr[i]=false;
        }
    }



    private int calc(char[] word, int sumValue){
        int value = 0;
        for (int i = 0; i<word.length; i++){
            value += (charValues[(word[i])]* pows[word.length-1-i]);
            if (value>sumValue){
                return -1;
            }
        }

        return value;
    }

    private int[] pows = {1,10,100,1000,10000,100000,1000000,10000000,100000000,1000000000};


    private boolean solved2(){
        int sumValue = calc(sumCharArray, Integer.MAX_VALUE);
        int value = 0;


        for (int i = 0; i<maxWordLength; i++){
            for (int j = 0; j<wordsCharArray.length; j++){
                if (i >= wordsCharArray[j].length){
                    continue;
                }
                value += charValues[(wordsCharArray[j][i])] * pows[wordsCharArray[j].length-i-1];

                if (value>sumValue){
                    return false;
                }
            }

        }


        return sumValue==value;
    }




    public static void main(String[] args) {
        new Porto();

    }
}

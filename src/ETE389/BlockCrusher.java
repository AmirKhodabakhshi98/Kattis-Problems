package ETE389;


public class BlockCrusher {


    private static int[][] fractureFinder(int[][] block){


        return block;
    }

    private static void djikstra(){

    }



    private static int[] wordToDigits(String word){
        int[] digits  = new int[word.length()];
        for(int i = 0; i < word.length(); i++){
            digits[i] = word.charAt(i) - '0';
        }
        return digits;

    }

    static void main() {
        Kattio io = new Kattio(System.in, System.out);
        while (true){
            int H = io.getInt();
            int W = io.getInt();

            //no more cases
            if (H == 0 && W == 0){
                break;
            }

            int[][] block = new int[H][W];
            for (int h = 0; h < H; h++) {
                String word = io.getWord();
                block[h] = wordToDigits(word);
            }

            block = fractureFinder(block);


            for (int h = 0; h < H; h++) {
                for (int w = 0; w < W; w++) {
                    if (block[h][w] == 0){
                        System.out.print(" ");
                    }else  {
                        System.out.print(block[h][w]);
                    }
                }
                System.out.println();
            }
            System.out.println();



        }


        io.flush();
        io.close();


    }

}

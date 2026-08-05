package TDDD95.GreedyDynamic1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class LjutnaTestGenerator {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("LjutnaTest");
        file.delete();
        PrintWriter writer = new PrintWriter("LjutnaTest");
        int M = (int) (2 * Math.pow(10,9));
        //int M = 1;
        int N = 10;
        writer.println(M + " " + N);
        int totalDemand = (int) (2 * Math.pow(10,3)-1);

        int[] children = new int[N];
        Arrays.fill(children,1);
        int demand = N;
        //totalDemand -= children.length;
        Random rand = new Random();
        for (int i = 0; i< children.length-1;i++) {
            int child = rand.nextInt(2000000000-1);
            children[i]+=child;
            demand+=child+1;
            writer.println(children[i]);
        }
        if (demand<totalDemand){
            children[children.length-1]=totalDemand-demand;
        }
        writer.println(children[children.length-1]);

        writer.flush();
        writer.close();



    }

}

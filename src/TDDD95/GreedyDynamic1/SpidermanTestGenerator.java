package TDDD95.GreedyDynamic1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class SpidermanTestGenerator {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("spidermanTest");
        file.delete();
        PrintWriter writer = new PrintWriter("spidermanTest");

        int N = 1;
        Random rand = new Random();

        writer.println(N);
        for (int i=0; i<N; i++){
            int sum = 0;

            ArrayList<Integer> list = new ArrayList<>();
            while (sum<1000 && list.size()<40){
                int d = rand.nextInt(1,20);
                sum+=d;
                list.add(d);
            }

            writer.println(list.size());
            for (int x:
                 list) {
                writer.print(x + " ");
            }
            writer.println();
        }

        writer.flush();
        writer.close();

    }
}

package TDDD95.DataStructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class IntrospectiveCachingTestGenerator {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("TDDD95/src/Exercise2/introspectiveCaching");
        file.delete();
        PrintWriter writer = new PrintWriter("TDDD95/src/Exercise2/introspectiveCaching");
        int c = 1000;
        int n = 10000;
        int a = 100000;

        Random rand = new Random();

        writer.println(c + " " + n + " " + a);
        for (int i=0; i<a; i++){
            writer.println(i%c);
        }


        writer.flush();
        writer.close();

    }






}

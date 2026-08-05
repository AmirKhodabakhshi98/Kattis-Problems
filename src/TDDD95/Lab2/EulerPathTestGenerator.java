package TDDD95.Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class EulerPathTestGenerator {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("eulerPathTestGenerator");
        file.delete();
        PrintWriter writer = new PrintWriter("eulerPathTestGenerator");
        int n = 10000;
        int m = (n-1)*2;

        Random rand = new Random();

        writer.println(n + " " + m);
        for (int i=0; i<n-1; i++){
            writer.println(i + " " + (i+1));
            writer.println(i+1 + " " + (i));
        }

        writer.println(0 + " " + 0);
        writer.flush();
        writer.close();

    }

}

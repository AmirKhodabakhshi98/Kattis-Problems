package TDDD95.DataStructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class SetStackTestGenerator {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("TDDD95/src/Exercise2/SetStackTest");
        file.delete();
        PrintWriter writer = new PrintWriter("TDDD95/src/Exercise2/SetStackTest");
        int T = 1;
        int N = 100;
        Random rand = new Random();

        writer.println(T + "\n" + N);
        for (int i=0; i<N/2; i++){
            writer.println("DUP");
            writer.println("ADD");
        }


        writer.flush();
        writer.close();

    }




}

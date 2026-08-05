package TDDD95.Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class suffixTest {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("suffixTest.txt");
        file.delete();
        PrintWriter writer = new PrintWriter("suffixTest.txt");
        int n = 50000;
        StringBuilder sb = new StringBuilder(n);

        Random rand = new Random();

        for (int i=0; i<n; i++){
            sb.append((char)rand.nextInt('A','z'+1));
        }

        writer.print(sb.toString());
        writer.print(sb.toString());

        writer.flush();
        writer.close();

    }
}

package ETE388.Search1;

import java.io.*;
import java.util.*;
public class CandyDivision {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long input = scanner.nextLong();

        Set<Long> set = new HashSet<>();

      //  System.out.print(Math.sqrt(input));
        long sqrt = (long) Math.floor(Math.sqrt(input));

        ArrayList <Long> list1 = new ArrayList<>();
        ArrayList <Long> list2 = new ArrayList<>();


        for (long i = 1; i<sqrt+1; i++){

            if (input % i == 0){
                list1.add(i-1);
                set.add(i-1);
                if (!set.contains((input / i) - 1)) {
                    list2.add((input / i) - 1);
                }
               // System.out.print(i-1 + " ");
            }
        }

        for (long l:
             list1) {
           System.out.print(l + " ");

        }

        Collections.reverse(list2);
        for (long l:
                list2) {
            System.out.print(l + " ");

        }


    }
}

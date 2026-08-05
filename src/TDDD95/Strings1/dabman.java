package TDDD95.Strings1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
import Kattis.*;



    public class dabman {



        public static void main(String[] args) throws FileNotFoundException {




            File file = new File("rin.txt");
            file.delete();
            PrintWriter writer = new PrintWriter("rin.txt");

            File myObj = new File("dictionary.txt");
            Scanner scanner = new Scanner(myObj);

            int dictSize = Integer.parseInt(scanner.nextLine());
            long startTime = System.currentTimeMillis();
            boolean notFound = true;
            boolean noMatchingSize = true;
            ArrayList<String> dict = new ArrayList<String>();

            for (int i = 0; i < dictSize; i++){
                String word = scanner.nextLine();
                dict.add(word);
            }

            while (scanner.hasNextLine()){
                notFound = true;
                noMatchingSize = true;
                String attempt = scanner.nextLine();

                for (int i = 0; i < dictSize && notFound; i++){
                    String check = dict.get(i);
                    if (check.length() == attempt.length()){
                        notFound = Check(check, attempt);
                        noMatchingSize = false;
                    }
                }



                if (!notFound || noMatchingSize){
                    //System.out.println(attempt);
                    writer.println(attempt);
                }
            }

            long endTime = System.currentTimeMillis();
            System.err.println(endTime - startTime);
            scanner.close();
            writer.flush();
            writer.close();
        }

        private static boolean Check(String check, String attempt){
            int swapsLeft = 3;
            for (int i = 0; i < attempt.length(); i++) {
                if (Character.isDigit(attempt.charAt(i))) {
                    swapsLeft--;
                }
            }
            if (swapsLeft > -1){
                return CheckRecursive(check, attempt, swapsLeft);
            }
            return false;
        }


        private static boolean CheckRecursive(String check, String attempt, int swapsLeft){
            if (FinalCheck(check, attempt)){
                return true;
            }
            if (swapsLeft > 0){
                for (int i = 0; i < attempt.length() - 1; i++) {
                    StringBuilder newCheck = new StringBuilder();
                    for (int j = 0; j < attempt.length(); j++) {
                        if (i == j){
                            newCheck.append(check.charAt(j + 1));
                        }
                        else if (i + 1 == j){
                            newCheck.append(check.charAt(j - 1));
                        }
                        else {
                            newCheck.append(check.charAt(j));
                        }
                    }
                    if (CheckRecursive(newCheck.toString(), attempt, swapsLeft - 1)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private static boolean FinalCheck(String check, String attempt){
            for (int i = 0; i < attempt.length(); i++) {
                if (!Character.isDigit(attempt.charAt(i)) && check.charAt(i) != attempt.charAt(i)){
                    return false;
                }
            }
            return true;
        }


    }


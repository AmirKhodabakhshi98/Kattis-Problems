package TDDD95.Strings1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class PomeRinComparator {

    public static void main(String[] args) throws FileNotFoundException {



     //   File fileComp = new File("comparison.txt");
    //    PrintWriter writer = new PrintWriter(fileComp);


            dictAttackTestGen(10,10000,4);

            DictionaryAttack.main(null);
            dabman.main(null);

            File myObjRin = new File("rin.txt");
            File myObjPome = new File("pome.txt");



            Scanner myReaderRin = new Scanner(myObjRin);
            Scanner myReaderPome = new Scanner(myObjPome);

            HashSet<String> rinNoMatch = new HashSet<>();
            HashSet<String> pomeNoMatch = new HashSet<>();

            HashSet<String> pomeButNotRin = new HashSet<>();
            HashSet<String> rinButNotPome = new HashSet<>();


       while (myReaderRin.hasNext()){
                String line = myReaderRin.nextLine();
                rinNoMatch.add(line);
            }

        while (myReaderPome.hasNext()){
            String line = myReaderPome.nextLine();
            pomeNoMatch.add(line);
        }


        for (String nomatch : pomeNoMatch) {
                if (!rinNoMatch.contains(nomatch)) {
                    pomeButNotRin.add(nomatch);
                }
            }


        for (String noMatch : rinNoMatch) {
                if (!pomeNoMatch.contains(noMatch)) {
                    rinButNotPome.add(noMatch);
                }
            }

        System.out.println();
        System.out.println("nbr pome printed but not rin:");
        System.out.println(pomeButNotRin.size());
        System.out.println("following:");
        for (String s : pomeButNotRin) {
            System.out.println(s);
        }


        System.out.println("nbr rin printed but not pome:");
        System.out.println(rinButNotPome.size());
        System.out.println("following:");
        for (String s : rinButNotPome) {
            System.out.println(s);
        }

        System.out.println("pome output size:");
        System.out.println(pomeNoMatch.size());
        System.out.println("rin output size");
        System.out.println(rinNoMatch.size());

        myReaderRin.close();
        myReaderPome.close();

        }










    private static String getWord(int chars) {
        StringBuilder sb = new StringBuilder();
        Random rand  = new Random();

        for (int i = 0; i<chars; i++) {

            char c = (char) (rand.nextInt(26) + 'a');
            sb.append(c);

        }
        return sb.toString();

    }

    private static String getWord2(int chars) {
        StringBuilder sb = new StringBuilder();
        Random rand  = new Random();
        for (int i = 0; i<chars; i++) {

            char c = (char) (rand.nextInt(26) + 'a');
            sb.append(c);

        }

        for (int i = 0; i< 3; i++){
            //    sb.setCharAt(rand.nextInt(3), (char) rand.nextInt(9));
        }
        return sb.toString();

    }


    public static void dictAttackTestGen(int nbrDict, int nbrPwd, int chars) throws FileNotFoundException {
        File file = new File("dictionary.txt");
        file.delete();
        PrintWriter writer = new PrintWriter("dictionary.txt");




        Random rand = new Random();
        writer.println(nbrDict);

        for (int i = 0; i<nbrDict; i++) {
            writer.println(getWord(chars));
        }

        for (int i = 0; i < nbrPwd; i++) {
            writer.println(getWord2(chars));
        }

        
        writer.flush();
        writer.close();
    }







    }
